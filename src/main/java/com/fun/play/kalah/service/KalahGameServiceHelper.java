package com.fun.play.kalah.service;

import com.fun.play.kalah.domain.KalahGame;
import com.fun.play.kalah.domain.Player;
import com.fun.play.kalah.domain.Status;
import com.fun.play.kalah.exception.InvalidKalahMoveException;
import com.fun.play.kalah.util.KalahUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.fun.play.kalah.util.KalahUtils.LAST_PIT_INDEX;

@Slf4j
@Component
public class KalahGameServiceHelper {


    public KalahGame makeMove(final KalahGame kalahGame, final int pitId) {

        if (!checkIfPitSelectionIsValid (pitId, kalahGame.getPlayer (), kalahGame.getStatus ())) {
            throw new InvalidKalahMoveException ("Invalid Move.Please selected valid pit for player {} " + kalahGame.getPlayer ().getPits ());
        } else {

            if (kalahGame.getStatus ().get (pitId) == 0) {
                if (calculateValidMoves (kalahGame.getPlayer (), pitId, kalahGame.getStatus ()).isEmpty ()) {
                    findWinnerAndTerminateGame (kalahGame);
                } else {
                    throw new InvalidKalahMoveException ("Invalid Move. Selected pit is empty.");
                }
            } else {
                final int count = pitId + kalahGame.getStatus ().get (pitId);
                sowSeeds (pitId, kalahGame, count);
            }
        }

        return kalahGame;
    }

    private boolean checkIfPitSelectionIsValid(final int pitId, final Player player, final Map<Integer, Integer> status) {
        //pitId should not be kalah
        if (pitId == player.getKalahId () || pitId == player.getOppositePlayer ().getKalahId ()) return false;
        //pitId should not be opponent pits
        if (player.getOppositePlayer ().getPits ().contains (pitId)) return false;
        //pit should be in range 1-14.
        return pitId >= KalahUtils.FIRST_PIT_INDEX && pitId <= LAST_PIT_INDEX;
    }


    private void emptySelectedPit(final int pitId, final KalahGame kalahGame) {
        //set value of stones to 0.
        kalahGame.getStatus ().put (pitId, 0);
    }

    private void enableTurnOfPlayer(final KalahGame kalahGame, final int pitIndex) {
        if (kalahGame.getPlayer ().getKalahId () != pitIndex) {
            kalahGame.setPlayer (kalahGame.getPlayer ().getOppositePlayer ());
        }
    }

    private void updateKalahWithSeedsOfOppositeHouse(final KalahGame kalahGame, final int lastPitIndex) {
        if (isUserPit (lastPitIndex, kalahGame.getPlayer ())) {
            final Integer oppositePitIndex = getOppositePit (lastPitIndex);
            final Integer oppositePitValue = kalahGame.getStatus ().get (oppositePitIndex);
            final Integer kalahValue = kalahGame.getStatus ().get (kalahGame.getPlayer ().getKalahId ());
            final Integer lastPitIndexValue = kalahGame.getStatus ().get (lastPitIndex);

            kalahGame.getStatus ().put (kalahGame.getPlayer ().getKalahId (), (kalahValue + oppositePitValue + lastPitIndexValue));

            emptySelectedPit (oppositePitIndex, kalahGame);
            emptySelectedPit (lastPitIndex, kalahGame);

        }
    }

    private int getOppositePit(final int pitId) {
        return LAST_PIT_INDEX - pitId;
    }

    private boolean isUserPit(final int pitId, final Player player) {
        return player.getPits ().contains (pitId);
    }

    private List<Integer> calculateValidMoves(final Player player, final int pitId, final Map<Integer, Integer> status) {
        List<Integer> validPits = new ArrayList<> ();
        if (status.get (pitId) == 0) {
            player.getPits ().forEach (pitId1 -> {
                if (status.get (pitId1) > 0) {
                    validPits.add (pitId1);
                }
            });
        }
        return validPits;
    }

    private void findWinnerAndTerminateGame(final KalahGame kalahGame) {

        //calculate Total Seeds for the player with all pits empty
        int totalSeedsForPlayerWithEmptyPits = kalahGame.getStatus ().get (kalahGame.getPlayer ().getKalahId ());

        //calculate all seeds in oponent Pits
        List<Integer> pits = kalahGame.getPlayer ().getOppositePlayer ().getPits ();
        int totalOfseedsOponenet = pits.stream ().mapToInt (pit -> kalahGame.getStatus ().get (pit)).sum ()+
                kalahGame.getStatus ().get (kalahGame.getPlayer ().getOppositePlayer ().getKalahId ());

        if (totalOfseedsOponenet == totalSeedsForPlayerWithEmptyPits) {
            kalahGame.setGameStatus (Status.DRAW);
        } else if (totalOfseedsOponenet > totalSeedsForPlayerWithEmptyPits) {
            kalahGame.setGameStatus (Status.SECOND_PLAYER_WIN);
        } else {
            kalahGame.setGameStatus (Status.FIRST_PLAYER_WIN);
        }
    }

    private void updateSeedsToHouses(final Map<Integer, Integer> status, final int count, final int pitId) {

        for (int counter = pitId; counter <= count; counter++) {
            Integer currentValue = status.get (counter);
            status.put (counter, (currentValue + 1));
        }
    }


    private void sowSeeds(final int pitId, final KalahGame kalahGame, final int count) {
        emptySelectedPit (pitId, kalahGame);

        int lastPitIndex;

        if (Player.FIRST_PLAYER == kalahGame.getPlayer ()) {
            if (count <= (LAST_PIT_INDEX - 1)) {
                lastPitIndex = count;
                updateSeedsToHouses (kalahGame.getStatus (), count, pitId + 1);

            } else {
                int diff = count - (LAST_PIT_INDEX - 1);
                lastPitIndex = diff;
                updateSeedsToHouses (kalahGame.getStatus (), diff, 1);
            }


        } else {
            if (count <= LAST_PIT_INDEX) {
                lastPitIndex = count;
                updateSeedsToHouses (kalahGame.getStatus (), count, pitId + 1);
            } else {
                //covering boundary
                updateSeedsToHouses (kalahGame.getStatus (), LAST_PIT_INDEX, pitId + 1);

                int diff = count - LAST_PIT_INDEX;

                for (int i = 1; i <= diff; i++) {
                    if (i == kalahGame.getPlayer ().getOppositePlayer ().getKalahId ()) {
                        i = i + 1;
                        diff = diff + 1;
                        Integer currentValue = kalahGame.getStatus ().get (i);
                        kalahGame.getStatus ().put (i, (currentValue + 1));
                    } else {
                        Integer currentValue = kalahGame.getStatus ().get (i);
                        kalahGame.getStatus ().put (i, (currentValue + 1));
                    }
                }

                lastPitIndex = diff;
            }
        }

        if (kalahGame.getStatus ().get (lastPitIndex) == 1) {
            updateKalahWithSeedsOfOppositeHouse (kalahGame, lastPitIndex);
        }
        enableTurnOfPlayer (kalahGame, lastPitIndex);
    }

}

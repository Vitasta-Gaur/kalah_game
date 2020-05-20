package com.fun.play.kalah.service;

import com.fun.play.kalah.domain.KalahGame;
import com.fun.play.kalah.domain.Player;
import com.fun.play.kalah.domain.Status;
import com.fun.play.kalah.exception.InvalidKalahMoveException;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KalahGameServiceHelperTest {

    private KalahGame kalahGame = new KalahGame ();

    private Map<Integer,Integer> board = kalahGame.getStatus ();

    private KalahGameServiceHelper kalahGameServiceHelper = new KalahGameServiceHelper();

    @Test
    public void should_make_move_successfully(){
        int pitId = 3;
        int pitAmount = board.get(pitId);
        Map<Integer, Integer> beforeMove = new HashMap<> (board);

        kalahGameServiceHelper.makeMove(kalahGame, pitId);

        IntStream.range(pitId + 1, pitAmount + 1).forEach(pit -> {
            int amount = board.get(pit);
            assertEquals(amount, beforeMove.get(pit) + 1);
        });
    }


    @Test
    public void should_throw_exception_if_invalid_pit_is_provided(){

        Exception exception = Assertions.assertThrows(InvalidKalahMoveException.class, () ->
                kalahGameServiceHelper.makeMove (kalahGame,8) );
        assertEquals("Invalid Move.Please selected valid pit for player {} [1, 2, 3, 4, 5, 6]", exception.getMessage());

    }

    @Test
    public void should_return_valid_pitId_if_empty_pit_selected(){
        board.put (3,0);

        Exception exception = Assertions.assertThrows(InvalidKalahMoveException.class, () ->
                kalahGameServiceHelper.makeMove (kalahGame,3) );
        assertEquals("Invalid Move. Selected pit is empty.", exception.getMessage());
    }

    @Test
    public void should_find_winner_if_all_pits_are_empty(){
        board.put (1,0);
        board.put (2,0);
        board.put (3,0);
        board.put (4,0);
        board.put (5,0);
        board.put (6,0);
        board.put (7,10);

        final KalahGame game = kalahGameServiceHelper.makeMove (kalahGame, 3);

        assertEquals (Status.SECOND_PLAYER_WIN , game.getGameStatus ());
    }

    @Test
    public void should_add_all_stones_to_kalah_if_own_pit_empty(){
        board.put (1,3);
        board.put (4,0);
        board.put (7,10);

        final KalahGame game = kalahGameServiceHelper.makeMove (kalahGame, 1);

        assertAll (
                () -> assertEquals (17 , game.getStatus ().get (7).intValue () ,"Kalah id is not populated with stones"),
                () -> assertEquals (0 , game.getStatus ().get (1).intValue (), "selected pit index is not set to 0"),
                () -> assertEquals (0 , game.getStatus ().get (10).intValue () , "opposite pit index is not set to 0")
        );
    }

    @Test
    public void should_update_kalah_board_for_player_2(){
        kalahGame.setPlayer (Player.SECOND_PLAYER);
        final KalahGame game = kalahGameServiceHelper.makeMove (kalahGame, 12);

        assertAll (
                () -> assertEquals (1 , game.getStatus().get (14).intValue ()),
                () -> assertEquals (7 , game.getStatus().get (1).intValue ()),
                () -> assertEquals (7 , game.getStatus().get (2).intValue ()),
                () -> assertEquals (7 , game.getStatus().get (3).intValue ()),
                () -> assertEquals (7 , game.getStatus().get (4).intValue ())
        );
    }

}
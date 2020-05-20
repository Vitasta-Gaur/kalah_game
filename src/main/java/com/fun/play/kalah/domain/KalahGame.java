package com.fun.play.kalah.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

import static com.fun.play.kalah.util.KalahUtils.FIRST_PIT_INDEX;
import static com.fun.play.kalah.util.KalahUtils.INITIAL_STONES_QUANTITY;
import static com.fun.play.kalah.util.KalahUtils.LAST_PIT_INDEX;
import static java.util.stream.IntStream.rangeClosed;

/**
 * This is the domain entity mapped to table kalah in database.
 */

@Entity
@Table(name = "Kalah")
@Getter
@Setter
public class KalahGame {

    @Id
    @GeneratedValue
    private int id;

    @ElementCollection
    @MapKeyColumn(name="pitId")
    @Column(name="value")
    private Map<Integer, Integer> status;

    @Enumerated(value = EnumType.STRING)
    private Status gameStatus;

    @Enumerated(value = EnumType.STRING)
    private Player player;

    public KalahGame() {
        initializeKalahBoard();
        gameStatus = Status.IN_PROGRESS;
        player = Player.FIRST_PLAYER;
    }


    private void initializeKalahBoard() {
        status = new HashMap<>();
        rangeClosed (FIRST_PIT_INDEX, LAST_PIT_INDEX).forEach (i -> {
            int firstKhalIndex = Player.FIRST_PLAYER.getKalahId ();
            int secondKhalIndex = Player.SECOND_PLAYER.getKalahId ();
            int value = (i != firstKhalIndex && i != secondKhalIndex) ? INITIAL_STONES_QUANTITY : 0;
            status.put (i, value);
        });
    }
}

package com.fun.play.kalah.repository;

import com.fun.play.kalah.domain.KalahGame;
import com.fun.play.kalah.domain.Player;
import com.fun.play.kalah.domain.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith (SpringExtension.class)
@DataJpaTest
public class KalahGameRepositoryTest {

    @Autowired
    KalahGameRepository kalahGameRepository;

    @BeforeEach
    public void init(){
        kalahGameRepository.save (new KalahGame ());
    }

    @DisplayName ("Should return kalah game where status = IN_PROGRESS and id is valid.")
    @Test
    public void should_find_kalah_game_if_gameid_is_valid(){
        final Optional<KalahGame> optionalKalahGame = kalahGameRepository.findByIdAndGameStatus (1, Status.IN_PROGRESS);
        final KalahGame game = optionalKalahGame.get ();

        Assertions.assertAll (
                () -> Assertions.assertEquals (1 , game.getId ()),
                () -> Assertions.assertNotNull (game)
        );
    }
}

package com.fun.play.kalah.service;

import com.fun.play.kalah.domain.KalahGame;
import com.fun.play.kalah.domain.Status;
import com.fun.play.kalah.dto.KalahMoveDTO;
import com.fun.play.kalah.dto.KalahDTO;
import com.fun.play.kalah.exception.KalahGameNotFoundException;
import com.fun.play.kalah.mapper.KalahGameMapper;
import com.fun.play.kalah.repository.KalahGameRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith (MockitoExtension.class)
public class KalahGameServiceTest {

    @Mock
    KalahGameRepository kalahGameRepository;

    @Mock
    KalahGameMapper kalahGameMapper;

    @Mock
    KalahGameServiceHelper kalahGameServiceHelper;

    @InjectMocks
    KalahGameService kalahGameService;


    @DisplayName ("Should create a new kalah game.")
    @Test
    public void should_create_kalah_game_successfully(){
        when (kalahGameRepository.save (any ())).thenReturn (getKalahGame ());
        when (kalahGameMapper.convertToDto (any ())).thenReturn (getNewKalahDTO ());

        KalahDTO kalahDTO = kalahGameService.createKalahGame ();

        Assertions.assertAll (
                () -> Assertions.assertEquals (1, kalahDTO.getId ().intValue ()),
                () -> Assertions.assertEquals ("http://localhost:8080/games/1" , kalahDTO.getUri ())
        );

    }


    @DisplayName ("Should make moves for selected pit id.")
    @Test
    public void should_make_moves_for_players(){

        when (kalahGameRepository.findByIdAndGameStatus (1,Status.IN_PROGRESS)).
                thenReturn (Optional.of (getKalahGame ()));
        when (kalahGameRepository.save (any ())).thenReturn (getKalahGame ());
        when (kalahGameMapper.convertToDTO (any ())).thenReturn (getKalahMoveDTO());
        when (kalahGameServiceHelper.makeMove (any (),anyInt ())).thenReturn (getKalahGame ());

        KalahMoveDTO kalahMoveDTO = kalahGameService.makeMove (1, 1);

        Assertions.assertAll (
                () -> Assertions.assertEquals (1, kalahMoveDTO.getId ().intValue ()),
                () -> Assertions.assertEquals ("http://localhost:8080/games/1" , kalahMoveDTO.getUri ())
        );
    }

    @DisplayName ("Should throw exception if gane is not found.")
    @Test
    public void should_throw_exception_when_gameid_is_invalid(){
        Exception exception = Assertions.assertThrows(KalahGameNotFoundException.class, () ->
               kalahGameService.makeMove (1,1) );
        Assertions.assertEquals("Game with id: 1 not found.", exception.getMessage());
    }

    private KalahGame getKalahGame(){
        KalahGame kalahGame = new KalahGame ();
        kalahGame.setId (1);
        return kalahGame;
    }

    private KalahDTO getNewKalahDTO(){
        return new KalahDTO (1, "http://localhost:8080/games/1");
    }

    private KalahMoveDTO getKalahMoveDTO(){
        Map<Integer,Integer> map = new HashMap<> ();
        map.put (1,6);
        map.put (2,6);
        return new KalahMoveDTO (1,"http://localhost:8080/games/1",map);
    }

}

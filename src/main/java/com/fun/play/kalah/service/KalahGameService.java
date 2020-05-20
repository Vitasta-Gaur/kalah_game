package com.fun.play.kalah.service;

import com.fun.play.kalah.domain.KalahGame;
import com.fun.play.kalah.domain.Status;
import com.fun.play.kalah.dto.KalahMoveDTO;
import com.fun.play.kalah.dto.KalahDTO;
import com.fun.play.kalah.exception.KalahGameNotFoundException;
import com.fun.play.kalah.mapper.KalahGameMapper;
import com.fun.play.kalah.repository.KalahGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KalahGameService implements IKalahGameService{

    private final KalahGameRepository kalahGameRepository;

    private final KalahGameMapper kalahGameMapper;

    private final KalahGameServiceHelper kalahGameServiceHelper;

    public KalahGameService(KalahGameRepository kalahGameRepository , KalahGameMapper kalahGameMapper ,
                            KalahGameServiceHelper kalahGameServiceHelper){
        this.kalahGameRepository = kalahGameRepository;
        this.kalahGameMapper = kalahGameMapper;
        this.kalahGameServiceHelper = kalahGameServiceHelper;
    }

    @Override
    public KalahDTO createKalahGame() {
        final KalahGame kalahGame =  kalahGameRepository.save (new KalahGame ());
        return kalahGameMapper.convertToDto (kalahGame);
    }

    @Override
    public KalahMoveDTO makeMove(final int gameId , final int pitId) {
        final KalahGame kalahGame = kalahGameRepository.findByIdAndGameStatus(gameId,Status.IN_PROGRESS).orElseThrow(
                () -> new KalahGameNotFoundException ("Game with id: " + gameId + " not found."));
        final KalahGame game = kalahGameServiceHelper.makeMove (kalahGame, pitId);
        final KalahGame afterMove = kalahGameRepository.save(game);
        return kalahGameMapper.convertToDTO (afterMove);
    }
}

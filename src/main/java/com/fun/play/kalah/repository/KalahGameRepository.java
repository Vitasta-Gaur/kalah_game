package com.fun.play.kalah.repository;

import com.fun.play.kalah.domain.KalahGame;
import com.fun.play.kalah.domain.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KalahGameRepository extends CrudRepository<KalahGame,Integer> {

    Optional<KalahGame> findByIdAndGameStatus(Integer gameId , Status status);
}

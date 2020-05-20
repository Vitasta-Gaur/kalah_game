package com.fun.play.kalah.service;

import com.fun.play.kalah.dto.KalahMoveDTO;
import com.fun.play.kalah.dto.KalahDTO;

public interface IKalahGameService {

     KalahDTO createKalahGame();

     KalahMoveDTO makeMove(final int gameId , final int pitId);
}

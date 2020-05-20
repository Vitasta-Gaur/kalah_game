package com.fun.play.kalah.exception;

public class InvalidKalahMoveException extends RuntimeException {

    public InvalidKalahMoveException(final String errorDescription){
        super(errorDescription);
    }
}

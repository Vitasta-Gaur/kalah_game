package com.fun.play.kalah.exception;

public class KalahGameNotFoundException extends RuntimeException {

    public KalahGameNotFoundException(final String errorDescription){
        super(errorDescription);

    }
}

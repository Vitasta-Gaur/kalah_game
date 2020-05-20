package com.fun.play.kalah.exception;

public class KalahGameTerminatedException extends RuntimeException {

    public KalahGameTerminatedException(final String errorDescription){
        super(errorDescription);
    }
}

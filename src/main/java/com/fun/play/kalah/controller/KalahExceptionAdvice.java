package com.fun.play.kalah.controller;


import com.fun.play.kalah.dto.ErrorDTO;
import com.fun.play.kalah.exception.InvalidKalahMoveException;
import com.fun.play.kalah.exception.KalahGameNotFoundException;
import com.fun.play.kalah.exception.KalahGameTerminatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Converts exceptions to Http statuses.
 * {@link HttpStatus}
 * {@link ErrorDTO} represents error.
 *
 */

@RestControllerAdvice
public class KalahExceptionAdvice {

    @ExceptionHandler(KalahGameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception ex) {
        return new ErrorDTO (HttpStatus.NOT_FOUND, ex.getMessage ());
    }

    @ExceptionHandler(InvalidKalahMoveException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleInvalidMoveRequest(Exception ex) {
        return new ErrorDTO(HttpStatus.BAD_REQUEST, ex.getMessage ());
    }

    @ExceptionHandler(KalahGameTerminatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDTO handleConflict(KalahGameTerminatedException ex) {
        return new ErrorDTO( HttpStatus.CONFLICT , ex.getMessage ());
    }
}

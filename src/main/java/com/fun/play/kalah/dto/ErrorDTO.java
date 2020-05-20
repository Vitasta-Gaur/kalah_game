package com.fun.play.kalah.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Getter
public class ErrorDTO {

    private HttpStatus httpStatus;
    private String errorDescription;

    public ErrorDTO(final HttpStatus httpStatus , final String errorDescription){
        this.httpStatus = httpStatus;
        this.errorDescription = errorDescription;
    }
}

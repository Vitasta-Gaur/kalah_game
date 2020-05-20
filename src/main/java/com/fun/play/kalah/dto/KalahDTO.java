package com.fun.play.kalah.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class KalahDTO {

    @NotNull
    private Integer id;

    @NotNull
    private String uri;

    public KalahDTO(final Integer id , final String uri) {
        this.uri = uri;
        this.id = id;
    }
}

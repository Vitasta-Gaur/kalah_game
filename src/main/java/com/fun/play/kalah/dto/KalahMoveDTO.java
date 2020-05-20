package com.fun.play.kalah.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class KalahMoveDTO {

    @NotNull
    private Integer id;

    @NotNull
    private String uri;

    @NotNull
    private Map<Integer,Integer> status;

    public KalahMoveDTO(final Integer id , final String uri , final Map<Integer,Integer> status){
        this.status = status;
        this.id = id;
        this.uri = uri;
    }

}

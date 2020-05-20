package com.fun.play.kalah.mapper;

import com.fun.play.kalah.domain.KalahGame;
import com.fun.play.kalah.dto.KalahDTO;
import com.fun.play.kalah.dto.KalahMoveDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriTemplate;

@Component
public class KalahGameMapper {

    private final ModelMapper modelMapper;

    @Value("${kalah.game.uri}")
    private String kalahGameUri;

    public KalahGameMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    private String generateGameUrl(int gameId) {
        final UriTemplate uriTemplate = new UriTemplate (kalahGameUri + gameId);
        return uriTemplate.toString ();
    }

    public KalahDTO convertToDto(final KalahGame kalahGame){
        KalahDTO kalahDTO =  modelMapper.map (kalahGame,KalahDTO.class);
        kalahDTO.setUri (generateGameUrl (kalahGame.getId ()));
        return kalahDTO;
    }

    public KalahMoveDTO convertToDTO(final KalahGame kalahGame){
        KalahMoveDTO kalahMoveDTO = modelMapper.map (kalahGame, KalahMoveDTO.class);
        kalahMoveDTO.setUri (generateGameUrl (kalahGame.getId ()));
        return kalahMoveDTO;
    }
}

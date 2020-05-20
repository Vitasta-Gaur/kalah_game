package com.fun.play.kalah.controller;

import com.fun.play.kalah.dto.KalahMoveDTO;
import com.fun.play.kalah.dto.KalahDTO;
import com.fun.play.kalah.service.KalahGameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class KalahGameControllerTest {

    private static final String URL = "http://localhost:8080/games/1";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KalahGameService gameService;

    @DisplayName ("New Kalah Game should be created.")
    @Test
    public void should_create_new_kalah_game() throws Exception {
        KalahDTO newGame = new KalahDTO (1, URL);
        given(gameService.createKalahGame ()).willReturn(newGame);

        mockMvc.perform(post("/games"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.uri").value(newGame.getUri ()));
    }

    @DisplayName ("Consecutive pits are updated with selected pits stone and move is made.")
    @Test
    public void should_make_move_on_kalah_board() throws Exception {
        KalahMoveDTO gameDTO = new KalahMoveDTO();
        gameDTO.setId (1);
        gameDTO.setUri (URL);
        given(gameService.makeMove(1, 3)).willReturn(gameDTO);

        mockMvc.perform(put("/games/1/pits/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.uri").value(gameDTO.getUri ()));
    }
}

package com.fun.play.kalah.controller;

import com.fun.play.kalah.dto.KalahMoveDTO;
import com.fun.play.kalah.dto.KalahDTO;
import com.fun.play.kalah.service.KalahGameService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
@Api(value="Kalah", description = "Kalah endpoints ", tags=("kalah"))
public class KalahGameController {

    private final KalahGameService kalahGameService;

    public KalahGameController(KalahGameService kalahGameService) {
        this.kalahGameService = kalahGameService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Create the game", notes="Creates new Kalah game")
    public KalahDTO createNewGame() {
        return kalahGameService.createKalahGame ();
    }

    @PutMapping("/{gameId}/pits/{pitId}")
    @ApiOperation(value="Make a move")
    public KalahMoveDTO makeMove(@PathVariable int gameId,
                                 @PathVariable int pitId) {

        return kalahGameService.makeMove(gameId, pitId);
    }


}

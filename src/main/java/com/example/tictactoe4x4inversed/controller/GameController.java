package com.example.tictactoe4x4inversed.controller;

import com.example.tictactoe4x4inversed.model.Game;
import com.example.tictactoe4x4inversed.model.Move;
import com.example.tictactoe4x4inversed.model.Player;
import com.example.tictactoe4x4inversed.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GameController {

    private final GameService gameService;
    private Game game;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/start")
    public ResponseEntity<Game> startGame(@RequestParam("starting-player") String startingPlayer) {
        game = gameService.startGame(Player.valueOf(startingPlayer));
        return ResponseEntity.ok(game);
    }

    @PostMapping("/makeMove")
    public ResponseEntity<Game> makeMove(@RequestBody Move move) {
        game = gameService.makeUserMove(game, move);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/game")
    public ResponseEntity<Game> getGame() {
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    @PostMapping("/reset")
    public ResponseEntity<Game> resetGame() {
        game = gameService.resetGame();
        return ResponseEntity.ok(game);
    }

}

package com.example.tictactoe4x4inversed.model;

import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Data
@Component
public class Game {

    private Board board;

    private boolean isGameOver;

    private Player winner;

    public Game() {
        board = new Board();
    }
}


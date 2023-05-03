package com.example.tictactoe4x4inversed.model;

import lombok.Data;

@Data
public class Game {

    private Board board;

    private boolean isGameOver;

    private Player winner;

    public Game() {
        board = new Board();
    }
}


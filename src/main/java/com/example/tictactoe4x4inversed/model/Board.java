package com.example.tictactoe4x4inversed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Board  {

    public static final int BOARD_SIZE = 4;

    private char[][] board;

    @JsonIgnore
    private Player winner;

    @JsonIgnore
    private Player currentPlayer;

    @JsonIgnore
    private int score;

    public Board() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = ' ';
            }
        }
        score = 0;
    }


}

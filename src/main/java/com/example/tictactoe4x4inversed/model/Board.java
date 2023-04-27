package com.example.tictactoe4x4inversed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Board  {
    public static final int BOARD_SIZE = 4;
    private char[][] board;
    @JsonIgnore
    private Player winner;

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
    @JsonIgnore
    public boolean isGameOver() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((board[i][0] == 'X' && board[i][1] == 'X' && board[i][2] == 'X' && board[i][3] == 'X') ||
                    (board[0][i] == 'X' && board[1][i] == 'X' && board[2][i] == 'X' && board[3][i] == 'X')) {

                return true;
            }
        }

        if ((board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X' && board[3][3] == 'X') ||
                (board[0][3] == 'X' && board[1][2] == 'X' && board[2][1] == 'X' && board[3][0] == 'X')) {
            return true;
        }
        return false;
    }
    @JsonIgnore
    public boolean isEndPossible() {

        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((board[i][0] == 'X' && board[i][1] == 'X' && board[i][2] == 'X' && board[i][3] == 'X') ||
                    (board[0][i] == 'X' && board[1][i] == 'X' && board[2][i] == 'X' && board[3][i] == 'X')) {
                winner = Player.USER;

                return true;
            }
        }

        if ((board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X' && board[3][3] == 'X') ||
                (board[0][3] == 'X' && board[1][2] == 'X' && board[2][1] == 'X' && board[3][0] == 'X')) {

            winner = Player.USER;

            return true;
        }

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] == ' ') {
                    return false;
                }
            }
        }
        return false;
    }
    @JsonIgnore
    public List<Move> getPossibleMoves() {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == ' ') {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }
    public void makeMove(Move move) {
        int x = move.getRow();
        int y = move.getCol();

        if (board[x][y] == ' ') {
            board[x][y] = 'X';
        }
    }

    public void undoMove(Move move) {
        int x = move.getRow();
        int y = move.getCol();
        if (board[x][y] == 'X') {
            board[x][y] = ' ';
        }
    }
    @JsonIgnore
    public int getScore() {
        if (isEndPossible()) {
            if (winner == Player.COMPUTER) {
                return 100 - getMoveCount();
            }
            else if (winner == Player.USER) {
                return -100 + getMoveCount();
            }
            else {
                return 0;
            }
        }
        return score;
    }

    private int getMoveCount() {
        int count = 0;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != ' ') {
                    count++;
                }
            }
        }
        return count;
    }

}

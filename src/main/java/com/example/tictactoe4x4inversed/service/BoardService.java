package com.example.tictactoe4x4inversed.service;

import com.example.tictactoe4x4inversed.model.Board;
import com.example.tictactoe4x4inversed.model.Move;
import com.example.tictactoe4x4inversed.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.tictactoe4x4inversed.model.Board.BOARD_SIZE;

@Service
public class BoardService {

    public boolean isGameOver(Board board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if ((board.getBoard()[i][0] == 'X' && board.getBoard()[i][1] == 'X' && board.getBoard()[i][2] == 'X' && board.getBoard()[i][3] == 'X') ||
                    (board.getBoard()[0][i] == 'X' && board.getBoard()[1][i] == 'X' && board.getBoard()[2][i] == 'X' && board.getBoard()[3][i] == 'X')) {

                board.setWinner(swapPlayer(board.getCurrentPlayer()));

                return true;
            }
        }

        if ((board.getBoard()[0][0] == 'X' && board.getBoard()[1][1] == 'X' && board.getBoard()[2][2] == 'X' && board.getBoard()[3][3] == 'X') ||
                (board.getBoard()[0][3] == 'X' && board.getBoard()[1][2] == 'X' && board.getBoard()[2][1] == 'X' && board.getBoard()[3][0] == 'X')) {

            board.setWinner(swapPlayer(board.getCurrentPlayer()));

            return true;
        }

        return false;
    }


    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board.getBoard()[i][j] == ' ') {
                    moves.add(new Move(i, j));
                }
            }
        }
        return moves;
    }

    public void makeMove(Board board, Move move) {
        int x = move.getRow();
        int y = move.getCol();

        if (board.getBoard()[x][y] == ' ') {
            board.getBoard()[x][y] = 'X';
        }
        Player nextPlayer = swapPlayer(board.getCurrentPlayer());
        board.setCurrentPlayer(nextPlayer);
    }

    public void undoMove(Board board, Move move) {
        int x = move.getRow();
        int y = move.getCol();
        if (board.getBoard()[x][y] == 'X') {
            board.getBoard()[x][y] = ' ';
        }
        Player playerBefore = swapPlayer(board.getCurrentPlayer());
        board.setCurrentPlayer(playerBefore);
    }

    public int getScore(Board board) {
            if (board.getWinner() == Player.COMPUTER) return 13 + getMoveCount(board);
            else if (board.getWinner() == Player.USER) return -13 - getMoveCount(board);
            else return 0;
    }

    private int getMoveCount(Board board) {
        int count = 0;
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board.getBoard()[row][col] != ' ') {
                    count++;
                }
            }
        }
        return count;
    }

    private Player swapPlayer(Player player) {
        return player == Player.USER ? Player.COMPUTER : Player.USER;
    }
}

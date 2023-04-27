package com.example.tictactoe4x4inversed.service;

import com.example.tictactoe4x4inversed.model.Board;
import com.example.tictactoe4x4inversed.model.Game;
import com.example.tictactoe4x4inversed.model.Move;
import com.example.tictactoe4x4inversed.model.Player;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;


@Service
public class GameService {

    public Game startGame(Player startingPlayer) {
        Game game = new Game();
        if (startingPlayer == Player.COMPUTER) {
            Move computerMove = findBestMove(game.getBoard());
            game.getBoard().makeMove(computerMove);
        }
        return game;
    }

    public Game makeMove(Game game, Move move) {
        Board board = game.getBoard();
        board.makeMove(move);
        System.out.println(move);
        if (!board.isEndPossible()) {
            Move computerMove = findBestMove(board);
            board.makeMove(computerMove);
            if (board.isEndPossible()) board.setWinner(Player.USER);
        } else {
            board.setWinner(Player.COMPUTER);
        }
        game.setBoard(board);
        return game;
    }
    public Game makeUserMove(Game game, Move move) {
        Board board = game.getBoard();
        makeMove(game,move);

        if (board.isGameOver()) {
            game.setGameOver(true);
            game.setWinner(board.getWinner());
        }
        return game;
    }

    public Game resetGame() {
        return new Game();
    }

    public Move findBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        for (Move move : board.getPossibleMoves()) {
            board.makeMove(move);
            int moveScore = minimax(board, Integer.MIN_VALUE, Integer.MAX_VALUE,true);
            board.undoMove(move);

            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMove = move;
            }
        }
        return bestMove;
    }


    private int minimax(Board board, int alpha, int beta, boolean maximizing) {
        if (board.isEndPossible()) return board.getScore();
        int bestScore = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : board.getPossibleMoves()) {

            board.makeMove(move);
            int moveScore = minimax(board, alpha, beta, !maximizing);
            board.undoMove(move);

            bestScore = maximizing ? Math.max(bestScore, moveScore) : Math.min(bestScore, moveScore);

            if (maximizing) alpha = Math.max(alpha, bestScore);
            else beta = Math.min(beta, bestScore);

            if (beta <= alpha) break;
        }
        return bestScore;
    }

}

package com.example.tictactoe4x4inversed.service;

import com.example.tictactoe4x4inversed.model.Board;
import com.example.tictactoe4x4inversed.model.Game;
import com.example.tictactoe4x4inversed.model.Move;
import com.example.tictactoe4x4inversed.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.BitSet;


@Service
@RequiredArgsConstructor
public class GameService {

    private final BoardService boardService;

    public Game startGame(Player startingPlayer) {

        Game game = new Game();

        if (startingPlayer == Player.COMPUTER) {

            Move computerMove = findBestMove(game.getBoard());
            game.getBoard().setCurrentPlayer(Player.USER);

            boardService.makeMove(game.getBoard(), computerMove);

        } else game.getBoard().setCurrentPlayer(Player.COMPUTER);

        return game;
    }

    public Game makeMove(Game game, Move move) {

        Board board = game.getBoard();
        boardService.makeMove(board, move);

        if (!boardService.isGameOver(board)) {

            Move computerMove = findBestMove(board);
            boardService.makeMove(board, computerMove);

            System.out.println("Computer move: " + computerMove);

            if (boardService.isGameOver(board)) game.setWinner(Player.USER);

        } else game.setWinner(Player.COMPUTER);

        game.setBoard(board);

        return game;
    }

    public Game makeUserMove(Game game, Move move) {
        if (game.isGameOver()) return game;
        Board board = game.getBoard();

        System.out.println("User move: " + move);
        makeMove(game, move);

        if (boardService.isGameOver(board)) {

            game.setGameOver(true);
            game.setWinner(board.getWinner());
            System.out.println("Game over, winner is: " + board.getWinner());
        }

        return game;
    }

    public Game resetGame() {
        return new Game();
    }

    public Move findBestMove(Board board) {
        int bestScore = Integer.MIN_VALUE;
        Move bestMove = null;

        for (Move move : boardService.getPossibleMoves(board)) {
            boardService.makeMove(board, move);
            int moveScore = minimax(board, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
            boardService.undoMove(board, move);

            if (moveScore > bestScore) {
                bestScore = moveScore;
                bestMove = move;
            }
        }

        return bestMove;
    }


    private int minimax(Board board, int alpha, int beta, boolean maximizing) {
        if (boardService.isGameOver(board)) return boardService.getScore(board);
        int bestScore = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Move move : boardService.getPossibleMoves(board)) {

            boardService.makeMove(board, move);
            int moveScore = minimax(board, alpha, beta, !maximizing);
            boardService.undoMove(board, move);

            bestScore = maximizing ? Math.max(bestScore, moveScore) : Math.min(bestScore, moveScore);

            if (maximizing) alpha = Math.max(alpha, bestScore);
            else beta = Math.min(beta, bestScore);

            if (beta <= alpha) break;
        }
        return bestScore;
    }

}

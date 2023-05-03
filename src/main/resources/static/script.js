const BOARD_SIZE = 4;
let startingPlayer = "";
let gameBoard = Array(BOARD_SIZE).fill().map(() => Array(BOARD_SIZE).fill(''));

document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("user-starts").addEventListener("click", () => {
        startingPlayer = "USER";
        document.getElementById("buttons").style.display = "none";
        document.getElementById("new-game").style.display = "inline-block";
        createGameBoard();
    });
    document.getElementById("computer-starts").addEventListener("click", () => {
        startingPlayer = "COMPUTER";
        document.getElementById("buttons").style.display = "none";
        document.getElementById("new-game").style.display = "inline-block";
        createGameBoard();
    });
    document.getElementById("new-game").addEventListener("click", () => {
        location.reload();
    });
    document.getElementById("new-game").style.display = "none";

});

function createGameBoard() {
    let gameBoardElement = document.getElementById("game-board");
    gameBoardElement.innerHTML = "";
    fetch(`http://localhost:8080/start?starting-player=${startingPlayer}`, {method: "POST"})
        .then(response => response.json())
        .then(data => {
            for (let i = 0; i < BOARD_SIZE; i++) {
                let row = document.createElement("div");
                row.className = "game-row";
                for (let j = 0; j < BOARD_SIZE; j++) {
                    let cell = document.createElement("div");
                    cell.className = "border game-cell m-1";
                    cell.dataset.x = i;
                    cell.dataset.y = j;

                    cell.addEventListener("click", function () {
                        let x = this.dataset.x;
                        let y = this.dataset.y;
                        if (gameBoard[x][y] === '') {
                            gameBoard[x][y] = 'X';
                            if (gameBoard[x][y] === 'X') {
                                fetch("http://localhost:8080/makeMove", {
                                    method: "POST",
                                    headers: {"Content-Type": "application/json"},
                                    body: JSON.stringify({row: x, col: y})
                                })
                                    .then(response => response.json())
                                    .then(gameData => {
                                        data = gameData;
                                        updateGameBoard(gameData.board.board);
                                        if (gameData.gameOver === true && gameData.winner === "USER") {
                                            alert("You won!");
                                        } else if (gameData.gameOver === true && gameData.winner === "COMPUTER") {
                                            alert("You lost!");
                                        }
                                    });
                            }

                        }
                    });

                    if (data.board.board && data.board.board[i]) {
                        cell.textContent = data.board.board[i][j];
                    }

                    row.appendChild(cell);
                }
                gameBoardElement.appendChild(row);
            }
        });
}

function updateGameBoard(board) {
    for (let i = 0; i < BOARD_SIZE; i++) {
        for (let j = 0; j < BOARD_SIZE; j++) {
            let cell = document.querySelector(`[data-x="${i}"][data-y="${j}"]`);
            cell.textContent = board[i][j];
        }
    }
}


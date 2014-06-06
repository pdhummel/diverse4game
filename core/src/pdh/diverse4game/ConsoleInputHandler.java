package pdh.diverse4game;

import java.util.*;
public class ConsoleInputHandler {
    Game game = null;
    GameState gameState = null;

    int x = -1;
    int y = -1;
    GamePiece selectedPiece = null;


    public ConsoleInputHandler(Game game) {
        this.game = game;
        this.gameState = game.getGameState();
    }

    public void handleState(String input) {
        String nullString = null;
        gameState.setMessage(nullString);
        int currentState = gameState.getState();
        switch (currentState) {
            case GameState.NONE: 
                handleNone(input);
                break;
            case GameState.NEW_GAME: 
                handleNewGame(input);
                break;
            case GameState.PLAYERS_DEFINED: 
                handlePlayersDefined(input);
                break;
            case GameState.PLAYER_ORDER_DEFINED: 
                handlePlayerOrderDefined(input);
                break;
            case GameState.GAME_STARTED: 
                handleGameStarted(input);
                break;
            case GameState.PLAYER_TURN_STARTED: 
                handlePlayerTurnStarted(input);
                break;
            case GameState.PLAYER_PLAYED_PIECE: 
                handlePlayerPlayedPiece(input);
                break;
            case GameState.PLAYER_PICKED_PIECE: 
                handlePlayerPickedPiece(input);
                break;
            case GameState.PLAYER_TURN_ENDED: 
                handlePlayerTurnEnded(input);
                break;
            case GameState.FOUND_VICTORY: 
                handleFoundVictory(input);
                break;
            case GameState.NO_MORE_SPACES: 
                handleNoMoreSpaces(input);
                break;
            case GameState.GAME_OVER: 
                handleGameOver(input);
                break;

        }
    }

    private void handleNone(String input) {
    }

    private void handleNewGame(String input) {
        // Can exit.
        if (isValidExitGame(input))
            game.exit();

        // Defining players.
        // else if
        
        else 
            gameState.setMessage("Unrecognized input.");
            
    }

    private void handlePlayersDefined(String input) {
        // Can exit.
        if (isValidExitGame(input))
            game.exit();

        // Setting player order.
        // else if
        
        else 
            gameState.setMessage("Unrecognized input.");

    }

    private void handlePlayerOrderDefined(String input) {
    }

    private void handleGameStarted(String input) {
        // Can quit.
        if (isValidQuitGame(input))
            game.quitGame();
        // Pick piece.
        else if (isValidAvailablePiece(input)) {
            try {
                game.pickPiece(selectedPiece);
            }
            catch(Exception e) {
                gameState.setMessage("An error occurred while trying to pick the piece.");
            }
        }
        else 
            gameState.setMessage("Unrecognized input.");
    }

    private void handlePlayerTurnStarted(String input) {
        // Can quit.
        if (isValidQuitGame(input))
            game.quitGame();
        // Play piece.
        else if (isValidLocation(input)) {
            try {
                game.playPiece(x, y);
            }
            catch(Exception e) {
                gameState.setMessage("An error occurred while trying to play the piece.");
            }
        }
        else 
            gameState.setMessage("Unrecognized input.");

    }

    private void handlePlayerPlayedPiece(String input) {
        // Can quit.
        if (isValidQuitGame(input))
            game.quitGame();
        // Pick piece.
        else if (isValidAvailablePiece(input)) {
            try {
                game.pickPiece(selectedPiece);
            }
            catch(Exception e) {
                gameState.setMessage("An error occurred while trying to pick the piece.");
            }
        }
        else 
            gameState.setMessage("Unrecognized input.");
    }

    private void handlePlayerPickedPiece(String input) {
    }

    private void handlePlayerTurnEnded(String input) {
    }

    private void handleFoundVictory(String input) {
    }

    private void handleNoMoreSpaces(String input) {
    }

    private void handleGameOver(String input) {
        // Can exit.
        if (isValidExitGame(input))
            game.exit();
        // Can start new game.
        if (isValidNewGame(input)) {
            try {
                game.startNewGame();
            }
            catch(Exception e) {
                gameState.setMessage("An error occurred while trying to start the game.");
            }
        }          
    }



    private boolean isValidNewGame(String input) {
        boolean bOk = false;
        if ((input.trim()).equalsIgnoreCase("n"))
            bOk = true;
        return bOk;
    }

    private boolean isValidExitGame(String input) {
        boolean bOk = false;
        if ((input.trim()).equalsIgnoreCase("x"))
            bOk = true;
        return bOk;
    }

    private boolean isValidQuitGame(String input) {
        boolean bOk = false;
        if ((input.trim()).equalsIgnoreCase("q"))
            bOk = true;
        return bOk;

    }

    private boolean isValidLocation(String input) {
        boolean bOk = false;
        // Parse x,y into 2 values.
        StringTokenizer st = new StringTokenizer(input, ",");
        String sX = null;
        String sY = null;
        int iX = -1;
        int iY = -1;
        if (st.hasMoreElements()) {
            sX = st.nextToken();
        }
        if (st.hasMoreElements()) {
            sY = st.nextToken();
        }
        try {
            iX = (new Integer(sX)).intValue();
            iY = (new Integer(sY)).intValue();
        }
        catch(Exception eIgnore) {}

        // Make sure x and y are between 1 and 4
        if (iX >= 1 && iX <= 4 && iY >= 1 && iY <= 4) {
            // Make sure the space isn't already occupied
            GameBoard gameBoard = game.getGameBoard();
            GameSpace gameSpace = gameBoard.getGameSpace(iX-1, iY-1);
            GamePiece gamePiece = gameSpace.getGamePiece();
            if (gamePiece == null) {
                this.x = iX;
                this.y = iY;
                bOk = true;
            }
        }

        return bOk;

    }

    private boolean isValidAvailablePiece(String input) {
        boolean bOk = false;
        // Make sure the value is between 1 and 16
        int selection = 0;
        try {
            selection = (new Integer((input).trim())).intValue();
        }
        catch(Exception eIgnore) {}
        
        // Check if the piece is still available
        if (selection >= 1 && selection <= 16) {
            GamePieces gamePieces = game.getGamePieces();            
            Collection allGamePieces = gamePieces.getGamePieces();            
            Collection availableGamePieces = gameState.getAvailablePieces();
            TreeSet treeAllGamePieces = new TreeSet(allGamePieces);
            Object o[] = treeAllGamePieces.toArray();
            Object one = o[selection-1];
            GamePiece gamePiece = (GamePiece)one;
            if (availableGamePieces.contains(gamePiece)) {
                selectedPiece = gamePiece;
                bOk = true;
            }
        }
        return bOk;
    }


}

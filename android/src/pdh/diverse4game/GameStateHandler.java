package pdh.diverse4game;

//import java.util.*;
public class GameStateHandler {
    Game game = null;
    GameState gameState = null;
    GamePresenter gamePresenter = null;


    public GameStateHandler(Game game, GamePresenter gamePresenter) {
        this.game = game;
        this.gameState = game.getGameState();
        this.gamePresenter = gamePresenter;
    }

    public void handleState() {
        int currentState = gameState.getState();
        switch (currentState) {
            case GameState.NONE: 
                handleNone();
                break;
            case GameState.NEW_GAME: 
                handleNewGame();
                break;
            case GameState.PLAYERS_DEFINED: 
                handlePlayersDefined();
                break;
            case GameState.PLAYER_ORDER_DEFINED: 
                handlePlayerOrderDefined();
                break;
            case GameState.GAME_STARTED: 
                handleGameStarted();
                break;
            case GameState.PLAYER_TURN_STARTED: 
                handlePlayerTurnStarted();
                break;
            case GameState.PLAYER_PLAYED_PIECE: 
                handlePlayerPlayedPiece();
                break;
            case GameState.PLAYER_PICKED_PIECE: 
                handlePlayerPickedPiece();
                break;
            case GameState.PLAYER_TURN_ENDED: 
                handlePlayerTurnEnded();
                break;
            case GameState.FOUND_VICTORY: 
                handleFoundVictory();
                break;
            case GameState.NO_MORE_SPACES: 
                handleNoMoreSpaces();
                break;
            case GameState.GAME_OVER: 
                handleGameOver();
                break;

        }
    }

    private void handleNone() {
    }

    private void handleNewGame() {
    }

    private void handlePlayersDefined() {
    }

    private void handlePlayerOrderDefined() {
    }

    private void handleGameStarted() {
        gamePresenter.showAvailablePieces();
        Player currentPlayer = gameState.getCurrentPlayer();        
        if (currentPlayer.isComputer()) {
            ComputerAI ai = new ComputerAI(game);
            GamePiece gamePiece = ai.pickFirstPiece();
            try {
                game.pickPiece(gamePiece);
                gamePresenter.afterAI();
            } catch(Exception e) {
                System.out.println(this.getClass().getName() + 
                " - An unexpected error occurred:\n" + e);
                System.exit(-1);
            }
        }
        else {       
            String errorMsg = gameState.getMessage();
            if (errorMsg != null && errorMsg.length() > 0)
                gamePresenter.showErrorMessage();
            gamePresenter.showQuitOption();
            gamePresenter.showPickPieceOption();
            gamePresenter.startGame();
            gamePresenter.allowInput();
        }                 
    }

    private void handlePlayerTurnStarted() {
        gamePresenter.showBoard();
        gamePresenter.showAvailablePieces();
        gamePresenter.showCurrentPiece();
        Player currentPlayer = gameState.getCurrentPlayer();
        if (currentPlayer.isComputer()) {
            ComputerAI ai = new ComputerAI(game);
            ComputerAI.ComputerMove move = ai.playPiece();
            try {
                game.playPiece(move.getX()+1, move.getY()+1);
            } catch(Throwable t) {
                System.out.println(this.getClass().getName() +
                " - An unexpected error occurred:\n" + t);
                System.exit(-1);
            }
            gameState.setComputerMove(move);
            gamePresenter.afterAI();
        }
        else {       
            String errorMsg = gameState.getMessage();
            if (errorMsg != null && errorMsg.length() > 0)
                gamePresenter.showErrorMessage();
            gamePresenter.showQuitOption();
            gamePresenter.showPlayPieceOption();
            gamePresenter.allowInput();
        }
    }

    private void handlePlayerPlayedPiece() {
        gamePresenter.showBoard();
        gamePresenter.showAvailablePieces();
        Player currentPlayer = gameState.getCurrentPlayer();
        if (currentPlayer.isComputer()) {
            ComputerAI.ComputerMove cm = gameState.getComputerMove();
            try {
                game.pickPiece(cm.getPickedPiece());
                gamePresenter.afterAI();                
            } catch (Exception e) {
                System.out.println(this.getClass().getName() +
                " - An unexpected error occurred:\n" + e);
                System.exit(-1);
            }
        }
        else {        
            String errorMsg = gameState.getMessage();
            if (errorMsg != null && errorMsg.length() > 0)
                gamePresenter.showErrorMessage();
            gamePresenter.showQuitOption();
            gamePresenter.showPickPieceOption();
            gamePresenter.allowInput();
        }
    }

    private void handlePlayerPickedPiece() {
    }

    private void handlePlayerTurnEnded() {
    }

    private void handleFoundVictory() {
        gamePresenter.showBoard();
        gamePresenter.showAvailablePieces();
        String errorMsg = gameState.getMessage();
        if (errorMsg != null && errorMsg.length() > 0)
            gamePresenter.showErrorMessage();
        gamePresenter.showVictoryMessage();
        gamePresenter.allowInput();
    }

    private void handleNoMoreSpaces() {
        gamePresenter.showBoard();
        gamePresenter.showAvailablePieces();
        String errorMsg = gameState.getMessage();
        if (errorMsg != null && errorMsg.length() > 0)
            gamePresenter.showErrorMessage();
        gamePresenter.showBoardFullMessage();
        gamePresenter.allowInput();
    }

    private void handleGameOver() {
        int priorState = gameState.getPriorState();
        if (priorState == GameState.NO_MORE_SPACES) {
            gamePresenter.showBoard();
            gamePresenter.showAvailablePieces();
            gamePresenter.showBoardFullMessage();
        }
        else if (priorState == GameState.FOUND_VICTORY) {
            gamePresenter.showBoard();
            gamePresenter.showAvailablePieces();
            gamePresenter.showVictoryMessage();
        }
        gamePresenter.endItAll();
    }
}

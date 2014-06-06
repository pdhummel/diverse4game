package pdh.diverse4game;

import java.util.*;

public class Game {
    
    private GameBoard gameBoard;
    private Player player[] = new Player[2];
    private GamePieces gamePieces;
    private GameState gameState;

    
    public Game() {
        player[0] = new Player("You", false);
        player[1] = new Player("Computer", true);
        gameBoard = new GameBoard();
        gameState = new GameState();
        gamePieces = new GamePieces();    
    }
    
    
    public GameState getGameState() {
        return gameState;
    }

    public GamePieces getGamePieces() {
        return gamePieces;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Player getPlayer(int number) {
        return player[number];
    }


    public void createNewGame() throws InvalidStateTransistion {
        int currentState = gameState.getState();
        if (currentState  != GameState.NONE &&
            currentState != GameState.GAME_OVER)
            throw new InvalidStateTransistion();

        gameState.setState(GameState.NEW_GAME);
    }


    public void definePlayers() 
        throws InvalidStateTransistion {
        int currentState = gameState.getState();
        if (currentState  != GameState.NEW_GAME)
            throw new InvalidStateTransistion();

        // Players should already have been created in the 
        // constructor.
        
        gameState.setState(GameState.PLAYERS_DEFINED);
    }
    
     
    public void definePlayers(Player player1, Player player2) 
        throws InvalidStateTransistion {
        int currentState = gameState.getState();
        if (currentState  != GameState.NEW_GAME)
            throw new InvalidStateTransistion();
        
        // todo:  Make sure the players aren't null
        
        player[0] = player1;
        player[1] = player2;
        gameState.setState(GameState.PLAYERS_DEFINED);
    }

    public void setPlayerOrder(int firstPlayer) 
        throws InvalidStateTransistion {
        int currentState = gameState.getState();
        if (currentState != GameState.PLAYERS_DEFINED)
            throw new InvalidStateTransistion();
        
        // TODO:  Check firstPlayer for range for exception.
        
        gameState.setCurrentPlayer(player[firstPlayer]);
        gameState.setState(GameState.PLAYER_ORDER_DEFINED);
        this.startNewGame();
    }
    
    public void startNewGame() throws InvalidStateTransistion {
        int currentState = gameState.getState();
        if (currentState != GameState.PLAYER_ORDER_DEFINED &&
            currentState != GameState.GAME_OVER)
            throw new InvalidStateTransistion();

        gameBoard.clearBoard();
        Collection availablePieces = gameState.getAvailablePieces();
        availablePieces.clear();
        availablePieces.addAll(gamePieces.getGamePieces());
        gameState.setState(GameState.GAME_STARTED);
        //startPlayerTurn();
    }
    

    private void startPlayerTurn() {
        gameState.setState(GameState.PLAYER_TURN_STARTED);
        gameState.nextTurn();
    }

    public void playPiece(int x, int y) 
        throws InvalidStateTransistion, InvalidGameBoardLocation,
        UnavailablePieceException {

        int currentState = gameState.getState();        
        // Check state transistion.
        if (currentState != GameState.PLAYER_TURN_STARTED)
            throw new InvalidStateTransistion();
        
        // Check if the piece is in hand.
        if (gameState.getCurrentPiece() == null)
            throw new UnavailablePieceException();
        
        // Try to play the piece.
        gameBoard.playPiece1To4(gameState.getCurrentPiece(), x, y);
        gameState.setCurrentPiece(null);        

        gameState.setState(GameState.PLAYER_PLAYED_PIECE);
        checkPiecePlayed();
        
    }

    private void checkPiecePlayed() {
        Collection availablePieces = gameState.getAvailablePieces();

        // Check for victory.
        GameBoardStatus gbs = new GameBoardStatus(gameBoard);
        boolean victory = gbs.checkForVictory();
        if (victory) {
            GameBoardStatus.VictoryReason victoryReason = gbs.getVictoryReason();
            gameState.setVictoryReason(victoryReason);
            gameState.setState(GameState.FOUND_VICTORY);
            this.endGame();
        }

        // Check for the board being full.
        else if (availablePieces.isEmpty()) {
            gameState.setState(GameState.NO_MORE_SPACES);
            this.endGame();
        }

    }

    public void pickPiece(GamePiece gamePiece) 
        throws InvalidStateTransistion, UnavailablePieceException {

        // Check state transistion.
        int currentState = gameState.getState();
        if (currentState != GameState.PLAYER_PLAYED_PIECE &&
            currentState != GameState.GAME_STARTED)
            throw new InvalidStateTransistion();
        
        // Check if the piece is available.
        Collection availablePieces = gameState.getAvailablePieces();
        if (! availablePieces.contains(gamePiece))
            throw new UnavailablePieceException();

        // Make the piece unavailable.
        availablePieces.remove(gamePiece);

        gameState.setCurrentPiece(gamePiece);
        gameState.setState(GameState.PLAYER_PICKED_PIECE);
        this.endPlayerTurn();

    }
    
    private void endPlayerTurn() {
        gameState.setState(GameState.PLAYER_TURN_ENDED);

        if (gameState.getCurrentPlayer() == player[1])
            gameState.setCurrentPlayer(player[0]);
        else
            gameState.setCurrentPlayer(player[1]);

        startPlayerTurn();
    }


    public void quitGame() {
        endGame();
    }

    private void endGame() {
        gameState.setState(GameState.GAME_OVER);
    }


    public void exit() {
        System.exit(0);
    }
 
    /*
    public Object clone() {
        Game game = new Game();
        GameBoard origGameBoard = this.getGameBoard();
        
        private GameBoard gameBoard;
        private Player player[] = new Player[2];
        private GamePieces gamePieces;
        private GameState gameState;
    }
    */
    
}

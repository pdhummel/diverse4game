package pdh.diverse4game;

import java.util.*;

public class GameState {
    public static final int NONE = 0;
    public static final int NEW_GAME = 10;
    public static final int PLAYERS_DEFINED = 20;
    public static final int PLAYER_ORDER_DEFINED = 30;
    public static final int GAME_STARTED = 35;
    public static final int PLAYER_TURN_STARTED = 40;
    public static final int PLAYER_PLAYED_PIECE = 50;
    public static final int PLAYER_PICKED_PIECE = 60;
    public static final int PLAYER_TURN_ENDED = 70;
    public static final int FOUND_VICTORY = 80;
    public static final int NO_MORE_SPACES = 100;
    public static final int GAME_OVER = 110;
    
    // NONE ->(createNewGame) NEW_GAME
    // NEW_GAME ->(definePlayers) PLAYERS_DEFINED
    // PLAYERS_DEFINED ->(setPlayerOrder) PLAYER_ORDER_DEFINED
    // PLAY_ORDER_DEFINED ->(startNewGame) GAME_STARTED
    // GAME_STARTED ->(pickPiece) PLAYER_PICKED_PIECE
    // PLAYER_TURN_STARTED ->(playPiece) PLAYER_PLAYED_PIECE
    // PLAYER_PLAYED_PIECE -> (pickPiece) PLAYER_PICKED_PIECE
    // PLAYER_PLAYED_PIECE ->(checkPiecePlayed) FOUND_VICTORY, NO_MORE_SPACES
    // PLAYER_PICKED_PIECE ->(endPlayerTurn) PLAYER_TURN_ENDED
    // PLAYER_TURN_ENDED ->(startPlayerTurn) PLAYER_TURN_STARTED
    // FOUND_VICTORY ->(endGame) GAME_OVER
    // NO_MORE_SPACES ->(endGame) GAME_OVER
    // QUIT_GAME ->(endGame) GAME_OVER
    // GAME_OVER ->(createNewGame) NEW_GAME
    // GAME_OVER ->(startNewGame) GAME_STARTED
    
    private int priorState;    
    private int currentState;
    private Player currentPlayer;
    private GamePiece currentPiece;
    private ArrayList alAvailablePieces = new ArrayList();
    private String message = null;
    private int gameTurn = 0;
    private ComputerAI.ComputerMove computerMove = null;
    
    private GameBoardStatus.VictoryReason victoryReason =
        new GameBoardStatus.VictoryReason(); 

    
    public GameState() {
    }
    
    public void setComputerMove(ComputerAI.ComputerMove cm) {
        this.computerMove = cm;
    }
    
    public ComputerAI.ComputerMove getComputerMove() {
        return computerMove;
    }

    public Collection getAvailablePieces() {
        return this.alAvailablePieces;
    }

    public int getState() {
        return currentState;
    }

    public void setState(int state) {
        priorState = currentState;
        this.currentState = state;
    }    

    public int getGameTurn() {
        return gameTurn;
    }
    
    public void setGameTurn(int turn) {
        this.gameTurn = turn;
    }
        
    public void nextTurn() {
        this.gameTurn++;        
    }
    
    public int getPriorState() {
        return priorState;
    }
    
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public GamePiece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(GamePiece gamePiece) {
        this.currentPiece = gamePiece;
    }


    public void setVictoryReason(GameBoardStatus.VictoryReason victoryReason) {
        this.victoryReason = victoryReason;
    }
    
    public GameBoardStatus.VictoryReason getVictoryReason() {
        return this.victoryReason;
    }

    

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object clone() {
        GameState gs = new GameState();
        gs.setCurrentPiece(this.currentPiece);
        gs.setCurrentPlayer(this.currentPlayer);
        gs.setState(this.priorState);
        gs.setState(this.currentState);
        gs.setMessage(this.message);
        gs.setGameTurn(this.gameTurn);
        gs.setVictoryReason(this.victoryReason);
        Collection availablePieces = gs.getAvailablePieces();
        availablePieces.addAll(this.alAvailablePieces);
        return (Object)gs;
    }
}

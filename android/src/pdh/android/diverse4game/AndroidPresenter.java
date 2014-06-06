package pdh.android.diverse4game;

import pdh.diverse4game.GamePresenter;
import pdh.android.diverse4game.GuiPanel;
import pdh.diverse4game.*;
import java.util.*;
import android.widget.*;
import android.view.*;

public class AndroidPresenter implements GamePresenter {
    private Game game;
    private GameState gameState;
    private GameStateHandler gsh;
    private boolean done = false;
    private GuiPanel guiPanel = null;
    private GUIInputHandler guiInputHandler = null;
    
    public AndroidPresenter(GuiPanel guiPanel) {
    	this.guiPanel = guiPanel;
    }
    
    public void initialize() {
        game = new Game();
        gameState = game.getGameState();
        guiInputHandler = new GUIInputHandler(game);        
        gsh = new GameStateHandler(game, this);
        try {
            game.createNewGame();
            game.definePlayers();
            game.setPlayerOrder(1);
        }
        catch(Exception eIgnore) {}    	
    }
    
    public GUIInputHandler getInputHandler() {
        return guiInputHandler;
    }

    public void checkState() {
        gsh.handleState();    
    }
	
    
	public void showBoard() {
        GameBoard gameBoard = game.getGameBoard();
        guiPanel.refreshGameBoardView(gameBoard);
	}

	public void showAvailablePieces() {
		guiPanel.refreshAvailablePiecesView(game, gameState);
	}

	public void showCurrentPiece() {
        GamePiece gamePiece = gameState.getCurrentPiece();
        guiPanel.refreshCurrentPieceView(gamePiece);
	}

	public void showQuitOption() {
	}

	public void showPickPieceOption() {
        GamePiece gamePiece = null;
        guiPanel.refreshCurrentPieceView(gamePiece);  
        guiPanel.setAllowPickPiece(true);      
        guiPanel.refreshMessage("Pick a piece to give to your opponent.");  
	}

	public void showPlayPieceOption() {
        guiPanel.setAllowPlayPiece(true);
        guiPanel.refreshMessage("Play the piece by clicking on the game board."); 
	}

	public void showErrorMessage() {
	}

	public void showVictoryMessage() {
        StringBuffer sb = new StringBuffer();
        Player player = gameState.getCurrentPlayer();
        if (player.isComputer()) {
            sb.append("The computer won the game.  ");
        }
        else 
            sb.append("You won the game.  ");

        sb.append("Victory was found on ");        
        GameBoardStatus.VictoryReason victoryReason = gameState.getVictoryReason();
        sb.append(GameBoardStatus.VICTORY_LOCATION_MESSAGE[victoryReason.getStatus()]);

        sb.append(" - ");
        GamePieces gamePieces = game.getGamePieces();  
        GamePieceAttribute gpa[] = gamePieces.getGamePieceAttributes();
        int attr = victoryReason.getVictoryAttribute();
        boolean attrValue = victoryReason.getVictoryAttributeValue();
        sb.append(gpa[attr-1].getAttributeValue(attrValue));      
        sb.append(" smileys.");        
        guiPanel.refreshMessage(sb.toString());	
        guiPanel.refreshNew(true);
    }
	


	public void showBoardFullMessage() {
	}

	public String allowInput() {
        String input = null;
        return input;
	}

	public void endItAll() {
	}

	public void afterAI() {
		gsh.handleState();
	}

	public void startGame() {
        GameBoard gameBoard = game.getGameBoard();
        guiPanel.refreshGameBoardView(gameBoard);
	}
	

	
	

}

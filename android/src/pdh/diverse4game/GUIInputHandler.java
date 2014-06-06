package pdh.diverse4game;

import java.util.*;
public class GUIInputHandler {
    Game game = null;
    GameState gameState = null;

    int x = -1;
    int y = -1;
    GamePiece selectedPiece = null;


    public GUIInputHandler(Game game) {
        this.game = game;
        this.gameState = game.getGameState();
    }


    public boolean pickPiece(String input) {
        boolean ok = false;
        int selection = 0;
        try {
            selection = (new Integer((input).trim())).intValue();
        }
        catch(Exception eIgnore) {}
        
        if (selection >= 0 && selection < 16) {        
            GamePieces gamePieces = game.getGamePieces();            
            Collection allGamePieces = gamePieces.getGamePieces();            
            Collection availableGamePieces = gameState.getAvailablePieces();
            TreeSet treeAllGamePieces = new TreeSet(allGamePieces);
            Object o[] = treeAllGamePieces.toArray();
            Object one = o[selection];
            GamePiece gamePiece = (GamePiece)one;
            if (availableGamePieces.contains(gamePiece)) {
                selectedPiece = gamePiece;
                try {
                    game.pickPiece(selectedPiece);
                    ok = true;
                }
                catch(Exception e) {
                    gameState.setMessage("An error occurred while trying to pick the piece.");
                }
            }
        }
        return ok;
    }

    public boolean playPiece(String input) {
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
        if (iX >= 0 && iX < 4 && iY >= 0 && iY < 4) {
            // Make sure the space isn't already occupied
            GameBoard gameBoard = game.getGameBoard();
            GameSpace gameSpace = gameBoard.getGameSpace(iX, iY);
            GamePiece gamePiece = gameSpace.getGamePiece();
            if (gamePiece == null) {
                try {
                    game.playPiece(iX+1, iY+1);
                    bOk = true;
                }
                catch(Exception e) {
                    gameState.setMessage("An error occurred while trying to play the piece.");
                }
            }
        }
        return bOk;
    }
    
    public void quit() {
        game.quitGame();
    }

    public void newGame() {
        try {
            game.startNewGame();
        } catch(Exception eIgnore) {
        }
    }

}

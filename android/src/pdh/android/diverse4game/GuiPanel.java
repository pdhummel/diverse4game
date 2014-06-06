package pdh.android.diverse4game;

import pdh.diverse4game.Game;
import pdh.diverse4game.GameBoard;
import pdh.diverse4game.GamePiece;
import pdh.diverse4game.GameState;

public interface GuiPanel {

    public void refreshGameBoardView(GameBoard gameBoard);
	public void refreshAvailablePiecesView(Game game, GameState gameState);
    public void refreshCurrentPieceView(GamePiece gamePiece);
    public void setAllowPlayPiece(boolean b);
    public void setAllowPickPiece(boolean b);
    public void refreshQuit(boolean b);
    public void refreshNew(boolean b);
    public void refreshMessage(String message);
    
}

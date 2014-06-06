package pdh.diverse4game;

public interface GamePresenter {

    public void showBoard();
    public void showAvailablePieces();
    public void showCurrentPiece();
    public void showQuitOption();
    public void showPickPieceOption();
    public void showPlayPieceOption();
    public void showErrorMessage();
    public void showVictoryMessage();
    public void showBoardFullMessage();
    public String allowInput();    
    public void endItAll();
    public void afterAI();
    public void startGame();

}

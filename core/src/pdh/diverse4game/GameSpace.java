package pdh.diverse4game;

public class GameSpace {
    private GamePiece gamePiece;
    
    public GameSpace() {
    }
    
    public GameSpace(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
    }
    
    public GamePiece getGamePiece() {
        return gamePiece;
    }
    
    public void setGamePiece(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
    }
    
    public boolean isEmpty() {
        boolean isSpaceEmpty = true;
        if (this.gamePiece != null) {
            isSpaceEmpty = false;
        }
        return isSpaceEmpty;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GameSpace:[");
        if (isEmpty()) 
            sb.append("EMPTY");
        else
            sb.append(gamePiece.toString());
        sb.append("]");
        return sb.toString();
    }
    
    public Object clone() {
        GameSpace cloneGameSpace = new GameSpace(this.gamePiece);
        return (Object)cloneGameSpace;
    }
}

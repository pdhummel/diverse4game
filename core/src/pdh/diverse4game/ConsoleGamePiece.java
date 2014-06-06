package pdh.diverse4game;

public class ConsoleGamePiece extends GamePiece {
    GamePiece gamePiece;

    public ConsoleGamePiece(GamePiece gamePiece) {
        this.gamePiece = gamePiece;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (gamePiece.getValue1()) 
            sb.append("'");
        else
            sb.append(" ");

        if (gamePiece.getValue2()) 
            sb.append("-");

        if (gamePiece.getValue3()) 
            sb.append("x");
        else
            sb.append("o");

        if (! gamePiece.getValue2())
            sb.append("-");

        if (gamePiece.getValue1()) 
            sb.append("'");
        else
            sb.append(" ");


        String piece = sb.toString();
        if (gamePiece.getValue4())
            piece = piece.toUpperCase();
 

        return piece;
    }

}

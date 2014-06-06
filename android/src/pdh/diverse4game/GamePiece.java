package pdh.diverse4game;

public class GamePiece implements Comparable {
    private GamePieceAttribute attribute1;
    private GamePieceAttribute attribute2;
    private GamePieceAttribute attribute3;
    private GamePieceAttribute attribute4;
    
    private boolean attrValue1;
    private boolean attrValue2;
    private boolean attrValue3;
    private boolean attrValue4;  

    private String picture;  


    public GamePiece() {
    }

    
    public GamePiece(GamePieceAttribute gpa1, boolean value1,
        GamePieceAttribute gpa2, boolean value2,
        GamePieceAttribute gpa3, boolean value3,
        GamePieceAttribute gpa4, boolean value4) {
            
        this.attribute1 = gpa1;
        this.attribute2 = gpa2;
        this.attribute3 = gpa3;
        this.attribute4 = gpa4; 
        this.attrValue1 = value1;       
        this.attrValue2 = value2;
        this.attrValue3 = value3;
        this.attrValue4 = value4;
    }

    public GamePiece(GamePieceAttribute gpa1, GamePieceAttribute gpa2, 
        GamePieceAttribute gpa3, GamePieceAttribute gpa4, 
        boolean value1, boolean value2, boolean value3, boolean value4) {
            
        this(gpa1, value1, gpa2, value2, gpa3, value3, gpa4, value4);
    }
    
    
    public boolean getValue1() {
        return attrValue1;
    }

    public boolean getValue2() {
        return attrValue2;
    }

    public boolean getValue3() {
        return attrValue3;
    }

    public boolean getValue4() {
        return attrValue4;
    }
    
    
    public String getPicture() {
        return picture;
    }

    public void setPicture(String pic) {
        this.picture = pic;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GamePiece:[");
        sb.append(attribute1.getAttribute(attrValue1));
        sb.append(", " + attribute2.getAttribute(attrValue2));
        sb.append(", " + attribute3.getAttribute(attrValue3));
        sb.append(", " + attribute4.getAttribute(attrValue4));                
        sb.append("]");

        return sb.toString();
    }

    private int getValue() {
        int returnValue = 0;
        if (attrValue1)
            returnValue = returnValue + 1000;
        if (attrValue2)
            returnValue = returnValue + 100;
        if (attrValue3)
            returnValue = returnValue + 10;
        if (attrValue4)
            returnValue = returnValue + 1;

        return returnValue;
    }


    public int compareTo(Object o) {
        int returnValue = 0;
        GamePiece gamePiece = (GamePiece) o;

        if (this.getValue() > gamePiece.getValue()) 
            returnValue = 1;
        if (this.getValue() < gamePiece.getValue()) 
            returnValue = -1;

        return returnValue;        
    }


}

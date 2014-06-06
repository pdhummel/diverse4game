package pdh.diverse4game;

import java.util.*;
import java.io.*;
public class GamePieces {
    
    private ArrayList alGamePieces = new ArrayList();
    private Properties attrProps = new Properties();
    private GamePieceAttribute gpa[] = new GamePieceAttribute[4];

    public GamePieces() {
        initializeGamePieces();
        //System.out.println(gpa[0].toString() + gpa[1].toString() + gpa[2].toString() + gpa[3].toString());
    }
    


    private void initializeGamePieces() {
        InputStream inputStream = null;
        try {
            Class thisClass = this.getClass();
            inputStream = thisClass.getResourceAsStream("attribute.properties");
            attrProps.load(inputStream);
        }
        catch(Exception e) {
            System.out.println(this.getClass().getName() + 
            " - An unexpected error has occurred:\n" + e);
            System.exit(-2);
        }

        String attrName = null;
        String attrTrueValue = null;
        String attrFalseValue = null;        

        attrName = (String)attrProps.get("attr1Name");
        attrTrueValue = (String)attrProps.get("attr1TrueValue");
        attrFalseValue = (String)attrProps.get("attr1FalseValue");
        GamePieceAttribute gpa1 = new GamePieceAttribute(
            attrName, attrTrueValue, attrFalseValue);
        gpa[0] = gpa1;
            
        attrName = (String)attrProps.get("attr2Name");
        attrTrueValue = (String)attrProps.get("attr2TrueValue");
        attrFalseValue = (String)attrProps.get("attr2FalseValue");
        GamePieceAttribute gpa2 = new GamePieceAttribute(
            attrName, attrTrueValue, attrFalseValue);
        gpa[1] = gpa2;

        attrName = (String)attrProps.get("attr3Name");
        attrTrueValue = (String)attrProps.get("attr3TrueValue");
        attrFalseValue = (String)attrProps.get("attr3FalseValue");
        GamePieceAttribute gpa3 = new GamePieceAttribute(
            attrName, attrTrueValue, attrFalseValue);
        gpa[2] = gpa3;

        attrName = (String)attrProps.get("attr4Name");
        attrTrueValue = (String)attrProps.get("attr4TrueValue");
        attrFalseValue = (String)attrProps.get("attr4FalseValue");
        GamePieceAttribute gpa4 = new GamePieceAttribute(
            attrName, attrTrueValue, attrFalseValue);
        gpa[3] = gpa4;
            
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, true, true, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, true, true, false);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, true, false, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, true, false, false);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, false, true, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, false, true, false);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, false, false, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, true, false, false, false);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, true, true, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, true, true, false);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, true, false, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, true, false, false);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, false, true, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, false, true, false);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, false, false, true);
        addGamePiece(gpa1, gpa2, gpa3, gpa4, false, false, false, false);
    }
    
    
    private void addGamePiece(GamePieceAttribute gpa1, GamePieceAttribute gpa2, 
        GamePieceAttribute gpa3, GamePieceAttribute gpa4, 
        boolean value1, boolean value2, boolean value3, boolean value4) {
        
        GamePiece gamePiece = new GamePiece(gpa1, gpa2, gpa3, gpa4,
            value1, value2, value3, value4);
        String propertyKey = (new Boolean(value1)).toString() + "." + 
            (new Boolean(value2)).toString() + "." + 
            (new Boolean(value3)).toString() + "." +
            (new Boolean(value4)).toString();
        String fileName = (String)attrProps.get(propertyKey);
        gamePiece.setPicture(fileName);
        alGamePieces.add(gamePiece);            
    }

    public Collection getGamePieces() {
        return alGamePieces;    
    }
    
    public GamePieceAttribute[] getGamePieceAttributes() {
        return gpa;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GamePieces:[\n");
        int size = alGamePieces.size();
        for (int i=0; i < size; i++) {
            sb.append(alGamePieces.get(i) + "\n");
        }
        sb.append("]");
        return sb.toString();
        
    }

}

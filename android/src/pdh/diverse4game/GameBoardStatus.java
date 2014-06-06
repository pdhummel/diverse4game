package pdh.diverse4game;

public class GameBoardStatus {
  
    public static final int NO_VICTORY_ON_OPEN_BOARD = 0;
    public static final int VICTORY_ON_ROW1 = 1;
    public static final int VICTORY_ON_ROW2 = 2;
    public static final int VICTORY_ON_ROW3 = 3;
    public static final int VICTORY_ON_ROW4 = 4;
    public static final int VICTORY_ON_COL1 = 5;
    public static final int VICTORY_ON_COL2 = 6;
    public static final int VICTORY_ON_COL3 = 7;
    public static final int VICTORY_ON_COL4 = 8;
    public static final int VICTORY_ON_DIAG1 = 9;
    public static final int VICTORY_ON_DIAG2 = 10;
    public static final int NO_VICTORY_ON_FILLED_BOARD = 11;

    public static final String VICTORY_LOCATION_MESSAGE[] = 
    {
        "unknown", 
        "column 1", "column 2", "column 3", "column 4",         
        "row 1", "row 2", "row 3", "row 4", 
        "diaganol 1", "diaganol 2", "unknown"
    };

    public static final int NONE = 0;
    public static final int ATTRIBUTE1 = 1;
    public static final int ATTRIBUTE2 = 2;
    public static final int ATTRIBUTE3 = 3;
    public static final int ATTRIBUTE4 = 4;

    private GameBoard gameBoard;
    private GameBoardStatus.VictoryReason victoryReason = 
        new GameBoardStatus.VictoryReason(); 
    
    
    public GameBoardStatus(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public GameBoardStatus.VictoryReason getVictoryReason() {
        return victoryReason;
    }

    public boolean checkForVictory() {
        boolean victory = false;
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_ROW1);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_ROW2);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_ROW3);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_ROW4);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_COL1);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_COL2);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_COL3);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_COL4);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_DIAG1);
        if (!victory)
            victory = checkFor4Matches(GameBoardStatus.VICTORY_ON_DIAG2);
        return victory;
    }


    // checkFor4Matches(GameBoardStatus.VICTORY_ON_ROW1)
    private boolean checkFor4Matches(int fourSpaces) {
        boolean victory = false;
        GameSpace gameSpaces[] = null;
        boolean match = false;

        try {
            if (fourSpaces >= GameBoardStatus.VICTORY_ON_ROW1 && 
                fourSpaces <= GameBoardStatus.VICTORY_ON_ROW4) {
                gameSpaces = (GameSpace[])gameBoard.getRow1To4(fourSpaces);
            }
            else if (fourSpaces >= GameBoardStatus.VICTORY_ON_COL1 && 
                fourSpaces <= GameBoardStatus.VICTORY_ON_COL4) {
                gameSpaces = (GameSpace[])gameBoard.getCol1To4(fourSpaces-4);
            }
            else if (fourSpaces == GameBoardStatus.VICTORY_ON_DIAG1) {
                gameSpaces = (GameSpace[])gameBoard.getDiag1();
            }
            else if (fourSpaces == GameBoardStatus.VICTORY_ON_DIAG2) {
                gameSpaces = (GameSpace[])gameBoard.getDiag2();
            }

            victory = checkFor4Matches(gameSpaces);
            if (victory) {
                victoryReason.setStatus(fourSpaces);
            }
        }
        catch(Exception e) {}
        
        return victory;
    }


    private boolean checkFor4Matches(GameSpace gameSpace[]) {
        boolean returnValue = false;
        GamePiece gp1 = gameSpace[0].getGamePiece();
        GamePiece gp2 = gameSpace[1].getGamePiece();
        GamePiece gp3 = gameSpace[2].getGamePiece();
        GamePiece gp4 = gameSpace[3].getGamePiece();

        returnValue = checkFor4Matches(gp1, gp2, gp3, gp4);
        if (returnValue == true) {
        	victoryReason.setVictoryGameSpaces(gameSpace);
        	gameSpace[0].setPartOfVictory(true);
        	gameSpace[1].setPartOfVictory(true);
        	gameSpace[2].setPartOfVictory(true);
        	gameSpace[3].setPartOfVictory(true);
        }
        return returnValue;
    }

    private boolean checkFor4Matches(GamePiece gamePiece[]) {
        boolean returnValue = false;
        returnValue = this.checkFor4Matches(
            gamePiece[0], gamePiece[1], gamePiece[2], gamePiece[3]);
        return returnValue;
    }

    private boolean checkFor4Matches(GamePiece gp1, GamePiece gp2,
        GamePiece gp3, GamePiece gp4) {
        boolean match = false;
        if (gp1 != null && gp2 != null && gp3 != null && gp4 != null) {
            if (gp1.getValue1() == gp2.getValue1() && 
                gp2.getValue1() == gp3.getValue1() && 
                gp3.getValue1() == gp4.getValue1()) {
                match = true;
                victoryReason.setVictoryAttribute(GameBoardStatus.ATTRIBUTE1);
                victoryReason.setVictoryAttributeValue(gp1.getValue1());
            }
            else if (gp1.getValue2() == gp2.getValue2() && 
                gp2.getValue2() == gp3.getValue2() &&                 
                gp3.getValue2() == gp4.getValue2()) {
                match = true;
                victoryReason.setVictoryAttribute(GameBoardStatus.ATTRIBUTE2);
                victoryReason.setVictoryAttributeValue(gp1.getValue2());
            }
            else if (gp1.getValue3() == gp2.getValue3() && 
                gp2.getValue3() == gp3.getValue3() && 
                gp3.getValue3() == gp4.getValue3()) {
                match = true;
                victoryReason.setVictoryAttribute(GameBoardStatus.ATTRIBUTE3);
                victoryReason.setVictoryAttributeValue(gp1.getValue3());
            }
            else if (gp1.getValue4() == gp2.getValue4() && 
                gp2.getValue4() == gp3.getValue4() && 
                gp3.getValue4() == gp4.getValue4()) {
                match = true;
                victoryReason.setVictoryAttribute(GameBoardStatus.ATTRIBUTE4);
                victoryReason.setVictoryAttributeValue(gp1.getValue4());
            }
        }
        return match;
    }


    // ----------------------------------------------------------------
    public static class VictoryReason {
        private int status = GameBoardStatus.NO_VICTORY_ON_OPEN_BOARD;
        private int victoryAttribute;
        private boolean victoryAttributeValue;   
        private GameSpace[] victoryGameSpaces;        

        public VictoryReason() {
        }

        public VictoryReason(int status, int victoryAttribute, 
        boolean attributeValue) {
            this.status = status;
            this.victoryAttribute = victoryAttribute;
            this.victoryAttributeValue = attributeValue;
        }
        
        public int getStatus() {
            return this.status;
        }

        public void setVictoryGameSpaces(GameSpace[] spaces) {
        	victoryGameSpaces = spaces;
        }
        
        public GameSpace[] getVictoryGameSpaces() {
        	return victoryGameSpaces;
        }
        
        public int getVictoryAttribute() {
            return this.victoryAttribute;
        }

        public boolean getVictoryAttributeValue() {
            return this.victoryAttributeValue;
        }
        
        public void setStatus(int status) {
            this.status = status;
        }
        
        public void setVictoryAttribute(int victoryAttribute) {
            this.victoryAttribute = victoryAttribute;
        }

        public void setVictoryAttributeValue(boolean value) {
            this.victoryAttributeValue = value;
        }
        
    }

}

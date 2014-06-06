package pdh.diverse4game;

public class GameBoard {
    private GameSpace gameSpace[][] = new GameSpace[4][4];
    
    public GameBoard() {
        for (int x=0; x<4; x++) {
            for (int y=0; y<4; y++) {
                gameSpace[x][y] = new GameSpace();
            }
        }
    }
    
    public void clearBoard() {
        GamePiece gamePiece = null;
        for (int x=0; x<4; x++) {
            for (int y=0; y<4; y++) {
                gameSpace[x][y].setGamePiece(gamePiece);
                gameSpace[x][y].setPartOfVictory(false);
            }
        }
    }

    public void playPiece0To3(GamePiece gamePiece, int x, int y) 
        throws InvalidGameBoardLocation {

        if (x < 0 || x > 3 || y < 0 || y > 3) {
            String msg = "x=" + x + ", y=" + y;
            throw new InvalidGameBoardLocation(msg);
        }
        gameSpace[x][y].setGamePiece(gamePiece);    
        
    }
       

    public void playPiece1To4(GamePiece gamePiece, int x, int y) 
        throws InvalidGameBoardLocation {
        x = x - 1;
        y = y - 1;
        this.playPiece0To3(gamePiece, x, y);
    }

  
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("GameBoard:[\n");
        for (int x=0; x<4; x++) {
            for (int y=0; y<4; y++) {
                sb.append("(" + x + ", " + y + ")-");
                if (gameSpace[x][y] != null)
                    sb.append(gameSpace[x][y].toString() + "\n");
                else
                    sb.append("null\n");
            }
        }
        sb.append("]");
        return sb.toString();
    }
    
    public GameSpace getGameSpace(int x, int y) {
        return gameSpace[x][y];
    }

	public Object getRow1To4(int row) 
		throws InvalidGameBoardLocation {
		
		Object oReturn = null;
		if (row < 1 || row > 4)
			throw new InvalidGameBoardLocation();
			
		GameSpace gameSpaceRow[] = new GameSpace[4];
		for (int x=0; x < 4; x++) {
			GameSpace gs = gameSpace[x][row-1];
			gameSpaceRow[x] = gs;
		}
		oReturn = (Object)gameSpaceRow;
		
		return oReturn;
	}
    
	public Object getCol1To4(int col) 
		throws InvalidGameBoardLocation {
		
		Object oReturn = null;
		if (col < 1 || col > 4)
			throw new InvalidGameBoardLocation();
			
		GameSpace gameSpaceCol[] = new GameSpace[4];
		for (int y=0; y < 4; y++) {
			GameSpace gs = gameSpace[col-1][y];
			gameSpaceCol[y] = gs;
		}
		oReturn = (Object)gameSpaceCol;
		
		return oReturn;
	}
    
    
	public Object getDiag1() {
		
		Object oReturn = null;
			
		GameSpace gameSpaceDiag[] = new GameSpace[4];
		for (int i=0; i < 4; i++) {
			GameSpace gs = gameSpace[i][i];
			gameSpaceDiag[i] = gs;
		}
		oReturn = (Object)gameSpaceDiag;
		
		return oReturn;
	}

	public Object getDiag2() {
		
		Object oReturn = null;
			
		GameSpace gameSpaceDiag[] = new GameSpace[4];
		for (int i=0; i < 4; i++) {
			GameSpace gs = gameSpace[3-i][i];
			gameSpaceDiag[i] = gs;
		}
		oReturn = (Object)gameSpaceDiag;
		
		return oReturn;
	}
    
    public Object clone() {
    	GameBoard cloneGameBoard = new GameBoard();
        for (int x=0; x<4; x++) {
            for (int y=0; y<4; y++) {
                GameSpace gs = this.gameSpace[x][y];
                GamePiece gp = gs.getGamePiece();
                try {
	                cloneGameBoard.playPiece0To3(gp, x, y);
	            } catch(Exception eIgnore) {}
            }
        }
    	return (Object)cloneGameBoard;
    }
    
}

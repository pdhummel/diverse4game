package pdh.diverse4game;

import java.util.*;

public class ComputerAI {
    private Game game = null;
    private GameBoard gameBoard = null;
    private GameState gameState = null;
    private Random random = null;
    


    public ComputerAI(Game game) {
        this.game = game;
        GameBoard origGameBoard = game.getGameBoard();
        gameBoard = (GameBoard)origGameBoard.clone();
        GameState origGameState = game.getGameState();
        gameState = (GameState)origGameState.clone();
        random = new Random((new Date()).getTime());
    }

    public GamePiece pickFirstPiece() {
        GamePiece gamePiece = null;        
        gamePiece = randomlyPickPiece();
        return gamePiece;
    }
    
    public ComputerMove playPiece() {
        ComputerMove computerMove = makeMove();
        return computerMove;
    }
    
    private ComputerMove makeMove() {
        ComputerMove computerMove = new ComputerAI.ComputerMove();
        boolean victoryFound = false;
        boolean safePieceFound = false;
        boolean foundOpenSpace = false;
        boolean done = false;
        int x = 0;
        int y = 0;
        Collection availablePieces = gameState.getAvailablePieces();
        // if turn < 3 then randomly play and pick a piece.        
        int turn = gameState.getGameTurn();   
        if (turn < 3) {
            //System.out.println(this.getClass().getName() + 
            //".makeMove() - random play"); 
            
            computerMove = randomlyPlayPiece();
            GamePiece gamePiece = randomlyPickPiece();
            computerMove.setPickedPiece(gamePiece);
            done = true;
        }
        // otherwise, if there are no available pieces,
        // find the only open space and play the piece.
        else if (availablePieces.size() == 0) {
            //System.out.println(this.getClass().getName() + 
            //".makeMove() - last move"); 
            
            GamePiece pieceToPlay = gameState.getCurrentPiece();
            while (x < 4 && foundOpenSpace == false) {
                y = 0;
                while (y < 4 && foundOpenSpace == false) {
                    GameSpace gameSpace = gameBoard.getGameSpace(x, y);
                    if (gameSpace.isEmpty()) {
                        computerMove.setX(x);
                        computerMove.setY(y);
                        foundOpenSpace = true;
                    }
                    y = y + 1;
                }
                x = x + 1;
            }
            done = true;
        }
        // otherwise, try to play the piece to win.
        else {
            //System.out.println(this.getClass().getName() + 
            //".makeMove() - try to win"); 
            
            GamePiece pieceToPlay = gameState.getCurrentPiece();
            GameBoard origGameBoard = game.getGameBoard();
            //System.out.println(this.getClass().getName() + 
            //".makeMove() - before canPieceWin"); 
            ComputerMove cm = this.canPieceWin(pieceToPlay, origGameBoard);
            //System.out.println(this.getClass().getName() + 
            //".makeMove() - after canPieceWin"); 
            
            if (cm != null) {
                computerMove = cm;
                victoryFound = true;
                done = true;
            }
        }
        
        // otherwise, loop through the spaces
            // and play a piece and
            // then pick a piece
        // until a "safe" play/pick is found or
        // there are no other choices.
        if (! done) {
            //System.out.println(this.getClass().getName() + 
            //".makeMove() - play something"); 
            
            GamePiece pieceToPlay = gameState.getCurrentPiece();
            while (x < 4 && safePieceFound == false) {
                y = 0;
                while (y < 4 && safePieceFound == false) {
                    GameBoard origGameBoard = game.getGameBoard();
                    gameBoard = (GameBoard)origGameBoard.clone();
                    GameSpace gameSpace = gameBoard.getGameSpace(x, y);
                    if (gameSpace.isEmpty()) {
                        try {
                            gameBoard.playPiece0To3(pieceToPlay, x, y);
                            Iterator iteratePieces = availablePieces.iterator();
                            while (iteratePieces.hasNext() && safePieceFound == false) {
                                GamePiece pieceToPick = (GamePiece)iteratePieces.next();
                                computerMove.setPickedPiece(pieceToPick);
                                computerMove.setX(x);
                                computerMove.setY(y);
                                ComputerMove cm = canPieceWin(pieceToPick, gameBoard);
                                if (cm == null) {
                                    safePieceFound = true;
                                }
                            }
                        } catch(Throwable t) {
                            System.out.println(this.getClass().getName() + 
                            ".makeMove() - " + 
                            "An unexpected error occurred:\n" + t);
                        }        
                    }
                    y = y + 1;
                }
                x = x + 1;
            }
        }
        //System.out.println(this.getClass().getName() + 
        //".makeMove() - exit"); 
        
        return computerMove;
    }
    
   
    private GamePiece randomlyPickPiece() {
        GamePiece gamePiece = null;
             
        Collection availableGamePieces = gameState.getAvailablePieces();
        TreeSet treeGamePieces = new TreeSet(availableGamePieces);
        int size = treeGamePieces.size();        
        Object o[] = treeGamePieces.toArray();
        int selection = 0;
        while (gamePiece == null) {
            selection = random.nextInt(size);
            Object one = o[selection];
            gamePiece = (GamePiece)one;
        }
        return gamePiece;
    }
    
    private ComputerMove randomlyPlayPiece() {
        ComputerMove computerMove = new ComputerAI.ComputerMove();
        int x = -1;
        int y = -1;
        GamePiece gamePiece = null;
        GameSpace gameSpace = null;
        while (gameSpace == null || gamePiece != null) {    
            x = random.nextInt(4);
            y = random.nextInt(4);
            gameSpace = gameBoard.getGameSpace(x, y);
            gamePiece = gameSpace.getGamePiece();
        }    
        computerMove.setX(x);
        computerMove.setY(y);
        //System.out.println(computerMove.toString());
        return computerMove;
    }
    

    private ComputerMove canPieceWin(GamePiece pieceToPlay, 
    GameBoard startingGameBoard, String msg) {
        System.out.println("canPieceWin - " + msg);
        return canPieceWin(pieceToPlay, startingGameBoard);
    }
            
    private ComputerMove canPieceWin(GamePiece pieceToPlay, 
        GameBoard startingGameBoard) {
            
        ComputerMove cm = null;
        boolean victoryFound = false;
        int x = 0;
        int y = 0;
        while (x < 4 && victoryFound == false) {
            y = 0;
            while (y < 4 && victoryFound == false) {
                gameBoard = (GameBoard)startingGameBoard.clone();
                GameSpace gameSpace = gameBoard.getGameSpace(x, y);
                if (gameSpace.isEmpty()) {
                    try {
                        //System.out.println(this.getClass().getName() + 
                        //".canPieceWin() - gameSpace isEmpty - x="+x+", y="+y); 
                        
                        gameBoard.playPiece0To3(pieceToPlay, x, y);
                        GameBoardStatus gbs = new GameBoardStatus(gameBoard);
                        victoryFound = gbs.checkForVictory();
                        if (victoryFound) {
                            //System.out.println(this.getClass().getName() + 
                            //".canPieceWin() - victoryFound"); 
                            cm = new ComputerAI.ComputerMove();
                            cm.setX(x);
                            cm.setY(y);
                        }
                    } catch(Throwable t) {
                        System.out.println(this.getClass().getName() + 
                        ".canPieceWin() - " + 
                        "An unexpected error occurred:\n" + t);
                    }
                }
                else {
                    GamePiece gp = gameSpace.getGamePiece();
                    //System.out.println("x="+x+",y="+y+","+gp.toString());
                }
                y = y + 1;
            }
            x = x + 1;
        }
        return cm;
    }

    public static class ComputerMove {
        private GamePiece pickedPiece = null;
        private int x = -1;
        private int y = -1;
        public ComputerMove() {
        }
        public GamePiece getPickedPiece() {
            return pickedPiece;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public void setX(int x) {
            this.x = x;
        }
        public void setY(int y) {
            this.y = y;
        }
        public void setPickedPiece(GamePiece gamePiece) {
            this.pickedPiece = gamePiece;
        }
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append((new Integer(x)).toString() + ", ");
            sb.append((new Integer(y)).toString());
            if (pickedPiece != null) {
                sb.append(", " + pickedPiece.toString()); 
            }
            return sb.toString(); 
        }
    }
}

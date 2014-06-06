package pdh.diverse4game;

import java.util.*;
import java.io.*;

public class ConsoleUI implements GamePresenter {
    private Game game;
    private GameState gameState;
    private GameStateHandler gsh;
    private boolean done = false;

    public ConsoleUI(Game game) {
        this.game = game;
        gameState = game.getGameState();
        gsh = new GameStateHandler(game, this);
    }


    public void initialize() {
        try {
            game.createNewGame();
            game.definePlayers();
            game.setPlayerOrder(1);
            this.interact();
        }
        catch(Exception eIgnore) {}
    }

    public void interact() {
        //GameState gameState = game.getGameState();
        while (! done) {
            gsh.handleState();    
        }
    }


    public void showBoard() {
        GameBoard gameBoard = game.getGameBoard();

        StringBuffer sb = new StringBuffer();
        for (int y=0; y<4; y++) {
            sb.append((y+1));
            for (int x=0; x<4; x++) {
                GameSpace gameSpace = gameBoard.getGameSpace(x, y);
                sb.append("[ ");
                if (gameSpace != null) {
                    GamePiece gamePiece = gameSpace.getGamePiece();
                    if (gamePiece != null) {
                        ConsoleGamePiece cgp = new ConsoleGamePiece(gamePiece);
                        sb.append(cgp.toString());
                    }
                    else
                        sb.append("    ");
                }
                else
                    sb.append("    ");
                sb.append(" ]");
            }
            sb.append("\n\n");
        }

        System.out.println("\nBoard:");
        System.out.println("   1       2       3       4");
        System.out.println(sb.toString());
        
    }


    public void showAvailablePieces() {
        StringBuffer sb = new StringBuffer();

        //GameState gameState = game.getGameState();
        GamePieces gamePieces = game.getGamePieces();
        Collection allGamePieces = gamePieces.getGamePieces();
        Collection availableGamePieces = gameState.getAvailablePieces();
        TreeSet treeAllGamePieces = new TreeSet(allGamePieces);
        Iterator iterateAllGamePieces = treeAllGamePieces.iterator();
        int count = 0;

        while (iterateAllGamePieces.hasNext()) {
            GamePiece gamePiece = (GamePiece)iterateAllGamePieces.next();
            count = count + 1;
            if (count < 10)
                sb.append(" " + count + ") ");
            else
                sb.append(count + ") ");
            if (availableGamePieces.contains(gamePiece)) {
                ConsoleGamePiece cgp = new ConsoleGamePiece(gamePiece);
                sb.append(cgp.toString() + " ");
            }
            else
                sb.append("     ");
            if ((count % 4) == 0) {
                sb.append("\n");
            }
        }
    
        System.out.println("Available Pieces:");
        System.out.println(sb.toString());
    }


    public void showCurrentPiece() {
        //GameState gameState = game.getGameState();
        GamePiece gamePiece = gameState.getCurrentPiece();
        ConsoleGamePiece cgp = null;
        String piece = null;
        if (gamePiece != null) {
            cgp = new ConsoleGamePiece(gamePiece);
            piece = cgp.toString();
        }
        else
            piece = "";
        System.out.println("Current piece to play:");
        System.out.println(piece);
    }

    public void showQuitOption() {
        System.out.println("Input (Q) to quit.");
    }

    public void showPickPieceOption() {
        System.out.println("Input a number to pick a piece. (1-16)");
    }

    public void showPlayPieceOption() {
        System.out.println("Input a location to play a piece. (x,y)");
    }

    public void showBoardFullMessage() {
        System.out.println("The game ended in a draw.");
    }
    
    public void showErrorMessage() {
        //GameState gameState = game.getGameState();
        System.out.println(gameState.getMessage());
    }

    public void showVictoryMessage() {
        System.out.println("The game was won.");
    }



    public String allowInput() {
        String input;
        System.out.print("? ");
        input = getInput();
        
        ConsoleInputHandler cih = new ConsoleInputHandler(game);
        cih.handleState(input);
        return input;
    }

    public void endItAll() {
        this.done = true;
    }

    public void afterAI() {
    }
    
    public void startGame() {
    }

    private String getInput() {
        String input = null;
        
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bis = new BufferedReader(isr);
        while (input == null || input.length() < 1) {
            try {
                input = bis.readLine();
            }
            catch(Exception eIgnore) {}
        }
        return input;
    }


}

// TODO:  double check exception handling in all classes
// TODO:  Create executable jar and add xml header line.
// TODO:  Add buttons to Quit and start a new game.
// TODO:  Add option to set who goes first.
// TODO:  Remove backround edge color from smiley pics.
// TODO:  Reconcile pattern diffs between console and gui versions.

package pdh.diverse4game;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class GUIPanel extends JApplet  {
    public static void main(String args[]) {

        JFrame appWindow = new JFrame("Diverse 4");
        appWindow.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            }
        );
        GUIPanel guiPanel = new GUIPanel();
        guiPanel.init();
        guiPanel.start();
        appWindow.getContentPane().add(guiPanel);
        appWindow.setSize(600, 600);
        appWindow.show();
    }


    private JButton spaceButton[][] = new JButton[4][4];
    private JButton availablePieceButton[] = new JButton[16];
    private JButton currentPieceButton = new JButton();
    private JButton quitButton = new JButton("Quit");
    private JButton newButton = new JButton("New");
    private JLabel jlMessage = new JLabel();

    private GUIGamePresenter guiGamePresenter = null;
    private boolean allowPickPiece = false;
    private boolean allowPlayPiece = false;
    private boolean allowQuit = true;
    private boolean allowNew = false;
    
            


    public void init() {

        // Define the game board layout.
        JPanel gameBoardPanel = new JPanel();
        gameBoardPanel.setLayout(new BoxLayout(gameBoardPanel, BoxLayout.Y_AXIS));
        gameBoardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        gameBoardPanel.add(Box.createGlue());
        JPanel jpGameBoardLabel = new JPanel();
        jpGameBoardLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel jlGameBoard = new JLabel("Game Board");
        jpGameBoardLabel.add(jlGameBoard);        
        gameBoardPanel.add(jpGameBoardLabel);
        JPanel gameSpacePanel = new JPanel();
        gameSpacePanel.setLayout(new GridLayout(4, 4));
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                spaceButton[x][y] = new JButton();
                spaceButton[x][y].setActionCommand(x + "," + y);
                spaceButton[x][y].addActionListener(new GameSpaceButtonHandler());
                spaceButton[x][y].setPreferredSize(new Dimension(50, 50));
                gameSpacePanel.add(spaceButton[x][y]);
            }
        }
        gameBoardPanel.add(gameSpacePanel);


        
        // Define the available pieces layout.
        JPanel availablePiecesPanel = new JPanel();
        availablePiecesPanel.setLayout(new BoxLayout(availablePiecesPanel, BoxLayout.Y_AXIS));
        JPanel jpAvailablePiecesLabel = new JPanel();        
        JLabel jlAvailablePieces = new JLabel("Available Pieces:  ", SwingConstants.LEFT);
        jpAvailablePiecesLabel.setLayout(new FlowLayout(FlowLayout.LEFT));
        jpAvailablePiecesLabel.add(jlAvailablePieces);
        availablePiecesPanel.add(jpAvailablePiecesLabel);        
        JPanel piecesPanel = new JPanel();
        piecesPanel.setLayout(new GridLayout(2, 8));
        for (int i=0; i<16; i++) {
            availablePieceButton[i] = new JButton();
            //availablePieceButton[i].setBorderPainted(false);
            availablePieceButton[i].setActionCommand("" + i);            
            availablePieceButton[i].addActionListener(new AvailablePieceButtonHandler());            
            availablePieceButton[i].setPreferredSize(new Dimension(50, 50));            
            piecesPanel.add(availablePieceButton[i]);
        }
        availablePiecesPanel.add(piecesPanel);
        availablePiecesPanel.add(Box.createGlue());        

        // Define the layout for the current piece to play.
        JPanel pieceToPlayPanel = new JPanel();
        pieceToPlayPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel jlCurrentPiece = new JLabel("Current piece to play:  ");
        pieceToPlayPanel.add(jlCurrentPiece);
        currentPieceButton.setPreferredSize(new Dimension(50, 50));
        pieceToPlayPanel.add(currentPieceButton);

        // Define the message area layout.
        JPanel msgAreaPanel = new JPanel();
        msgAreaPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        jlMessage.setText("");
        msgAreaPanel.add(jlMessage);


        // Layout the button functionality.
        JPanel functionButtonPanel = new JPanel();
        functionButtonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        newButton.setPreferredSize(new Dimension(75, 25));
        newButton.addActionListener(new NewButtonHandler());
        quitButton.setPreferredSize(new Dimension(75, 25));
        quitButton.addActionListener(new QuitButtonHandler());
        functionButtonPanel.add(newButton);        
        functionButtonPanel.add(quitButton);        

        // Layout the outer most panel.
        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(functionButtonPanel);
        container.add(Box.createGlue());
        container.add(gameBoardPanel);
        container.add(Box.createGlue());
        container.add(availablePiecesPanel);
        container.add(Box.createGlue());        
        container.add(pieceToPlayPanel);
        container.add(Box.createGlue());        
        container.add(msgAreaPanel);
        
        // Create the mediator between the game and the gui.
        guiGamePresenter = new GUIGamePresenter(this);
        guiGamePresenter.initialize();
        guiGamePresenter.checkState();
    }

    public void setAllowPlayPiece(boolean b) {
        allowPlayPiece = b;        
    }

    public void setAllowPickPiece(boolean b) {
        allowPickPiece = b;        
    }
    
    public void refreshGameBoardView(GameBoard gameBoard) {
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                GameSpace gameSpace = gameBoard.getGameSpace(x, y);
                GamePiece gamePiece = gameSpace.getGamePiece();
                ImageIcon imageIcon = null;
                if (gamePiece != null) {
                    imageIcon = getImageIcon(gamePiece.getPicture(), "");
                }
                spaceButton[x][y].setIcon(imageIcon);
            }
        }
    }

    public void refreshAvailablePiecesView(Game game, GameState gameState) {
        GamePieces gamePieces = game.getGamePieces();
        Collection allGamePieces = gamePieces.getGamePieces();
        Collection availableGamePieces = gameState.getAvailablePieces();
        TreeSet treeAllGamePieces = new TreeSet(allGamePieces);
        Iterator iterateAllGamePieces = treeAllGamePieces.iterator();
        int count = 0;

        while (iterateAllGamePieces.hasNext()) {
            ImageIcon imageIcon = null;
            GamePiece gamePiece = (GamePiece)iterateAllGamePieces.next();
            if (availableGamePieces.contains(gamePiece)) {
                imageIcon = getImageIcon(gamePiece.getPicture(), "");
            }
            availablePieceButton[count].setIcon(imageIcon);
            count = count + 1;
        }        
    }

    public void refreshCurrentPieceView(GamePiece gamePiece) {
        ImageIcon imageIcon = null;
        if (gamePiece != null)
            imageIcon = getImageIcon(gamePiece.getPicture(), "");
        currentPieceButton.setIcon(imageIcon);
    }
    
    public void refreshMessage(String message) {
        jlMessage.setText(message);
    }
    
    public void refreshQuit(boolean b) {
        allowQuit = b;
        quitButton.setEnabled(b);
    }

    public void refreshNew(boolean b) {
        allowNew = b;
        newButton.setEnabled(b);
    }

    private ImageIcon getImageIcon(String path, String description) {
        ImageIcon imageIcon = null;
        Class thisClass = this.getClass();
        String fileName = "images/" + path;
        java.net.URL url = thisClass.getResource(fileName);        
        if (url != null) {
            imageIcon = new ImageIcon(url, description);
        }
        return imageIcon;
    }

    private class AvailablePieceButtonHandler
    implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (allowPickPiece) {
                JButton button = (JButton)e.getSource();
                GUIInputHandler inputHandler = guiGamePresenter.getInputHandler();
                boolean ok = inputHandler.pickPiece(button.getActionCommand());
                if (ok) {
                    guiGamePresenter.checkState();
                    allowPickPiece = false;
                }
            }
        }
    }

    private class GameSpaceButtonHandler
    implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (allowPlayPiece) {
                JButton button = (JButton)e.getSource();
                GUIInputHandler inputHandler = guiGamePresenter.getInputHandler();
                boolean ok = inputHandler.playPiece(button.getActionCommand());
                if (ok) {
                    guiGamePresenter.checkState();
                    allowPlayPiece = false;
                }
            }
        }
    }

    private class QuitButtonHandler
    implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (allowQuit) {
                JButton button = (JButton)e.getSource();
                GUIInputHandler inputHandler = guiGamePresenter.getInputHandler();
                inputHandler.quit();
                guiGamePresenter.checkState();
                allowPlayPiece = false;
                allowPickPiece = false;
                allowNew = true;
                allowQuit = false;
                refreshMessage("The game was ended.");
            }
        }
    }

    private class NewButtonHandler
    implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (allowNew) {
                JButton button = (JButton)e.getSource();
                GUIInputHandler inputHandler = guiGamePresenter.getInputHandler();
                inputHandler.newGame();
                guiGamePresenter.checkState();
                allowNew = false;
                allowQuit = true;
            }
        }
    }

}

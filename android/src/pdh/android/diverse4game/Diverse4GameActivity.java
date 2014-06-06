package pdh.android.diverse4game;

import pdh.diverse4game.*;
import android.app.Activity;
import android.os.Bundle;
import android.widget.*;
import android.view.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.content.Intent;
import java.util.*;



public class Diverse4GameActivity extends Activity implements GuiPanel {
	


    private AndroidPresenter androidPresenter = null;
    private boolean allowPickPiece = false;
    private boolean allowPlayPiece = false;
    private boolean allowQuit = true;
    private boolean allowNew = false;
    
    
    private ImageButton spaceButton[][] = new ImageButton[4][4];
    private ImageButton availablePieceButton[] = new ImageButton[16];
    private ImageButton currentPieceButton;
    private Button quitButton;
    private Button newButton;
    private TextView tvMessage;	
    private LinearLayout layout;
    private Map<String, Integer> name2constant;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        layout = (LinearLayout)findViewById(R.id.main_layout);
            
        // Define the game board layout.	    
	    TableLayout tableBoard = new TableLayout(this);
	    TableRow tableRow[] = new TableRow[4];
	    for (int i=0; i<4; i++) {
	    	tableRow[i] = new TableRow(this);
	    	tableBoard.addView(tableRow[i]);
	    	tableRow[i].setHorizontalGravity(Gravity.CENTER);
	    }

        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                spaceButton[x][y] = new ImageButton(this);
                spaceButton[x][y].setMinimumWidth(60);
                spaceButton[x][y].setPadding(1, 1, 1, 1);
                tableRow[x].addView(spaceButton[x][y]);
                spaceButton[x][y].setTag(x + "," + y);
                spaceButton[x][y].setOnClickListener(gameSpaceButtonListener);
            }
        }
        layout.addView(tableBoard);

        // Define the available pieces layout.
        TextView tvAvailable = new TextView(this);
        tvAvailable.setText("Available Pieces:");
        TableLayout availablePiecesLayout = new TableLayout(this);
        TableRow availablePieces1 = new TableRow(this);
        TableRow availablePieces2 = new TableRow(this);
        availablePiecesLayout.addView(availablePieces1);
        availablePieces1.setHorizontalGravity(Gravity.CENTER);
        availablePiecesLayout.addView(availablePieces2);
        availablePieces2.setHorizontalGravity(Gravity.CENTER);
        
        for (int i=0; i<16; i++) {
            availablePieceButton[i] = new ImageButton(this);
            availablePieceButton[i].setMinimumWidth(60);
            availablePieceButton[i].setMaxWidth(60);  
            availablePieceButton[i].setPadding(1, 1, 1, 1);
            availablePieceButton[i].setTag("" + i);  
            availablePieceButton[i].setOnClickListener(availablePieceButtonListener);
            if (i < 8) {
            	availablePieces1.addView(availablePieceButton[i]);
            } else {
            	availablePieces2.addView(availablePieceButton[i]);
            }
        }
        layout.addView(tvAvailable);
        layout.addView(availablePiecesLayout);
        
        
        // Define the layout for the current piece to play.
	    currentPieceButton = new ImageButton(this);        
        TextView tvSelected = new TextView(this);
        tvSelected.setText("Current piece to play:");
        TableLayout currentPieceLayout = new TableLayout(this);
        TableRow currentPieceRow = new TableRow(this);
        currentPieceLayout.addView(currentPieceRow);
        currentPieceRow.setHorizontalGravity(Gravity.CENTER);
        currentPieceButton = new ImageButton(this);
        //currentPieceButton.setMinimumHeight(64);
        currentPieceButton.setMinimumWidth(60);  
        currentPieceButton.setPadding(1, 1, 1, 1);        
        currentPieceRow.addView(currentPieceButton);
        layout.addView(tvSelected);
        layout.addView(currentPieceLayout);

        // Define the message area layout.
	    tvMessage = new TextView(this);	
        layout.addView(tvMessage);

        // Layout the button functionality.
        TableLayout functionButtonLayout = new TableLayout(this);
        TableRow functionButtonRow = new TableRow(this);
        functionButtonLayout.addView(functionButtonRow);
	    quitButton = new Button(this);
	    quitButton.setText("Quit");
	    quitButton.setOnClickListener(auitButtonListener);
	    newButton = new Button(this);
	    newButton.setText("New");       
	    newButton.setOnClickListener(newButtonListener);
        functionButtonRow.addView(newButton);        
        functionButtonRow.addView(quitButton); 
        //layout.addView(functionButtonLayout);
        
    	name2constant = new HashMap<String, Integer>();
    	name2constant.put("white_female_nonose_frown.gif", R.drawable.white_female_nonose_frown);
    	name2constant.put("white_female_nonose_smile.gif", R.drawable.white_female_nonose_smile);
    	name2constant.put("white_female_nose_frown.gif", R.drawable.white_female_nose_frown);
    	name2constant.put("white_female_nose_smile.gif", R.drawable.white_female_nose_smile);
    	name2constant.put("white_male_nonose_frown.gif", R.drawable.white_male_nonose_frown);
    	name2constant.put("white_male_nonose_smile.gif", R.drawable.white_male_nonose_smile);
    	name2constant.put("white_male_nose_frown.gif", R.drawable.white_male_nose_frown);
    	name2constant.put("white_male_nose_smile.gif", R.drawable.white_male_nose_smile);
    	name2constant.put("yellow_female_nonose_frown.gif", R.drawable.yellow_female_nonose_frown);
    	name2constant.put("yellow_female_nonose_smile.gif", R.drawable.yellow_female_nonose_smile);
    	name2constant.put("yellow_female_nose_frown.gif", R.drawable.yellow_female_nose_frown);
    	name2constant.put("yellow_female_nose_smile.gif", R.drawable.yellow_female_nose_smile);
    	name2constant.put("yellow_male_nonose_frown.gif", R.drawable.yellow_male_nonose_frown);
    	name2constant.put("yellow_male_nonose_smile.gif", R.drawable.yellow_male_nonose_smile);
    	name2constant.put("yellow_male_nose_frown.gif", R.drawable.yellow_male_nose_frown);
    	name2constant.put("yellow_male_nose_smile.gif", R.drawable.yellow_male_nose_smile);
    
        
        // Create the mediator between the game and the gui.
        androidPresenter = new AndroidPresenter(this);
        androidPresenter.initialize();
        androidPresenter.checkState();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.layout.game_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.new_game:
            this.newGame();
            return true;
        case R.id.quit_game:
            this.quitGame();
            return true;
        case R.id.exit:
            this.finish();
            return true;            
        case R.id.help:
            this.showHelp();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
    
    public void refreshGameBoardView(GameBoard gameBoard) {
        for (int y=0; y<4; y++) {
            for (int x=0; x<4; x++) {
                GameSpace gameSpace = gameBoard.getGameSpace(x, y);
                GamePiece gamePiece = gameSpace.getGamePiece();
                int image = 0;
                if (gamePiece != null) {
                    image = getPicture(gamePiece.getPicture());
                }
                if (gameSpace.getPartOfVictory()) {
                	PorterDuffColorFilter filter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                	spaceButton[x][y].getBackground().setColorFilter(filter);
                	spaceButton[x][y].getBackground().setState(FOCUSED_STATE_SET);
                } else {
                	spaceButton[x][y].getBackground().clearColorFilter();
                }                
                spaceButton[x][y].setImageResource(image);
            }
        }    	
    }
    
    

    private int getPicture(String name) {
    	int returnValue =  name2constant.get(name); 
    	return returnValue;
    }
    
    
    public void refreshAvailablePiecesView(Game game, GameState gameState) {
        GamePieces gamePieces = game.getGamePieces();
        Collection<GamePiece> allGamePieces = gamePieces.getGamePieces();
        Collection<GamePiece> availableGamePieces = gameState.getAvailablePieces();
        TreeSet<GamePiece> treeAllGamePieces = new TreeSet<GamePiece>(allGamePieces);
        Iterator<GamePiece> iterateAllGamePieces = treeAllGamePieces.iterator();
        int count = 0;

        while (iterateAllGamePieces.hasNext()) {
            int image = 0;
            GamePiece gamePiece = (GamePiece)iterateAllGamePieces.next();
            if (availableGamePieces.contains(gamePiece)) {
                image = this.getPicture(gamePiece.getPicture());
            }
            availablePieceButton[count].setImageResource(image); 
            count = count + 1;
        }        
    }
 
    public void refreshCurrentPieceView(GamePiece gamePiece) {
        int image = 0;
        if (gamePiece != null) {
            image = getPicture(gamePiece.getPicture());
    	}
        this.currentPieceButton.setImageResource(image);
    }  
    
    public void setAllowPlayPiece(boolean b) {
        allowPlayPiece = b;        
    }

    public void setAllowPickPiece(boolean b) {
        allowPickPiece = b;        
    }

    public void refreshMessage(String message) {
        tvMessage.setText(message);
    }    
    
    public void refreshQuit(boolean b) {
        allowQuit = b;
        quitButton.setEnabled(b);
    }

    public void refreshNew(boolean b) {
        allowNew = b;
        newButton.setEnabled(b);
    }    
    
    

	private View.OnClickListener gameSpaceButtonListener = new View.OnClickListener() {
	    public void onClick(View v) {
            if (allowPlayPiece) {
                ImageButton button = (ImageButton)v;
                GUIInputHandler inputHandler = androidPresenter.getInputHandler();
                boolean ok = inputHandler.playPiece((String)button.getTag());
                if (ok) {
                	androidPresenter.checkState();
                	allowPlayPiece = false;
                }
            }        
	    }
	};

	private View.OnClickListener availablePieceButtonListener = new View.OnClickListener() {
	    public void onClick(View v) {
            if (allowPickPiece) {
                ImageButton button = (ImageButton)v;
                GUIInputHandler inputHandler = androidPresenter.getInputHandler();
                boolean ok = inputHandler.pickPiece((String)button.getTag());
                if (ok) {
                	androidPresenter.checkState();
                    allowPickPiece = false;
                }
            }
	    }
	};
	
	private void quitGame() {
        if (allowQuit) {
            GUIInputHandler inputHandler = androidPresenter.getInputHandler();
            inputHandler.quit();
            androidPresenter.checkState();
            allowPlayPiece = false;
            allowPickPiece = false;
            allowNew = true;
            allowQuit = false;
            refreshMessage("The game ended.");
        }		
	}
	
	private void showHelp() {
		Intent intent = new Intent(this, HelpActivity.class);
		this.startActivityForResult(intent, 0);
	}

	private View.OnClickListener auitButtonListener = new View.OnClickListener() {
	    public void onClick(View v) {
            quitGame();
	    }
	};
	
	private void newGame() {
        if (allowNew) {
            GUIInputHandler inputHandler = androidPresenter.getInputHandler();
            inputHandler.newGame();
            androidPresenter.checkState();
            allowNew = false;
            allowQuit = true;
        }		
	}

	private View.OnClickListener newButtonListener = new View.OnClickListener() {
	    public void onClick(View v) {
	    	newGame();
	    }
	};

	

	
}


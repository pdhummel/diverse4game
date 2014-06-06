How to run:
- It's an executable jar, so theoretically, you can just do:  java -jar diverse4game.jar.  
- If the executable jar doesn't work, try:
java -classpath diverse4game.jar pdh.diverse4game.GUIPanel
- There is a console version which can be run with:
java -classpath diverse4game.jar pdh.diverse4game.Diverse4Game


How to play:
Get 4 pieces in a row which share a common attribute.  The attributes are male/female, frown/smile, yellow/white, nose/no-nose.

Sequence of play:
The first player picks a piece for the next player to play.
(no piece is played on the board yet; player turn ends)

* The current player places the piece on the board.
(If the game is not won and there are still pieces to play, continue and repeat steps)
* Then the current player picks a piece for the next player to play.


Wierd UI:
- A piece is picked by clicking on an available piece.
- A piece is played by clicking on a board space.
- Quit will end the current game.  Even if the game ends, you must click Quit before New.
- New will start a new game.  


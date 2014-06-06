package pdh.diverse4game;


public class Diverse4Game {
    public static void main(String args[]) {
        Game game = new Game();
        ConsoleUI consoleUI = new ConsoleUI(game);
        consoleUI.initialize();
    }
}

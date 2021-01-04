package core;

public class Position {
    private boolean computerPlay; // true if the computer has to play and false otherwise
    private Player player;
    private Player computer;
    private int[] cells;
    private GameEngine game;

    public Position(GameEngine game, Player computer , Player player , int[] cells){
        this.game = game;
        this.computer = computer;
        this.player = player;
        this.cells = cells;
    }


    public boolean isFinalPosition(){
        return this.game.endOfGame();
    }
    // Getters ...
    public Player getPlayer() {
        return this.player;
    }

    public Player getPlayerComputer() {
        return this.computer;
    }

    public GameEngine getGame() {
        return game;
    }

    public int[] getCells() {
        return cells;
    }

    public void setCells(int[] cells) {
        this.cells = cells;
    }
}

package core;

public class Position {
    private boolean computerPlay; // true if the computer has to play and false otherwise
    private Player player;
    private Player computer;
    private int[] cells;

    Position(Player computer , Player player , int[] cells){
        this.computer = computer;
        this.player = player;
        this.cells = cells;
    }


    // Getters ...
    public Player getPlayer() {
        return this.player;
    }

    public Player getPlayerComputer() {
        return this.computer;
    }
}

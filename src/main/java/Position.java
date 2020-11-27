public class Position {

    private int[] cellsPlayer; // each cell contains a certain number of seeds
    private int[] cellsComputer;
    private boolean computerPlay; // boolean true if the computer has to play and false otherwise
    private int seedsPlayer; // seeds taken by the player
    //private int seedsComputer; // seeds taken by the computer


    private Player player;
    private Player playerComputer;

    Position(int nbCells , boolean computerPlay , int seedsPlayer , int seedsComputer){
        this.computerPlay = computerPlay ;

        Player player1 = new Player(nbCells,seedsPlayer);
        Player player2 = new Player(nbCells,seedsComputer);
    }

    // Getters ...
    public Player getPlayer() {
        return this.player;
    }

    public Player getPlayerComputer() {
        return this.playerComputer;
    }
}

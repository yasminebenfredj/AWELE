public class Position {

    private int [] cellsPlayer; // each cell contains a certain number of seeds
    private int[] cellsComputer;
    private boolean computer_play; // boolean true if the computer has to play and false otherwise
    private int seedsPlayer; // seeds taken by the player
    private int seedsComputer; // seeds taken by the computer

    Position(int nbCellsPlayer , int nbCellsComputer , boolean computerPlay , int seedsPlayer , int seedsComputer){
        this.cellsPlayer = new int[nbCellsPlayer] ; //12 cases par joueur au début
        this.cellsComputer = new int[nbCellsComputer] ;
        this.computer_play = computerPlay ;
        this.seedsPlayer = seedsPlayer ;
        this.seedsComputer = seedsComputer ;
    }
}

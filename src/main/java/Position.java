public class Position {

    private int [] cellsPlayer; // each cell contains a certain number of seeds
    private int[] cellsComputer;
    private boolean computerPlay; // boolean true if the computer has to play and false otherwise
    private int seedsPlayer; // seeds taken by the player

    private Joueur joueur1;
    private Joueur joueur2;

    Position(int nbCells , boolean computerPlay , int seedsPlayer , int seedsComputer){

        Joueur joueur1 = new Joueur(nbCells,seedsPlayer);
        Joueur joueur2 = new Joueur(nbCells,seedsComputer);
        this.computerPlay = computerPlay ;


    }
}

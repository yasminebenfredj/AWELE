import Intelligence.Intelligence;

public class Player {
    private Intelligence intelligence;
    private int[] cells; // each cell contains a certain number of seeds
    private int seeds; // seeds taken by the player

    public Player(int nbCellsPlayer, int seeds){
        this.cells = new int[nbCellsPlayer] ; //12 cases par joueur au début
        this.seeds = seeds ;
    }

    // Getters ...
    public int getSeeds() {
        return this.seeds;
    }
}
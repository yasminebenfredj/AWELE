import Intelligence.Intelligence;

import java.util.Random;

public class Player {
    private Intelligence intelligence;
    Random random = new Random();


    //private int[] cells; // each cell contains a certain number of seeds
    private int seeds; // seeds taken by the player
    private int[] indexes ;
    private boolean isComputer;
    private int nbCellsPlayer;


    public Player(int nbCellsPlayer, int seeds, boolean isComputer){
        //this.cells = new int[nbCellsPlayer] ; //12 cases par joueur au début
        this.seeds = seeds ;
        this.isComputer = isComputer;
        this.indexes = new int[nbCellsPlayer];
        this.nbCellsPlayer = nbCellsPlayer;
        this.initIndexes();
    }

    // Getters ...
    public int getSeeds() {
        return this.seeds;
    }

    public boolean isComputer() {
        return this.isComputer;
    }

    public void addSeeds(int seeds){
        this.seeds += seeds ;
    }



    public int chooseCell(){
        int index  =  random.nextInt(this.nbCellsPlayer);
        return this.indexes[index] ;
    }

    /*
    public void setOneCell(int index) {
        if (isComputer == true) {
            this.cells[index/2]++;
        }
        else {
            this.cells[(index-1)/2]++;
        }
    }
*/
    private void initIndexes(){
        for (int i = 0; i <= this.nbCellsPlayer  ; i++) {
            if (isComputer) {
                indexes[i] = (i * 2);
            }
            else
            {
                indexes[i] = (i * 2) + 1;

            }
        }
    }


}

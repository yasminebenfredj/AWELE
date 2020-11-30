import Intelligence.Intelligence;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
    private Intelligence intelligence;
    Random random = new Random();


    private int[] cells; // each cell contains a certain number of seeds
    private int seeds; // seeds taken by the player
    private int[] indexes ;
    private  int j;


    public Player(int nbCellsPlayer, int seeds,int j){
        this.cells = new int[nbCellsPlayer] ; //12 cases par joueur au début
        this.seeds = seeds ;
        this.j = j;
        indexes = new int[cells.length];
    }

    // Getters ...
    public int getSeeds() {
        return this.seeds;
    }

    public int[] getCells() {
        return cells;
    }

    public void setCells(int[] cells) {
        this.cells = cells;
    }

    public int chooseCell(){
        return random.nextInt(cells.length);
    }

    public void setOneCell(int index) {
        if (j == 1) {
            this.cells[index/2]++;
        }
        else {
            this.cells[(index-1)/2]++;
        }
    }
/*
    private void initIndexes(int j ){
        if(j == 1){
            for (int i = 0; i < cells.length ; i++) {
                indexes[i] = ((i*2));
            }
        }else
        {
            for (int i = 0; i < cells.length ; i++) {
                indexes[i] = ((i*2)+1);
            }
        }
    }
*/

}

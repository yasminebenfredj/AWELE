package Intelligence;

import core.Position;

import java.util.Random;

public abstract class Intelligence {

    private int[] indexes;
    private int nbCells;
    private Random random;

    public  Intelligence(int nbCellsPlayer, int[] indexes) {
        this.indexes = indexes;
        this.nbCells = nbCellsPlayer;
        this.random = new Random();
    }

    /**
     *
     * @return Choix de l'intelligence
     */
    public abstract int chooseCell(int [] cells);

    public abstract void setCurrentPosition(Position position);

    public abstract String toString();


    /*
     *
     ***********************  Les Getter and Setter ***************************
     *
     */

    public int[] getIndexes() {
        return indexes;
    }

    public int getNbCells() {
        return nbCells;
    }

    public Random getRandom() {
        return random;
    }

    public void setIndexes(int[] newIndexes) {
        this.indexes = newIndexes;
    }

    public void setNbCells(int nbCells) {
        this.nbCells = nbCells;
    }

    public boolean possible(int  index , int[] cells) {
        return cells[index] != 0;

    }

}

package Intelligence;

import core.State;
import java.util.Random;

public abstract class Intelligence {

    private int[] indexes;
    private int nbCells;
    private Random random;
    private int[] otherIndexes;


    public  Intelligence(int nbCellsPlayer, int[] indexes, int[] otherIndexes)
    {
        this.indexes = indexes;
        this.otherIndexes = otherIndexes;
        this.nbCells = nbCellsPlayer;
        this.random = new Random();
    }

    /**
     *
     * @return Choix de l'intelligence
     */
    public abstract int chooseCell(State state);

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

    public int[] getOtherIndexes() {
        return otherIndexes;
    }

    public void setOtherIndexes(int[] otherIndexes) {
        this.otherIndexes = otherIndexes;
    }

}

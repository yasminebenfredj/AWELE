package Intelligence;

import java.util.Random;

public abstract class Intelligence {

    private int[] indexes;
    private int nbCellsPlayer;
    private Random random;

    public  Intelligence(int nbCellsPlayer, int[] indexes)
    {
        this.indexes = indexes;
        this.nbCellsPlayer = nbCellsPlayer;
        this.random = new Random();
    }

    /**
     *
     * @return Choix de l'intelligence
     */
    public abstract  int chooseCell();

    public abstract String toString();




    /*
     *
     ***********************  Les Getter ***************************
     *
     */

    public int[] getIndexes() {
        return indexes;
    }

    public int getNbCellsPlayer() {
        return nbCellsPlayer;
    }

    public Random getRandom() {
        return random;
    }


}

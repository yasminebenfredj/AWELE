package Intelligence;


import core.State;

import java.util.Random;

/**
 * Cette classe est la stratégie Random
 */
public class RandomStrategy extends Intelligence {

    public RandomStrategy(int nbCellsPlayer, int[] indexes, int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
    }

    @Override
    public int chooseCell(State state){
        int index  =  super.getRandom().nextInt(super.getNbCells());
        int resp =  super.getIndexes()[index];

        while(!super.possible(resp, state.getGame().getCells())) {
            index  =  super.getRandom().nextInt(super.getNbCells());
            resp =  super.getIndexes()[index];
        }

        return resp ;
    }

    @Override
    public String toString() {
        return " Stratégie RANDOM ";
    }
}

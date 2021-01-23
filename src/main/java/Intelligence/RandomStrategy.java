package Intelligence;

import core.State;
import java.util.ArrayList;

/**
 * Cette classe est la stratégie Random
 */
public class RandomStrategy extends Intelligence {

    public RandomStrategy(int nbCellsPlayer, int[] indexes, int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
    }

    @Override
    public int chooseCell(State state){

        ArrayList<Integer> possibilities = allPossibilities(super.getIndexes(),state.getGame().getCells());
        System.out.println(possibilities);

        int index  =  super.getRandom().nextInt(possibilities.size());
        int resp =  possibilities.get(index);

        return resp ;
    }

    @Override
    public String toString() {
        return " Stratégie RANDOM ";
    }
}

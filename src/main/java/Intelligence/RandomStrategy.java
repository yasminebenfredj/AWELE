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

    /**
     *  cette methode permet de filter les possible choix du joueur
     * @return tableau des possiblité
     */
    private ArrayList<Integer> allPossibilities(int[] indexes, int[] cells)
    {
        ArrayList<Integer> possibilities = new ArrayList<>();
        for (int index: indexes) {
            if (super.possible(index, cells))
            {
                possibilities.add(index);
            }
        }
        return possibilities;
    }


    @Override
    public String toString() {
        return " Stratégie RANDOM ";
    }
}

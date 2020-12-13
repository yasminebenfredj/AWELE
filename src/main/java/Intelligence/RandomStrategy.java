package Intelligence;


/**
 * Cette classe est la stratégie Random
 */
public class RandomStrategy extends Intelligence {

    public RandomStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }

    @Override
    public int chooseCell(){
        int index  =  super.getRandom().nextInt(super.getNbCells());
        return super.getIndexes()[index] ;
    }

    @Override
    public String toString() {
        return " Stratégie RANDOM ";
    }
}
package Intelligence;


import core.Position;

/**
 * Cette classe est la stratégie Random
 */
public class RandomStrategy extends Intelligence {

    public RandomStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }

    @Override
    public int chooseCell(int[] cells){
        int index  =  super.getRandom().nextInt(super.getNbCells());
        int resp =  super.getIndexes()[index];

        while( !possible(resp, cells)) {
            index  =  super.getRandom().nextInt(super.getNbCells());
            resp =  super.getIndexes()[index];
        }

        return resp ;
    }

    @Override
    public void setCurrentPosition(Position position) {

    }

    private boolean possible(int  index , int[] cells) {
        return cells[index] != 0;

    }

    @Override
    public String toString() {
        return " Stratégie RANDOM ";
    }
}

package Intelligence;


import core.Position;

/**
 * Cette classe est la stratégie de notre IA
 */
public class ComputerStrategy extends Intelligence {
    boolean[] chosenCells = new boolean[getNbCells()];
    //core.Game game = new core.Game();


    public ComputerStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }


    //TODO
    @Override
    public int chooseCell(int[] cells) {
        int index  =  super.getRandom().nextInt(super.getNbCells());
        return super.getIndexes()[index] ;
    }

    @Override
    public void setCurrentPosition(Position position) {

    }


    private int minMaxValue(int [] cells) {
        boolean[] chosenCells = new boolean[getNbCells()];
        int[] newCells = cells.clone();
        return 0;

    }


    private int prediction(int[] cells) {
        int[] newCells = cells.clone();
        int score = 0;
        for (int i = 0; i < getNbCells() ; i++) {
            if ( cells[getIndexes()[i]] != 0) {
                int nbSeedsIn =  cells[getIndexes()[i]];
                cells[getIndexes()[i]] = 0;
                //int position  =
            }

        }
        return 0;
    }


    @Override
    public String toString() {
        return " Stratégie COMPUTER ";
    }



}

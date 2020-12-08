package Intelligence;

/**
 * Cette classe est la strategie de notre IA
 */
public class ComputerStrategy extends Intelligence {


    public ComputerStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }


    @Override
    public int chooseCell(){
        int index  =  super.getRandom().nextInt(super.getNbCells());
        return super.getIndexes()[index] ;
    }
    @Override
    public String toString() {
        return "COMPUTER";
    }
}

package Intelligence;

import core.Position;

public class MiniMaxStrategy extends Intelligence{
    private Position currentPosition;

    public MiniMaxStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }

    public int miniMax(Position position,int depth,boolean maximizingPlayer){
        if (depth == 0 || position.isFinalPosition()){
            if (position.isFinalPosition()){

            }
            else {
                int seedsDifference = position.getPlayerComputer().getSeeds() - position.getPlayer().getSeeds();
                if (seedsDifference > 0){

                }
                else if (seedsDifference < 0){

                }
                else {

                }
            }
        }
    }
    @Override
    public int chooseCell(int[] cells) {
        return 0;
    }

    @Override
    public void setCurrentPosition(Position position) {
        this.currentPosition = position;
    }

    @Override
    public String toString() {
        return "MiniMax Strategy";
    }
}

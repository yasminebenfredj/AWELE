package Intelligence;

import core.GameEngine;
import core.State;

import java.util.ArrayList;

public class MiniMaxStrategy extends Intelligence{
    private State currentState;
    private boolean isMerged ;

    public MiniMaxStrategy(int nbCellsPlayer, int[] indexes,  int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
        this.isMerged = false;
    }

    private State simulateTurn(int choice,State state,boolean isComputer){
        int[] cells = state.getGame().getCells().clone();
        int nbSeedsIn = cells[choice];
        cells[choice] = 0 ;
        state.getGame().setCells(cells);

        int initialPosition = choice;
        int position = GameEngine.nextPosition(choice, state.getGame().getNbCells(),super.getNbCells()*2+1) ;//this.nbCells*2+1 :n'importe quel nombre  > nbCells*2 marche //
        for (int i = 1  ; i <= nbSeedsIn ; i++ ) {
            cells[position] += 1 ;
            choice = position;
            position = GameEngine.nextPosition(position, state.getGame().getNbCells(),initialPosition);//
        }
        return this.simulateCollectSeeds(cells,choice,state,isComputer);
    }

    private State simulateCollectSeeds(int[] cells,int currentIndex,State state,boolean isComputer){
        while (cells[currentIndex] == 2 || cells[currentIndex] == 3 ) {
            int gains = cells[currentIndex];
            if (isComputer){
                state.getGame().computer.addSeeds(gains);
                state.getGame().nbSeedsInGame -= gains;
            }
            else {
                state.getGame().player.addSeeds(gains);
                state.getGame().nbSeedsInGame -= gains;
            }
            cells[currentIndex] = 0;
            currentIndex = GameEngine.precedentPosition(currentIndex, state.getGame().getNbCells());
        }
        state.getGame().setCells(cells);
        return state;
    }

    private int evaluation(State state){//@TODO Améliorer
        int nbSeedsComputer = state.getMe().getSeeds();
        int nbSeedsPlayer = state.getOtherPlayer().getSeeds();
        return nbSeedsComputer - nbSeedsPlayer;
    }

    private int evaluation2(State state){//@TODO Améliorer
        int nbSeedsComputer = state.getMe().getSeeds();
        int nbSeedsPlayer = state.getOtherPlayer().getSeeds();
        int seedsDifference = nbSeedsComputer - nbSeedsPlayer;
        if (seedsDifference > 0){
            return 1;
        }
        else if (seedsDifference < 0){
            return -1;
        }
        else {
            return 0;
        }
    }

    private int miniMax(State state, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (state.getGame().isMerged() && !isMerged){//@TODO
            System.out.println("Mergedddddddd");
            state.getGame().setCells(state.getGame().getCells().clone());

            isMerged = true;
        }

        if (depth == 0 || state.getGame().endOfGame()){
            if (state.getGame().endOfGame()){
                int seedsDifference = state.getGame().computer.getSeeds() - state.getGame().player.getSeeds();
                if (seedsDifference > 0){//computer wins
                    return state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();//96
                }
                else if (seedsDifference < 0){//player wins
                    return -state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();//-96
                }
                else {//draw
                    return 0;
                }
            }
            else {//depth is zero
                return evaluation(state); //@TODO update currentPosition when needed in the coming code
            }
        }
        if (maximizingPlayer){
            double value = Double.NEGATIVE_INFINITY;
            ArrayList<Integer> fullIndexes = super.allPossibilities(super.getIndexes(),state.getGame().getCells());
            int random  =  super.getRandom().nextInt(super.getNbCells());
            int index =  super.getIndexes()[random];
            for (int i = 0; i < fullIndexes.size(); i++) {
                //if (position.getCells()[super.getIndexes()[i]] != 0){ // >0
                State newState = simulateTurn(fullIndexes.get(i), state.clone(),true);
                double newScore = miniMax(newState,depth - 1,alpha,beta,false);
                if (newScore > value){
                    value = newScore;
                    //index = fullIndexes.get(i);
                }
                alpha = Math.max(alpha , value);//pruning
                if (alpha >= beta){
                    break;
                }
                index = fullIndexes.get(i);
            }
            return index;
        }
        else {
            double value = Double.POSITIVE_INFINITY;
            ArrayList<Integer> fullIndexes = super.allPossibilities(super.getOtherIndexes(),state.getGame().getCells());
            int random  =  super.getRandom().nextInt(super.getNbCells());
            int index =  super.getIndexes()[random];
            for (int i = 0; i < fullIndexes.size(); i++) {
                //if (position.getCells()[super.getIndexes()[i]] != 0){ // >0
                State newState = simulateTurn(fullIndexes.get(i), state.clone(),false);
                double newScore = miniMax(newState,depth - 1,alpha,beta,true);
                if (newScore < value){
                    value = newScore;
                    //index = fullIndexes.get(i);
                }
                beta = Math.min(beta , value);
                if (alpha >= beta){
                    break;
                }
                index = fullIndexes.get(i);
            }
            return index;
        }
    }
    @Override
    public int chooseCell(State state) {//@TODO depth 1 ça marche meme avec 1000 partie so ?
        this.currentState = state;
        int miniMax = miniMax(currentState.clone(), 6,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,true);
        return miniMax;
    }


    @Override
    public String toString() {
        return " MiniMax Strategy ";
    }
}
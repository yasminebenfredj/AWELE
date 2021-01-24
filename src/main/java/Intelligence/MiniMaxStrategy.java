package Intelligence;

import core.GameEngine;
import core.State;
import utility.Colors;

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
        int position = GameEngine.nextPosition(choice, state.getGame().getNbCells(),initialPosition) ;//this.nbCells*2+1 :n'importe quel nombre  > nbCells*2 marche //
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
                state.getMe().addSeeds(gains);
                state.getGame().nbSeedsInGame -= gains;
            }
            else {
                state.getOtherPlayer().addSeeds(gains);
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

    public int evaluation3(State state){
        return 0;
    }

    private int[] miniMax(State state, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (!state.getGame().isMerged() && state.getGame().nbSeedsInGame <= state.getGame().totalNbSeed/2){
            state.getGame().mergeCells(6);
            state.getGame().setIsMerged();
        }

        int[] tuple = new int[2];//0:index 1:résultat de la méthode d'évaluation

        /*if (depth == 0 || state.getGame().endOfGame()){
            if (state.getGame().endOfGame()){
                int seedsDifference = state.getGame().computer.getSeeds() - state.getGame().player.getSeeds();
                if (seedsDifference > 0){//computer wins
                    tuple[1] = state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();
                    return tuple;
                    //return state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();//96
                }
                else if (seedsDifference < 0){//player wins
                    tuple[1] = -state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();
                    return tuple;
                    //return -state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();//-96
                }
                else {//draw
                    tuple[1] = 0;
                    return tuple;
                    //return 0;
                }
            }
            else {//depth is zero
                tuple[1] = evaluation(state);
                return tuple;
                //return evaluation(state); //@TODO update currentPosition when needed in the coming code
            }
        }*/
        if (depth == 0 || state.getGame().endOfGame()){
                tuple[1] = evaluation(state);
                return tuple;
        }
        if (maximizingPlayer){
            double value = Double.NEGATIVE_INFINITY;
            ArrayList<Integer> fullIndexes = super.allPossibilities(super.getIndexes(),state.getGame().getCells());
            for (int i = 0; i < fullIndexes.size(); i++) {
                State newState = simulateTurn(fullIndexes.get(i), state.clone(),true);
                int newScore = miniMax(newState,depth - 1,alpha,beta,false)[1];
                if (newScore > value){
                    value = newScore;
                    tuple[0] = fullIndexes.get(i);
                    tuple[1] = (int) value;
                }
                alpha = Math.max(alpha , value);//pruning
                if (alpha >= beta){
                    break;
                }
            }
            return tuple;
        }
        else {
            double value = Double.POSITIVE_INFINITY;
            ArrayList<Integer> fullIndexes = super.allPossibilities(super.getOtherIndexes(),state.getGame().getCells());
            for (int i = 0; i < fullIndexes.size(); i++) {
                State newState = simulateTurn(fullIndexes.get(i), state.clone(),false);
                int newScore = miniMax(newState,depth - 1,alpha,beta,true)[1];
                if (newScore < value){
                    value = newScore;
                    tuple[0] = fullIndexes.get(i);
                    tuple[1] = (int) value;
                }
                beta = Math.min(beta , value);//pruning
                if (alpha >= beta){
                    break;
                }
            }
            return tuple;
        }
    }
    @Override
    public int chooseCell(State state) {//@TODO depth 1 ça marche meme avec 1000 partie so ?
        this.currentState = state;
        int[] miniMax = miniMax(currentState.clone(), 6,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,true);
        return miniMax[0];
    }

    @Override
    public String toString() {
        return " MiniMax Strategy ";
    }
}
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
        int[] cells = state.getCells().clone();
        int nbSeedsIn = cells[choice];
        cells[choice] = 0 ;
        state.setCells(cells);

        int initialPosition = choice;
        int position = GameEngine.nextPosition(choice, state.getNbCells(),initialPosition) ;//this.nbCells*2+1 :n'importe quel nombre  > nbCells*2 marche //
        for (int i = 1  ; i <= nbSeedsIn ; i++ ) {
            cells[position] += 1 ;
            choice = position;
            position = GameEngine.nextPosition(position, state.getNbCells(),initialPosition);//
        }
        return this.simulateCollectSeeds(cells,choice,state,isComputer);
    }

    private State simulateCollectSeeds(int[] cells,int currentIndex,State state,boolean isComputer){
        while (cells[currentIndex] == 2 || cells[currentIndex] == 3 ) {
            int gains = cells[currentIndex];
            if (isComputer){
                state.getMe().addSeeds(gains);
                state.setNbSeedsInGame(state.getNbSeedsInGame()-gains) ;
            }
            else {
                state.getOtherPlayer().addSeeds(gains);
                state.setNbSeedsInGame(state.getNbSeedsInGame()-gains) ;
            }
            cells[currentIndex] = 0;
            currentIndex = GameEngine.precedentPosition(currentIndex, state.getNbCells());
        }
        state.setCells(cells);
        return state;
    }

    private int evaluation(State state){
        int nbSeedsComputer = state.getMe().getSeeds();
        int nbSeedsPlayer = state.getOtherPlayer().getSeeds();
        return nbSeedsComputer - nbSeedsPlayer;
    }

    private int evaluation2(State state){
        int nbSeedsComputer = state.getMe().getSeeds();
        int nbSeedsPlayer = state.getOtherPlayer().getSeeds();
        int seedsDifference = nbSeedsComputer - nbSeedsPlayer;
        if (seedsDifference > 0){//computer wins
            return state.getNbCells() * 2 *  state.getNbSeeds();
        }
        else if (seedsDifference < 0){//player wins
            return  -state.getNbCells() * 2 *  state.getNbSeeds();
        }
        else {//draw
            return 0;
        }
    }

    private int[] miniMax(State state, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (!state.isMerged() && state.getNbSeedsInGame() <= state.getTotalNbSeed()/2){
            state.mergeCells(6);
            state.setIsMerged();
        }

        int[] tuple = new int[2];//0:index 1:résultat de la méthode d'évaluation

        if (state.endOfGame()){
            tuple[1] = evaluation(state) * 1000;
            return tuple;
        }
        if (depth == 0 ){
            tuple[1] = evaluation(state);
            return tuple;
        }

        if (maximizingPlayer){
            double value = Double.NEGATIVE_INFINITY;
            ArrayList<Integer> fullIndexes = super.allPossibilities(super.getIndexes(),state.getCells());
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
            ArrayList<Integer> fullIndexes = super.allPossibilities(super.getOtherIndexes(),state.getCells());
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
    public int chooseCell(State state) {
        this.currentState = state;

        int[] miniMax = miniMax(currentState.clone(), 10,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,true);
        return miniMax[0];
    }

    @Override
    public String toString() {
        return " MiniMax Strategy ";
    }
}
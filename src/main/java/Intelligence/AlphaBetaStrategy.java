package Intelligence;

import core.GameEngine;
import core.State;

import java.util.ArrayList;

public class AlphaBetaStrategy extends Intelligence {
    private State currentState;
    private int maxDepth = 3;

    private int MAX = 10000;
    private int MIN = -10000;

    public AlphaBetaStrategy(int nbCellsPlayer, int[] indexes, int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
    }

    @Override
    public int chooseCell(State state) {
        this.currentState = state.clone();
        return playAlphaBeta(0,MIN,MAX,true,this.currentState).choices;
    }


    private State playAlphaBeta(int depth,int alpha, int beta, boolean max,  State state) {

        //Si on attend la profondeur max on donne la differeance de score entre les 2 joueurs
        if (depth == this.maxDepth || state.getGame().endOfGame()) {
            state.node =  state.getGame().computer.getSeeds() - state.getGame().player.getSeeds();
            return state;
        }
        State finalState = state;

        // Si on fait un Max
        if (max) {
            int best = this.MAX;
            ArrayList<Integer> nodes = allPossibilities(super.getIndexes(),state.getGame().getCells());
            for (int i = 0; i < nodes.size() ; i++) {
                State newState = playAction(nodes.get(i),true,state.clone());
                State value = playAlphaBeta(depth +1 ,alpha , beta ,false,newState);

                if(best > value.node){
                    best = value.node;
                    finalState = value;
                    finalState.choices = nodes.get(i);

                }
                alpha = Math.max(alpha, best);

                if(beta <= alpha) {
                    break;
                }
            }

            return finalState;
        }
        else
        {
            int best = this.MIN;
            ArrayList<Integer> nodes = allPossibilities(super.getOtherIndexes(),state.getGame().getCells());

            for (int i = 0; i < nodes.size() ; i++) {
                State newState = playAction(nodes.get(i),false ,state.clone());
                newState.choices = nodes.get(i);
                State value = playAlphaBeta(depth +1 ,alpha , beta ,true, newState);

                if (best < value.node)
                {
                    best = value.node;
                    finalState = value;
                    finalState.choices = nodes.get(i);
                }
                beta = Math.min(beta, best);

                if(beta <= alpha) break;
            }
            return finalState;
        }
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
        return " Stratégie AlphaBeta ";
    }


    private State playAction(int coup, boolean isComp, State myState)
    {
        //System.out.println(isComp + " false / player  : " + myState.getGame().player.getSeeds());
        //System.out.println(isComp + " true / computer  : " + myState.getGame().computer.getSeeds());

        int[] cellules = myState.getGame().getCells().clone();
        int nbSeedsIn = cellules[coup];
        cellules[coup] = 0;
        myState.getGame().setCells(cellules);

        int initialPosition = coup;
        int position = GameEngine.nextPosition(coup, myState.getGame().getNbCells(),this.MAX);

        for (int j = 1; j <= nbSeedsIn; j++) {
            cellules[position] = cellules[position] +1;
            coup = position;
            position = GameEngine.nextPosition(position,myState.getGame().getNbCells(),initialPosition);
        }

        while (cellules[coup] == 2 || cellules[position] == 3) {
            int gains = cellules[coup];
            if(isComp) {
                myState.getGame().computer.addSeeds(gains);
                myState.getGame().nbSeedsInGame -= gains;
                cellules[coup] = 0;
                coup = GameEngine.precedentPosition(coup,myState.getGame().getNbCells());
            }
            else {
                myState.getGame().player.addSeeds(gains);
                myState.getGame().nbSeedsInGame -= gains;
                cellules[coup] = 0;
                coup = GameEngine.precedentPosition(coup,myState.getGame().getNbCells());
            }
        }
        myState.getGame().setCells(cellules);
        //System.out.println(isComp + " false / player  : " + myState.getGame().player.getSeeds());
        //System.out.println(isComp + " true / computer  : " + myState.getGame().computer.getSeeds());

        return myState;
    }
}

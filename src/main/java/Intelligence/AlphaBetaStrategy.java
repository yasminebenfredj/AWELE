package Intelligence;

import core.GameEngine;
import core.State;

import java.util.ArrayList;

public class AlphaBetaStrategy extends Intelligence {
    private State currentState;
    private int maxDepth = 6;
    private int initScore = 0;

    private int MAX = 10000;
    private int MIN = -10000;

    public AlphaBetaStrategy(int nbCellsPlayer, int[] indexes, int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
    }

    @Override
    public int chooseCell(State state) {

        this.currentState = state.clone();
        initScore = state.getMe().getSeeds();
        int[] solution = playAlphaBeta(0,MIN,MAX,true,this.currentState);
       //System.out.println(solution[0] + " " + solution[1]);
        return solution[0];
    }


    private int[] playAlphaBeta(int depth,int alpha, int beta, boolean max,  State state) {
        //merger
        int value;
        if (!state.getGame().isMerged() &&
                state.getGame().nbSeedsInGame <= state.getGame().totalNbSeed/2){
            state.getGame().mergeCells(6);
            state.getGame().setIsMerged();
        }

        int[] indexScore = new int[2];

        // EVALUATION
        if (depth == this.maxDepth ) {
            value =  state.getMe().getSeeds() - state.getOtherPlayer().getSeeds();
            indexScore[1] = value;
            return indexScore;
        }
        if(state.getGame().endOfGame()) {
            value =  state.getMe().getSeeds() - state.getOtherPlayer().getSeeds();
            indexScore[1] = value * 3;
            return indexScore;
        }
        if(state.getGame().seedsPlayerCells(state.getMe()) == 0 && max) {
            indexScore[1] = MIN;
            return indexScore;
        }

        //FIN evaluation

        // Si on fait un Max
        if (max) {
            int best = this.MIN;
            ArrayList<Integer> nodes = super.allPossibilities(super.getIndexes(),state.getGame().getCells());
           //System.out.println(nodes);
            //if(nodes.size() == 0) indexScore[1]=MIN;
            for (int i = 0; i < nodes.size() ; i++) {
               //System.out.println("Index jouer " + nodes.get(i));

                State newState = playAction(nodes.get(i),true,state.clone());
                value = playAlphaBeta(depth +1 ,alpha , beta ,false,newState)[1];
                //System.out.println("value = " +value+" best = "+ best);

                if(value > best){
                    best = value;
                    indexScore[0] = nodes.get(i);
                    indexScore[1] = best;
                }

                alpha = Math.max(alpha, best);


                if(beta <= alpha) {
                    //System.out.println("break "+ beta +" <= "+ alpha);
                    break;
                }
            }
            //System.out.println(depth + " max>>>>>"+indexScore[0] + " " + indexScore[1]);

            return indexScore ;
        }
        else
        {
            int best = this.MAX;
            ArrayList<Integer> nodes = super.allPossibilities(super.getOtherIndexes(),state.getGame().getCells());
           //System.out.println(nodes);
            //if(nodes.size() == 0) indexScore[1]=MAX;

            for (int i = 0; i < nodes.size() ; i++) {
               //System.out.println("Index jouer " + nodes.get(i));
                State newState = playAction(nodes.get(i),false ,state.clone());
                value = playAlphaBeta(depth +1 ,alpha , beta ,true, newState)[1];


                if(value < best){
                    best = value;
                    indexScore[0] = nodes.get(i);
                    indexScore[1] = best;
                }

                beta = Math.min(beta, best);

                if(beta <= alpha)
                {
                    //System.out.println("break "+ beta +" <= "+ alpha);
                    break;
                }

            }
           //System.out.println(depth+" min>>>>>"+indexScore[0] + " " + indexScore[1]);

            return indexScore;
        }
    }


    @Override
    public String toString() {
        return " Stratégie AlphaBeta ";
    }


    private State playAction(int coup, boolean isMe, State myState)
    {
        //System.out.println(isMe + " me  : " + myState.getMe().getSeeds() +
         //        "  other Player  : " + myState.getOtherPlayer().getSeeds());
       //System.out.println(isMe + "  " + (myState.getMe().getSeeds() - myState.getOtherPlayer().getSeeds()));


        int[] cellules = myState.getGame().getCells().clone();
        int nbSeedsIn = cellules[coup];
        cellules[coup] = 0;
        myState.getGame().setCells(cellules);

        int initialPosition = coup;
        int position = GameEngine.nextPosition(coup, myState.getGame().getNbCells(),initialPosition);

        for (int j = 1; j <= nbSeedsIn; j++) {
            cellules[position] +=1;
            coup = position;
            position = GameEngine.nextPosition(position,myState.getGame().getNbCells(),initialPosition);
        }

        while (cellules[coup] == 2 || cellules[coup] == 3) {
            int gains = cellules[coup];
            if(isMe) {
                myState.getMe().addSeeds(gains);
                myState.getGame().nbSeedsInGame -= gains;
                cellules[coup] = 0;
                coup = GameEngine.precedentPosition(coup,myState.getGame().getNbCells());
            }
            else {
                myState.getOtherPlayer().addSeeds(gains);
                myState.getGame().nbSeedsInGame -= gains;
                cellules[coup] = 0;
                coup = GameEngine.precedentPosition(coup,myState.getGame().getNbCells());
            }
        }
        myState.getGame().setCells(cellules);
        //System.out.println(isMe + " me  : " + myState.getMe().getSeeds() +
        //        "  other Player  : " + myState.getOtherPlayer().getSeeds());
       //System.out.println((myState.getMe().getSeeds() - myState.getOtherPlayer().getSeeds()));

        return myState;
    }
}

package Intelligence;

import core.GameEngine;
import core.State;

import java.util.ArrayList;

public class AlphaBetaStrategy extends Intelligence {
    private int maxDepth = 16;
    private int currentDepth = 13;
    private int minDepth = 11;

    private int MAX = 10000;
    private int MIN = -10000;

    public AlphaBetaStrategy(int nbCellsPlayer, int[] indexes, int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
    }

    @Override
    public int chooseCell(State state) {

        evaluateDeathMax(state);

        int[] solution = playAlphaBeta(0,MIN,MAX,true,state);
        System.out.println(currentDepth + " max>>>>>");

        //System.out.println(maxDepth);
        return solution[0];
    }

    private void evaluateDeathMax(State currentState)
    {
        if (currentState.isMerged()) currentDepth = maxDepth;

        if(super.allPossibilities(super.getIndexes(),
                currentState.getCells()).size() < 5  && currentDepth < maxDepth ) currentDepth +=3;
        if(super.allPossibilities(super.getIndexes(),
                currentState.getCells()).size() > 6  && currentDepth > minDepth ) currentDepth -=3;
    }

    private int[] playAlphaBeta(int depth,int alpha, int beta, boolean max,  State state) {
        //merger
        int value;
        if (!state.isMerged() &&
                state.getNbSeedsInGame() <= state.getTotalNbSeed()/2){
            state.mergeCells(6);
            state.setIsMerged();
            depth-= 3;
        }

        int[] indexScore = new int[2];

        // EVALUATION
        if (depth == this.currentDepth) {
            value =  state.getMe().getSeeds() - state.getOtherPlayer().getSeeds();
            indexScore[1] = value;
            return indexScore;
        }
        if(state.endOfGame()) {
            value =  state.getMe().getSeeds() - state.getOtherPlayer().getSeeds();
            indexScore[1] = value *3;
            return indexScore;
        }
        if(state.seedsPlayerCells(state.getOtherPlayer()) == 0 && depth != 0)
        {
            indexScore[1] = MAX;
            return indexScore;
        }

        //FIN evaluation

        // Si on fait un Max
        if (max) {
            int best = this.MIN;
            ArrayList<Integer> nodes = super.allPossibilities(super.getIndexes(),state.getCells());
           //System.out.println(nodes);

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
            ArrayList<Integer> nodes = super.allPossibilities(super.getOtherIndexes(),state.getCells());
           //System.out.println(nodes);

            for (int i = 0; i < nodes.size() ; i++) {
               //System.out.println("Index jouer " + nodes.get(i));
                State newState = playAction(nodes.get(i),false ,state.clone());
                value = playAlphaBeta(depth +1 ,alpha , beta ,true, newState)[1];


                if(value < best){
                    best = value;
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

        int[] cellules = myState.getCells().clone();
        int nbSeedsIn = cellules[coup];
        cellules[coup] = 0;
        myState.setCells(cellules);

        int initialPosition = coup;
        int position = GameEngine.nextPosition(coup, myState.getNbCells(),initialPosition);

        for (int j = 1; j <= nbSeedsIn; j++) {
            cellules[position] +=1;
            coup = position;
            position = GameEngine.nextPosition(position,myState.getNbCells(),initialPosition);
        }

        while (cellules[coup] == 2 || cellules[coup] == 3) {
            int gains = cellules[coup];
            if(isMe) {
                myState.getMe().addSeeds(gains);
                myState.setNbSeedsInGame(myState.getNbSeedsInGame() - gains);
                cellules[coup] = 0;
                coup = GameEngine.precedentPosition(coup,myState.getNbCells());
            }
            else {
                myState.getOtherPlayer().addSeeds(gains);
                myState.setNbSeedsInGame(myState.getNbSeedsInGame() - gains);
                cellules[coup] = 0;
                coup = GameEngine.precedentPosition(coup,myState.getNbCells());
            }
        }
        myState.setCells(cellules);
        //System.out.println(isMe + " me  : " + myState.getMe().getSeeds() +
        //        "  other Player  : " + myState.getOtherPlayer().getSeeds());
       //System.out.println((myState.getMe().getSeeds() - myState.getOtherPlayer().getSeeds()));

        return myState;
    }
}

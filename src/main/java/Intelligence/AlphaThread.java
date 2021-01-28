package Intelligence;

import core.GameEngine;

import core.Player;
import core.State;
import java.util.ArrayList;


public class AlphaThread extends  Intelligence  {
    private int MIN =-1000 ;
    private int MAX =1000;
    private int maxDepth =20;
    private int currentDepth =10 ;
    private int minDepth =9;
    long startTime;
    long endTime;
    long timeElapsed;


    public AlphaThread(int nbCellsPlayer, int[] indexes, int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);

    }

    @Override
    public int chooseCell(State state) {
        startTime = System.currentTimeMillis();

        evaluateDeathMax(state);

        int[] solution = playAlphaBeta(0,MIN,MAX,true,state);
        System.out.println(currentDepth + " max>>>>>");

        //System.out.println(maxDepth);
        endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        return solution[0];
    }

    private void evaluateDeathMax(State currentState)
    {
        if (currentState.isMerged())
        {currentDepth = maxDepth; }

        if (difficulty(currentState.getCells()))
        {currentDepth = minDepth; }

        if(super.allPossibilities(super.getIndexes(),
                currentState.getCells()).size() < 9 &&
                currentDepth < maxDepth &&
                !difficulty(currentState.getCells()))
        {currentDepth +=1;}

        if(currentDepth > minDepth &&
                super.allPossibilities(super.getIndexes(),
                        currentState.getCells()).size() > 6  ||
                difficulty(currentState.getCells()))
        {currentDepth -=1;}
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
        endTime = System.currentTimeMillis();
        timeElapsed = endTime - startTime;
        if (depth >= this.currentDepth && timeElapsed > 2*1200) {

            value = state.getMe().getSeeds() - state.getOtherPlayer().getSeeds();
            indexScore[1] = value;
            return indexScore;

        }
        if(state.endOfGame()) {
            value =  state.getMe().getSeeds() - state.getOtherPlayer().getSeeds();
            indexScore[1] = value *3;
            return indexScore;
        }
        if(state.seedsPlayerCells(state.getOtherPlayer()) == 0 &&
                depth != 0 && depth % 2 == 1)
        {
            indexScore[1] = MAX;
            return indexScore;
        }

        if(state.seedsPlayerCells(state.getMe()) == 0 &&
                depth != 0 && depth % 2 == 0)
        {
            indexScore[1] = MIN;
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

                State newState = playAction(nodes.get(i),state.getMe(),state.clone());
                value = playAlphaBeta(depth +1 ,alpha , beta ,false,newState)[1];
                //System.out.println("value = " +value+" best = "+ best);

                if(value > best){
                    best = value;
                    indexScore[0] = nodes.get(i);
                    indexScore[1] = best;
                }
                alpha = Math.max(alpha, best);
                if(beta <= alpha) {break;}
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
                State newState = playAction(nodes.get(i),state.getOtherPlayer() ,state.clone());
                value = playAlphaBeta(depth +1 ,alpha , beta ,true, newState)[1];

                if(value < best){
                    best = value;
                    indexScore[1] = best;
                }
                beta = Math.min(beta, best);
                if(beta <= alpha) {break;}
            }
            //System.out.println(depth+" min>>>>>"+indexScore[0] + " " + indexScore[1]);
            return indexScore;
        }
    }


    @Override
    public String toString() {
        return " Stratégie AlphaBeta ";
    }


    private State playAction(int coup, Player player, State myState)
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
            player.addSeeds(gains);
            myState.setNbSeedsInGame(myState.getNbSeedsInGame() - gains);
            cellules[coup] = 0;
            coup = GameEngine.precedentPosition(coup,myState.getNbCells());

        }
        myState.setCells(cellules);
        //System.out.println(isMe + " me  : " + myState.getMe().getSeeds() +
        //        "  other Player  : " + myState.getOtherPlayer().getSeeds());
        //System.out.println((myState.getMe().getSeeds() - myState.getOtherPlayer().getSeeds()));

        return myState;
    }

    private boolean difficulty(int[] cells)
    {
        for (int i : cells) {
            if (i > 12 ) return true;
        }
        return false;
    }

}

package Intelligence;

import java.util.ArrayList;
import java.util.List;

import core.GameEngine;
import core.Player;
import core.State;

/**
 * Cette classe est la stratégie de notre IA
 */
public class ComputerStrategy extends Intelligence {
    private State state;
    private int depthMax = 2;
    private int currentScore ;

    public ComputerStrategy(int nbCellsPlayer, int[] indexes , int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
    }

    @Override
    public int chooseCell(State state) {
        this.state = state.clone();
        this.currentScore = state.getGame().totalNbSeed;
        int index  =  minMaxValue();
        //System.out.println("resultat :"+index) ;
        return index ;
    }

    public int minMaxValue()
    {
        ArrayList<Integer> possibilities = this.allPossibilities(super.getIndexes(), this.state.getGame().getCells());
        int action = possibilities.get(0);
        int max = -this.currentScore;

        for (int i = 0; i < possibilities.size(); i++) {

            State newState = state.clone();
            newState.depth += 1;
            newState = playAction(possibilities.get(i), true, newState);

            int newScore = mini(newState);
            if (newScore > max)
            {
                max = newScore;
                action = possibilities.get(i);
            }

        }
        return action;
    }


    private int mini(State myState) {
        int min = this.currentScore;
        if (myState.depth > depthMax || myState.getGame().endOfGame()) {
            return  myState.gain ;
        } else {

            ArrayList<Integer> possibilities = this.allPossibilities(super.getOtherIndexes(), myState.getGame().getCells());

            for (int i = 0; i < possibilities.size(); i++) {
                State newState = myState.clone();

                newState = playAction(possibilities.get(i), false, newState);
                newState.depth += 1;

                int newScore = maxi(newState);
                if (min < newScore )
                {
                    min = newScore;
                }
            }
        }
        return min;
    }


    private int maxi(State myState)
    {
        int max = - currentScore;

        if (myState.depth > depthMax || myState.getGame().endOfGame()) {
            return   myState.gain ;
        } else {
            ArrayList<Integer> possibilities = this.allPossibilities(super.getIndexes(), myState.getGame().getCells());
            for (int i = 0; i < possibilities.size(); i++) {
                State newState = myState.clone();

                newState = playAction(possibilities.get(i), true , newState);
                newState.depth += 1;

                int newScore = mini(newState);
                if (newScore > max )
                {
                    max = newScore;
                }
            }
        }
        return max;
    }
    private State playAction(int coup, boolean isComp, State myState)
    {
        //System.out.println("---------------------------------------");

        if(isComp) {
        //    System.out.println("Play coup : "+coup+" || Computer ");

        }
        else {
         //   System.out.println("Play coup : " + coup + " || player " );
        }
        int nbSeedsIn = myState.getGame().getCells()[coup];

        int[] cellules = myState.getGame().getCells().clone();
        cellules[coup] = 0;
        myState.getGame().setCells(cellules);

        int position = coup;
        int initialPosition = coup;

        for (int j = 0; j < nbSeedsIn; j++) {
            position = GameEngine.nextPosition(position, myState.getGame().getNbCells(), initialPosition);

            cellules = myState.getGame().getCells().clone();
            cellules[position] = cellules[position] +1;
            myState.getGame().setCells(cellules);

        }
        position= GameEngine.precedentPosition(position, myState.getGame().getNbCells());
        while (myState.getGame().getCells()[position] == 2 || myState.getGame().getCells()[position] == 3) {
            int gains = myState.getGame().getCells()[position];
            if(isComp) {
                myState.getGame().computer.addSeeds(gains);
                myState.gain += gains;
            }
            else {
                myState.getGame().player.addSeeds(gains);
                myState.gain -= gains;
            }
            myState.getGame().nbSeedsInGame -= gains;

            cellules = myState.getGame().getCells().clone();
            cellules[position] = 0;
            myState.getGame().setCells(cellules);
            position= GameEngine.precedentPosition(position, myState.getGame().getNbCells());

        }

        //System.out.println("Computer "+" || gain "+ myState.gain);
        //System.out.println("---------------------------------------");

        return myState;
    }


    /**
     *  cette methode permet de filter les possible choix du joueur
     * @return tableau des possiblité
     * si pour paire = 0
     * si pour impaire = 1
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
        //System.out.println(possibilities+" || position depth :"+ myState.depth);
        return possibilities;
    }

    @Override
    public String toString() {
        return " Stratégie COMPUTER ";
    }

}

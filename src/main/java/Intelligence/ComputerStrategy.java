package Intelligence;

import awele.Solution;

import java.util.ArrayList;

import core.GameEngine;
import core.Player;
import core.Position;

/**
 * Cette classe est la stratégie de notre IA
 */
public class ComputerStrategy extends Intelligence {
    private Position position;
    private int depthMax = 3;
    private int currentScore ;

    public ComputerStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }

    @Override
    public int chooseCell(int[] cells) {
        position = super.getCurrentPosition().clone();
        this.currentScore = (position.getGame().totalNbSeed);
        int index  =  minMaxValue();
        System.out.println("resultat :"+index) ;
        return index ;
    }

    public int minMaxValue()
    {
        ArrayList<Integer> possibilities = this.allPossibilities(position.getGame().computer, position);
        int action = possibilities.get(0);
        int max = - this.currentScore;

        for (int i = 0; i < possibilities.size(); i++) {

            Position newPosition = position.clone();
            newPosition.depth += 1;
            newPosition = playAction(possibilities.get(i), true, newPosition);

            int newScore = mini(newPosition);
            if (newScore > max)
            {
                max = newScore;
                action = possibilities.get(i);
            }

        }
        return action;
    }


    private int mini(Position myPosition) {
        int min = this.currentScore;
        if (myPosition.depth > depthMax || myPosition.getGame().endOfGame()) {
            return  myPosition.getGame().computer.getSeeds() - myPosition.getGame().player.getSeeds() ;
        } else {

            ArrayList<Integer> possibilities = this.allPossibilities(myPosition.getGame().player, myPosition);

            for (int i = 0; i < possibilities.size(); i++) {
                Position newPosition = myPosition.clone();

                newPosition = playAction(possibilities.get(i), false, newPosition);
                newPosition.depth += 1;

                int newScore = maxi(newPosition);
                if (min < newScore )
                {
                    min = newScore;
                }
            }
        }
        return min;
    }


    private int maxi(Position myPosition)
    {
        int max = - currentScore;

        if (myPosition.depth > depthMax || myPosition.getGame().endOfGame()) {
            return   myPosition.getGame().computer.getSeeds() - myPosition.getGame().player.getSeeds() ;
        } else {
            ArrayList<Integer> possibilities = this.allPossibilities(myPosition.getGame().computer, myPosition);
            for (int i = 0; i < possibilities.size(); i++) {
                Position newPosition = myPosition.clone();

                newPosition = playAction(possibilities.get(i), true , newPosition);
                newPosition.depth += 1;

                int newScore = mini(newPosition);
                if (newScore > max )
                {
                    max = newScore;
                }
            }
        }
        return max;
    }
    private Position playAction(int coup, boolean isComp, Position myPosition)
    {
        System.out.println("---------------------------------------");

        if(isComp) {
            System.out.println("Play coup : "+coup+" || Computer ");

        }
        else {
            System.out.println("Play coup : " + coup + " || player " );
        }
        int nbSeedsIn = myPosition.getGame().getCells()[coup];
        myPosition.getGame().getCells()[coup] = 0;
        int position = coup;
        int initialPosition = coup;

        for (int j = 0; j < nbSeedsIn; j++) {
            int[] cellules = myPosition.getGame().getCells();
            cellules[position] = cellules[position] +1;
            myPosition.getGame().setCells(cellules);
            coup = position;
            position = GameEngine.nextPosition(position, myPosition.getGame().getNbCells(), initialPosition);

        }
        while (myPosition.getGame().getCells()[coup] == 2 || myPosition.getGame().getCells()[coup] == 3) {
            int gains = myPosition.getGame().getCells()[coup];
            if(isComp) {
                myPosition.getGame().computer.addSeeds(gains);
            }
            else {
                myPosition.getGame().player.addSeeds(gains);
            }
            myPosition.getGame().nbSeedsInGame -= gains;
            myPosition.getGame().getCells()[coup] = 0;
            coup = GameEngine.precedentPosition(coup, myPosition.getGame().getNbCells());

        }
        if(isComp) {
            System.out.println("Computer "+" || nb seeds "+myPosition.getGame().computer.getSeeds());

        }
        else {
            System.out.println("player " + " || nb seeds " + myPosition.getGame().player.getSeeds());
        }
        System.out.println("---------------------------------------");

        return myPosition;
    }


    /**
     *  cette methode permet de filter les possible choix du joueur
     * @return tableau des possiblité
     * si pour paire = 0
     * si pour impaire = 1
     */
    private ArrayList<Integer> allPossibilities(Player player, Position myPosition)
    {
        ArrayList<Integer> possibilities = new ArrayList<>();
        for (int index: player.getIndexes()
             ) {
            if (super.possible(index,myPosition.getGame().getCells()))
            {
                possibilities.add(index);
            }
        }
        System.out.println(possibilities+" || position depth :"+myPosition.depth);
        return possibilities;
    }

    @Override
    public String toString() {
        return " Stratégie COMPUTER ";
    }

}

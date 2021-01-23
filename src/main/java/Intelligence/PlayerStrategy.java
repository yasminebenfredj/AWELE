package Intelligence;

import core.State;

import java.util.Scanner;

/**
 * Cette classe est la stratégie de l'autre joueur
 */
public class PlayerStrategy extends Intelligence {

    public PlayerStrategy(int nbCellsPlayer, int[] indexes,  int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
    }

    @Override
    public int chooseCell(State state) {
        System.out.println("Entrer le numéro de la case que vous voulez jouer parmis les suivantes : ");
        printCells();
        Scanner scanne = new Scanner(System. in );
        int cell = scanne.nextInt();
        return cell-1 ;
    }

    public void printCells() {
        String output = "| ";
        for (int i : super.getIndexes()) {
            output+=  (i+1) + " | ";
        }
        System.out.println(output);

    }

    @Override
    public String toString() {
        return " YOU ";
    }
}

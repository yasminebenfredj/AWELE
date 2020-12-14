package Intelligence;

import java.util.Scanner;

/**
 * Cette classe est la stratégie de l'autre joueur
 */
public class PlayerStrategy extends Intelligence {

    public PlayerStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }

    @Override
    public int chooseCell(int[] cells) {
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

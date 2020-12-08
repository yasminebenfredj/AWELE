package Intelligence;

import java.util.Scanner;

/**
 * Cette classe est la strategie de l'autre joueur
 */
public class PlayerStrategy extends Intelligence {

    public PlayerStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
    }

    @Override
    public int chooseCell() {
        System.out.println("Entrer le numero de la case que vous voulez jouer parmis les suivantes : ");
        printCells();
        Scanner scanne = new Scanner(System. in );
        int cell = scanne.nextInt();
        return cell-1 ;
    }

    @Override
    public String toString() {
        return "YOU";
    }

    public void printCells()
    {
        String output = "| ";
        for (int i : super.getIndexes()) {
            output+=  (i+1) + " | ";
        }
        System.out.println(output);

    }
}

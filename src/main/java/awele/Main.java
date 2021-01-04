package awele;

import core.GameEngine;

public class Main {
    public static void main(String[] args) {
        /**
         * PARAMETRE DU JEU
         **/
        int nbCells = 12;
        int nbSeeds = 4;
        boolean statMode = false;

        /**
         * LANCEMENT DU JEU
         */
        GameEngine game = new GameEngine(nbCells,nbSeeds);
        int computerWins = 0;
        int playerWins = 0;
        int draw = 0;
        if (statMode){
            for (int i = 0; i < 100; i++) {
                game.play();
                int seedsDifference = game.computer.getSeeds() - game.player.getSeeds();
                if (seedsDifference > 0){
                    computerWins ++;
                }
                else if (seedsDifference < 0){
                    playerWins ++;
                }
                else {
                    draw ++;
                }
                game = new GameEngine(nbCells,nbSeeds);
            }
            System.out.println("Computer winrate : " + computerWins);
            System.out.println("Player winrate : " + playerWins);
            System.out.println("Draw rate : " + draw);
        }
        else {
            game.play();
        }
    }
}

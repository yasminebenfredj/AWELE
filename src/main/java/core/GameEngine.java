package core;

import utility.Colors;
import java.io.*;

public class GameEngine {
    public State state ;

    public GameEngine(int nbCells , int nbSeeds){
        this.state = new State(nbCells, nbSeeds);
    }

    /**
     * Cette methode permet de lancer une partie du jeu AWELE :
     * 1) le jeu va tourner jusqu'a ce qu'un des joueur gagne la partie
     * 2) les tableau mergent lorsque le nombre de graine restant en jeu se divise par 2 ,
     * on passe alors à 6 cases par joueur au lieu de 12.
     *
     * 3) si un joueur est affamé le jeu s'arrete et l'autre joueur collecte les graine en jeu
     */
    public void play(){
        System.out.print("\n<<< ***** >>>  Debut du jeu  <<< ***** >>>\n");
        int i = 1;

        while (!state.endOfGame() && state.seedsPlayerCells(state.getComputer()) != 0) {
            System.out.println("\n <<<<< Tour N°  "+ i +" >>>>> ");
            checkMerge();
            printTable();
            System.out.println("\n      *** Joueur " + state.getComputer().getPlayerNumber() + " -" + state.getComputer().toString()  + "-*** ");
            playTurn(state.getComputer());
            if(state.endOfGame() || state.seedsPlayerCells(state.getPlayer()) == 0) {break;}
            checkMerge();
            printTable();
            System.out.println("\n      *** Joueur " + state.getPlayer().getPlayerNumber() + " -" + state.getPlayer().toString()  + "-*** ");
            playTurn(state.getPlayer());
            printScore();
            i++;
        }
        finalAction();
    }

    private void checkMerge()
    {
        if (!state.isMerged() && state.getNbSeedsInGame() <= state.getTotalNbSeed()/2) {
            System.out.println("\n<<< *** >>>\nIl ne reste plus que "+ state.getNbSeedsInGame() +" graines dans le jeu. Le plateau est merger. \n<<< *** >>>\n");
            state.mergeCells(6);
            state.setIsMerged() ;
        }
    }

    /**
     * Cette methode permet au joueur de jouer son tour :
     * 1) recupere l'indice de la cellule que le joueur va joueur
     * 2) récupere les graine dans cette meme cellule
     * 3) distribue les graine dans les cellules suivante
     * 4) lui permet de collecter les graine qu'il a gagner
     * @param player le joueur qui joue le tour
     */
    public void playTurn (Player player) {
        long startTime = System.currentTimeMillis();
        state.setPlayerNumber( player.getPlayerNumber());
        int choice = player.chooseCell(state.clone());  // indice de la case choisi

        long endTime = System.currentTimeMillis();
        long timeElapsed = endTime - startTime;
        System.out.println("Temps de reflexion : " + timeElapsed + "ms");

        checkValidate(choice, player);
        int nbSeedsIn = state.getCells()[choice]; // nombre de graines dans la case choisi
        state.getCells()[choice] = 0 ; // on prend tous les graines de la case choisi pour jouer un tour

        System.out.println("Ramasse "+ nbSeedsIn +" graines de la case N° : " +Colors.RED+ (choice + 1) + Colors.RESET +" .");

        int initialPosition = choice;
        int position = nextPosition(choice, state.getNbCells(),state.getNbCells()*2+1) ;//this.nbCells*2+1 :n'importe quel nombre  > nbCells*2 marche
        for (int i = 1  ; i <= nbSeedsIn ; i++ ) {

            state.getCells()[position] += 1 ;
            System.out.println("Ajoute 1 graine à la Case  N° : " + (position + 1) + " .");
            choice = position;
            position = nextPosition(position, state.getNbCells(),initialPosition);

        }
        collectSeeds(player , choice); //on collecte les graines à partir de la dernière case semer (qui est choice)
    }

    /**
     * Cette methode permet de collecter les graine dans les cases precedant le dernier mouvement d'un joueur
     * il ne collecte que les cases qui contienent 2 ou 3 graines.
     * @param player le joueur qui vient de distribuer ses graines, et le meme qui va en collecter
     */
    public void collectSeeds(Player player , int currentIndex){
        while (state.getCells()[currentIndex] == 2 || state.getCells()[currentIndex] == 3 ) {
            int gains = state.getCells()[currentIndex] ;

            System.out.println(">> Récolte " + gains + " graines de la case N° " + (currentIndex + 1));
            player.addSeeds(gains);
            state.setNbSeedsInGame(state.getNbSeedsInGame()-gains) ;
            state.getCells()[currentIndex] = 0;
            currentIndex = precedentPosition(currentIndex, state.getNbCells());
        }
        System.out.println(">>> Case N° " + ( currentIndex + 1 )+" contient " + state.getCells()[currentIndex] + " graines. Pas de récolte. \n");
    }


    /**
     * Cette methode permet de donner la case suivante dans laquel le joueur va poser une graine
     * Si On arrive à la case 1 on repart à celle n°13
     * Si On arrive à la case 24 on repart à celle n°12
     * @param currentPosition
     * @return
     */
    public static int nextPosition(int currentPosition, int nbCells, int initialPosition) {
        if ((currentPosition + 1) % (nbCells * 2) == initialPosition){
            return (currentPosition + 2 ) % (nbCells * 2);
        }
        return (currentPosition + 1 )% (nbCells * 2);
    }

    /**
     * Cette methode permet de donner la case précédente dans laquelle le joueur va verifier s'il peut récupérer des graines
     * Si On arrive à la case 1 on repart à celle n°13
     * Si On arrive à la case 24 on repart à celle n°12
     * @param currentPosition
     * @return
     */
    public static int precedentPosition(int currentPosition, int nbCells ) {
        int previousPosition = (currentPosition - 1 ) % (nbCells * 2) ;
        if (previousPosition < 0 ) {
            previousPosition += nbCells*2;
        }
        return previousPosition;
    }

    /**
     * Cette methode permet de terminer la partie :
     * 1) afficher la raison de la fin
     * 2) afficher le score final
     * 3 ) afficher le gagant
     *
     */
    private void finalAction() {
        System.out.println(Colors.BLUE);
        System.out.println("********************************* FIN DU JEU ***********************************************");

        String printEndGameReason = "";
        if(state.getNbSeedsInGame() < 8 ) {
            printEndGameReason = "Le nombre de graines actuelles en jeu est inférieurs à 8. ";
        }
        else if (state.seedsPlayerCells(state.getComputer()) == 0){
            state.getPlayer().addSeeds(state.getNbSeedsInGame());
            state.setNbSeedsInGame(0);
            printEndGameReason = "Le joueur" + state.getComputer().toString() + state.getComputer().getPlayerNumber() + " a été affamé. " ;

        }
        else if (state.seedsPlayerCells(state.getPlayer()) == 0){
            state.getComputer().addSeeds(state.getNbSeedsInGame());
            state.setNbSeedsInGame(0);
            printEndGameReason = "Le joueur" + state.getPlayer().toString() + state.getPlayer().getPlayerNumber() + " a été affamé. " ;

        }
        System.out.println(">> " + printEndGameReason);
        this.getWinner();
        System.out.println(Colors.RESET);

    }
    /**
     * Cette methode affiche le score et le joueur gagnant à la fin du jeu
     */
    private void getWinner() {
        System.out.print(Colors.YELLOW);
        System.out.println("********************************** GAGNANT ************************************************** ");

        int seedsDifference = state.getComputer().getSeeds() - state.getPlayer().getSeeds() ;
        if (seedsDifference > 0){
            System.out.println(">> Joueur " + state.getComputer().getPlayerNumber() + Colors.PARTY + state.getComputer().toString() + Colors.PARTY+ " remporte la partie" );
        }
        else if (seedsDifference < 0){
            System.out.println(">> Joueur " + state.getPlayer().getPlayerNumber() + Colors.PARTY + state.getPlayer().toString() + Colors.PARTY+ " remporte la partie" );
        }
        else {
            System.out.println(" Égalité entre le joueur " + state.getComputer().toString() + " et le joueur " + state.getPlayer().toString()+"\n");
        }
        System.out.println("******************************* SCORE FINAL ************************************************");
        System.out.println(" Joueur " + state.getComputer().getPlayerNumber() + " " + state.getComputer().toString() + " : " + state.getComputer().getSeeds());
        System.out.println(" Joueur " + state.getPlayer().getPlayerNumber() + " " + state.getPlayer().toString() + " : " + state.getPlayer().getSeeds());
        System.out.println("******************************************************************************************** ");
        System.out.println(Colors.RESET);
    }

    /**
     * Cette méhode print tous les cases avec le nombre de graines qu'elles contiennent
     */
    private  void printTable() {
        String barre = "________________________________";
        String indexes1 = "Numéro des cases          :  ";
        String seeds1 = "Nombre de graine par case :  ";
        String indexes2 = "Numéro des cases          :  ";
        String seeds2 = "Nombre de graine par case :  ";
        for (int i = 0; i < state.getNbCells()  ; i++) {
            barre += "______";
            indexes1 += "|__"+( i+1 )+"_|";

            if (i < 9 ) {
                seeds1 += "|  "+ state.getCells()[i]+" |";
            }else {
                seeds1 += "|   "+ state.getCells()[i]+" |";
            }

        }
        for (int i = state.getNbCells() * 2 -1 ; i >= state.getNbCells()  ; i--) {
            indexes2 += "|_"+( i+1 )+"_|";
            seeds2 += "|  "+ state.getCells()[i]+" |";
        }

        System.out.println(Colors.PURPLE + barre);
        System.out.println(" < Plateau 1 > ");
        System.out.println(indexes1);
        System.out.println(seeds1);
        System.out.println(barre);
        System.out.println(" < Plateau 2 > ");
        System.out.println(indexes2);
        System.out.println(seeds2);
        System.out.println(barre + Colors.RESET);
    }

    /**
     * Cette méthode print le score actuelle
     */
    public  void  printScore() {
        System.out.println(Colors.RED +"________________________________");
        System.out.println(" < *** > SCORE ACTUELLE < *** > ");

        System.out.println("Joueur " + state.getComputer().getPlayerNumber() + " " + state.getComputer().toString() + " : " + state.getComputer().getSeeds());
        System.out.println("Joueur " + state.getPlayer().getPlayerNumber() + " " + state.getPlayer().toString() + " : " + state.getPlayer().getSeeds());
        System.out.println("________________________________"+ Colors.RESET);

    }

    /**
     * Cette methode va verifier qu'il y a des graine dans la cellule que le joueur souhaite jouer
     * @param choice l'indice d ela cellule choisi
     */
    public void checkValidate( int choice, Player p ) {
        if( state.getCells()[choice] == 0) {
            System.out.println(Colors.RED+ "FAUX PAS : UNE CASE VIDE A ÉTÉ CHOISI ! "+ Colors.RESET);
            //throw new WrongMoveException("Une case qui ne contient pas de graines a été choisi !") ;
            playTurn(p);
        }

    }

    @Override
    public GameEngine clone()
    {
        GameEngine gameEngine = new GameEngine(state.getNbCells(), state.getNbSeeds());
        gameEngine.state = state.clone();
        return gameEngine;

    }
}

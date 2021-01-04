package core;

import Exceptions.WrongMoveException;
import utility.Colors;

public class Game {
    int nbCells;
    final int nbSeeds;
    public final Player player ;
    public final Player computer ;
    private int[] cells ;
    private int nbSeedsInGame;
    private final int totalNbSeed ;
    private boolean isMerged ;
    private Position currentPosition ;



    public Game( int nbCells , int nbSeeds){
        this.isMerged = false ;
        this.nbCells = nbCells;
        this.nbSeeds = nbSeeds;
        this.computer = new Player(1,nbCells,0,true);
        this.player =  new Player(2,nbCells,0,false);
        this.nbSeedsInGame = this.nbSeeds * this.nbCells * 2;
        this.totalNbSeed = this.nbSeeds * this.nbCells * 2 ;
        cellsGeneration();

        this.currentPosition = new Position(this, this.computer,this.player,this.cells);
        computer.setCurrentPosition(this.currentPosition);
        player.setCurrentPosition(this.currentPosition);
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


        while (!this.endOfGame() ) {
            System.out.println("\n <<<<< Tour N°  "+ i +" >>>>> ");

            if (!isMerged && this.nbSeedsInGame <= this.totalNbSeed/2) {
                System.out.println("\n<<< *** >>>\nIl ne reste plus que "+ this.nbSeedsInGame +" graines dans le jeu. Le plateau est merger. \n<<< *** >>>\n");
                this.mergeCells(6);
                isMerged = true ;
            }

            printTable();
            System.out.println("\n      *** Joueur " + computer.getPlayerNumber() + " -" + computer.toString()  + "-*** ");
            if (!this.endOfGame()){
                playTurn(computer);
            }
            else {
                break;
            }
            printTable();
            System.out.println("\n      *** Joueur " + player.getPlayerNumber() + " -" + player.toString()  + "-*** ");
            if (!this.endOfGame()){
                playTurn(player);
            }
            else {
                break;
            }


            printScore();
            i++;
        }

        if (this.seedsPlayerCells(this.computer) == 0){//si on affame l'adversaire on gagne tous les pions du plateau.
            this.player.addSeeds(this.nbSeedsInGame);
    }
        else if (this.seedsPlayerCells(this.player) == 0){//si on affame l'adversaire on gagne tous les pions du plateau.
            this.computer.addSeeds(this.nbSeedsInGame);
        }
        this.getWinner();
    }
    private void finalAction() {

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
        int choice = player.chooseCell(this.cells);  // indice de la case choisi
        checkValidate(choice);
        int nbSeedsIn = this.cells[choice]; // nombre de graines dans la case choisi
        this.cells[choice] = 0 ; // on prend tous les graines de la case choisi pour jouer un tour

        System.out.println("Ramasse "+ nbSeedsIn +" graines de la case N° : " + (choice + 1) + " .");

        int initialPosition = choice;
        int position = nextPosition(choice, this.nbCells,this.nbCells*2+1) ;//this.nbCells*2+1 :n'importe quel nombre  > nbCells*2 marche
        for (int i = 1  ; i <= nbSeedsIn ; i++ ) {

            this.cells[position] += 1 ;
            System.out.println("Ajoute 1 graine à la Case  N° : " + (position + 1) + " .");
            choice = position;
            position = nextPosition(position, this.nbCells,initialPosition);

        }
        collectSeeds(player , choice); //on collecte les graines à partir de la dernière case semer (qui est choice)
    }

    /**
     * Cette methode permet de collecter les graine dans les cases precedant le dernier mouvement d'un joueur
     * il ne collecte que les cases qui contienent 2 ou 3 graines.
     * @param player le joueur qui vient de distribuer ses graines, et le meme qui va en collecter
     */
    public void collectSeeds(Player player , int currentIndex){
        while (this.cells[currentIndex] == 2 || this.cells[currentIndex] == 3 ) {
            int gains = this.cells[currentIndex] ;

            System.out.println(">> Récolte " + gains + " graines de la case N° " + (currentIndex + 1));
            player.addSeeds(gains);
            this.nbSeedsInGame -= gains;
            this.cells[currentIndex] = 0;
            currentIndex = precedentPosition(currentIndex, this.nbCells);
        }
        this.currentPosition = new Position(this, this.computer,this.player,this.cells);
        player.setCurrentPosition(currentPosition);
        System.out.println(">>> Case N° " + ( currentIndex + 1 )+" contient " + this.cells[currentIndex] + " graines. Pas de récolte. \n");
    }

    /**
     * Cette méthode merge les cases du jeu pour passer d'un plateau à 12 cases à un plateau à 6 cases
     * @param newNbCells le nouveau nombre de cellule
     */
    private void mergeCells(int newNbCells) {
        int[] newCells = new int[newNbCells*2] ;
        for (int i = 0; i < this.nbCells  ; i++ ) {
            newCells[i] = this.cells[i*2] + this.cells[i*2 + 1] ;
        }
        this.nbCells = newNbCells;
        this.cells = newCells;

        this.player.mergeCells(newNbCells);
        this.computer.mergeCells(newNbCells);

    }

    /**
     * This method generates the cells and put nbSeeds in each cell
     */
    private  void cellsGeneration() {
        this.cells = new int[this.nbCells * 2] ;
        for (int i = 0; i < this.nbCells * 2  ; i++) {
            this.cells[i] = this.nbSeeds;
        }
    }

    /**
     * This method computes the number of seeds in the cells belonging to a given player
     * @param player the given player
     * @return the number of seeds in the cells belonging to the player
     */
    private int seedsPlayerCells(Player player) {
        int seedsInPlayerCells = 0 ;
        for (int i = 0; i < player.getIndexes().length; i++) {
                seedsInPlayerCells += this.cells[player.getIndexes()[i]] ;
        }
        return seedsInPlayerCells ;
    }

    /**
     * Cette methode permet de donner la case suivante dans laquel le joueur va poser une graine
     * Si On arrive à la case 1 on repart à celle n°13
     * Si On arrive à la case 24 on repart à celle n°12
     * @param currentPosition
     * @return
     */
    public static int nextPosition(int currentPosition, int nbCells,int initialPosition) {
        if (currentPosition + 1 == initialPosition){
            return (currentPosition + 2 ) % (nbCells * 2);
        }
        return (currentPosition +1 )% (nbCells * 2);
    }

    /**
     * Cette methode permet de donner la case précédente dans laquelle le joueur va verifier s'il peut récupérer des graines
     * Si On arrive à la case 1 on repart à celle n°13
     * Si On arrive à la case 24 on repart à celle n°12
     * @param currentPosition
     * @return
     */
    public static int precedentPosition(int currentPosition, int nbCells) {
        int previousPosition = (currentPosition - 1 )% (nbCells * 2) ;
        if (previousPosition < 0 ) {
            previousPosition += nbCells*2;
        }
        return previousPosition;
    }

    /**
     * Le jeu continue tant que:
     * 1) Le nombre des graine en jeu est supérieur ou égal à 8
     * 2) Aucun des joueurs n'est affamer
     * 3) Aucun des joueur ne possède + que la moitié des graines (ici 48)
     *
     * @return
     */
    public boolean endOfGame() {
        boolean currentSeedsInCells = this.nbSeedsInGame < 8 ;
        boolean seedsInComputerCells = this.seedsPlayerCells(this.computer) == 0 ;
        boolean seedsInPlayerCells = this.seedsPlayerCells(this.player) == 0 ;
        return currentSeedsInCells || seedsInComputerCells || seedsInPlayerCells ;
    }


    /**
     * Cette methode affiche le score et le joueur gagnant à la fin du jeu
     */
    private void getWinner() {
        System.out.println(Colors.BLUE);

        System.out.println("********************************* FIN DU JEU ***********************************************");

        int seedsDifference = computer.getSeeds() - player.getSeeds() ;
        if (seedsDifference > 0){
            System.out.println(">> Joueur " + computer.getPlayerNumber() + Colors.PARTY + computer.toString() + Colors.PARTY+ " remporte la partie" );
        }
        else if (seedsDifference < 0){
            System.out.println(">> Joueur " + player.getPlayerNumber() + Colors.PARTY + player.toString() + Colors.PARTY+ " remporte la partie" );
        }
        else {
            System.out.println(" Égalité entre le joueur " + computer.toString() + " et le joueur " + player.toString()+"\n");
        }
        System.out.println(this.printEndGameReason());

        System.out.println("******************************* SCORE FINAL ************************************************");
        System.out.println(" Joueur " + computer.getPlayerNumber() + " " + computer.toString() + " : " + computer.getSeeds());
        System.out.println(" Joueur " + player.getPlayerNumber() + " " +player.toString() + " : " + player.getSeeds());
        System.out.println("******************************************************************************************** ");

        System.out.println(Colors.RESET);
    }

    /**
     * Cette methode eprmet d'afficher la raison de la fin de la partie
     * @return
     */
    private String printEndGameReason(){
        System.out.print(" Raison de fin du jeu : ");
        if (this.nbSeedsInGame < 8){
            return " Le nombre de graines actuelles dans les cellules est inférieurs à 8 ";
        }
        else if (this.seedsPlayerCells(this.computer) == 0){
            return " Le nombre de graines des cases correspondant au joueur " + computer.toString() + computer.getPlayerNumber() + "\n est nulle.Il a été affamé. " ;
        }
        else if (this.seedsPlayerCells(this.player) == 0) {
            return " Le nombre de graines des cases correspondant au joueur " + player.toString() + player.getPlayerNumber() + "\n est nulle.Il a été affamé. " ;
        }
        return null;
    }

    /**
     * This method prints all the cells with the number of their seeds
     */
    private  void printTable() {
        String barre = "________________________________";
        String indexes1 = "Numéro des cases          :  ";
        String seeds1 = "Nombre de graine par case :  ";
        String indexes2 = "Numéro des cases          :  ";
        String seeds2 = "Nombre de graine par case :  ";
        for (int i = 0; i < nbCells  ; i++) {
            barre += "______";
            indexes1 += "|__"+( i+1 )+"_|";

            if (i < 9 ) {
                seeds1 += "|  "+ this.cells[i]+" |";
            }else {
                seeds1 += "|   "+ this.cells[i]+" |";
            }

        }
        for (int i = nbCells * 2 -1 ; i >= nbCells  ; i--) {
            indexes2 += "|_"+( i+1 )+"_|";
            seeds2 += "|  "+ this.cells[i]+" |";


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
     * This method prints the current score after each
     */
    public  void  printScore() {
        System.out.println(Colors.RED +"________________________________");
        System.out.println(" < *** > SCORE ACTUELLE < *** > ");

        System.out.println("Joueur " + computer.getPlayerNumber() + " " + computer.toString() + " : " + computer.getSeeds());
        System.out.println("Joueur " + player.getPlayerNumber() + " " + player.toString() + " : " + player.getSeeds());
        System.out.println("________________________________"+ Colors.RESET);

    }

    /**
     * Cette methode va verifier qu'il y a des graine dans la cellule que le joueur souhaite jouer
     * @param choice l'indice d ela cellule choisi
     */
    public void checkValidate( int choice ) {
        if( this.cells[choice] == 0) {
            System.out.println(Colors.RED+ "WRONG MOVE : YOU CHOOSE AN EMPTY CELL ! GAME OVER ! "+ Colors.RESET);
            throw new WrongMoveException("A cell with zero seeds was chosen !") ;
        }

    }

    public int[] getCells() {
        return cells;
    }

    public int getNbCells() {
        return nbCells;
    }

    public int getNbSeeds() {
        return nbSeeds;
    }

}

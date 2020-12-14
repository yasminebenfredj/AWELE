
public class Game {
    int nbCells;
    final int nbSeeds;  // Number of Seeds per Cell
    final Player player ;
    final Player computer ;
    private int[] cells ;
    private int nbSeedsInGame; // Number of Seeds still in game(not collected yet)
    private final int totalNbSeed ; // Constant that represents the initial number of seeds
    private boolean isMerged ;
    private Position currentPosition ;

    public static void main(String[] args) {
        Game game = new Game(12,4);
        game.play();
    }

    Game(int nbCells , int nbSeeds){
        this.isMerged = false ;
        this.nbCells = nbCells;
        this.nbSeeds = nbSeeds;
        this.computer = new Player(1,nbCells,0,true);
        this.player =  new Player(2,nbCells,0,false);
        this.currentPosition = new Position(this.computer,this.player,this.cells);
        this.nbSeedsInGame = this.nbSeeds * this.nbCells * 2;
        this.totalNbSeed = this.nbSeeds * this.nbCells * 2 ;
        cellsGeneration();
    }

    public void play(){
        System.out.print("\n<<< ***** >>>  Debut du jeu  <<< ***** >>>\n");
        int i = 1;


        while ( !this.endOfGame() ) {
            System.out.println("\n <<<<< Tour N°  "+ i +" >>>>> ");

            if (!isMerged && this.nbSeedsInGame <= this.totalNbSeed/2) {
                System.out.println("\n<<< *** >>>\nIl ne reste plus que "+ this.nbSeedsInGame +" graines dans le jeu. Le plateau est merger. \n<<< *** >>>\n");
                this.mergeCells(6);
                isMerged = true ;
            }

            printTable();
            System.out.println("\n      *** Joueur 1  - " + computer.toString() + computer.getPlayerNumber() + " - *** ");
            playTurn(computer);

            printTable();
            System.out.println("\n      *** Joueur 2  - " + player.toString() + player.getPlayerNumber() + " - *** ");
            playTurn(player);

            printScore();
            i++;
        }
        //End of the game we print the winner and the final score
        this.getWinner();
    }

    /**
     * This methods plays a turn for a player
     * @param player a player
     */
    public void playTurn (Player player) {
        int choice = player.chooseCell();  // indice de la case choisi
        int nbSeedsIn = this.cells[choice]; // nombre de graines dans la case choisi
        this.cells[choice] = 0 ; // on prend tous les graines de la case choisi pour jouer un tour

        System.out.println("Ramasse "+ nbSeedsIn +" graines de la case N° : " + (choice + 1) + " .");

        int position = nextPosition(choice) ;
        for (int i = 1  ; i <= nbSeedsIn ; i++ ) {

            this.cells[position] += 1 ;
            System.out.println("Ajoute 1 graine à la Case  N° : " + (position + 1) + " .");
            choice = position;
            position = nextPosition(position);

        }
        collectSeeds(player , choice); //on collecte les graines à partir de la dernière case semer (qui est choice)
    }

    /**
     * This method collects the seeds for a player after he plays his turn
     * @param player
     */
    public void collectSeeds(Player player ,int currentIndex){
        while (this.cells[currentIndex] == 2 || this.cells[currentIndex] == 3 ) {
            int gains = this.cells[currentIndex] ;

            System.out.println(">> Récolte " + gains + " graines de la case N° " + (currentIndex + 1));
            player.addSeeds(gains); //on ajoute les graines récoltées au graines du joueur
            this.nbSeedsInGame -= gains; // on soustrait de la somme des graines présente dans le jeu
            this.cells[currentIndex] = 0; // la case devient vide
            currentIndex = precedentPosition(currentIndex);

        }
        this.currentPosition = new Position(this.computer,this.player,this.cells);
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
     * This method computes the number of seeds in the cells belonging to the computer
     * @return the number of seeds in the cells belonging to the computer
     */
    private int seedsInComputerCells() {
        int seedsInPlayerCells = 0 ;
        for (int i = 0; i < this.nbCells*2; i++) {
            if (i%2 == 0){
                seedsInPlayerCells ++ ;
            }
        }
        return seedsInPlayerCells ;
    }

    /**
     * This method computes the number of seeds in the cells belonging to the player
     * @return the number of seeds in the cells belonging to the player
     */
    private int seedsInPlayerCells() {
        int seedsInPlayerCells = 0 ;
        for (int i = 0; i < this.nbCells * 2; i++) {
            if(i%2 != 0){
                seedsInPlayerCells ++ ;
            }
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
    private int nextPosition(int currentPosition) {
        if (currentPosition < this.nbCells && currentPosition > 0 ) {
            return currentPosition - 1 ;
        }
        else if (currentPosition >= this.nbCells && currentPosition < (this.nbCells * 2) - 1 ) {
            return currentPosition + 1 ;
        }
        else if (currentPosition == 0 ) {
            return this.nbCells;
        }
        else {
            return this.nbCells - 1 ;
        }
    }

    /**
     * Cette methode permet de donner la case précédente dans laquelle le joueur va verifier s'il peut récupérer des graines
     * Si On arrive à la case 1 on repart à celle n°13
     * Si On arrive à la case 24 on repart à celle n°12
     * @param currentPosition
     * @return
     */
    private int precedentPosition(int currentPosition) {
        if (currentPosition <= this.nbCells && currentPosition >= 0 ) {
            return currentPosition + 1 ;
        }
        else {
            return currentPosition - 1 ;
        }
    }

    /**
     * Le jeu continue tant que:
     * 1) Le nombre des graine en jeu est supérieur ou égal à 8
     * 2) Aucun des joueurs n'est affamer
     * 3) Aucun des joueur ne possède + que la moitié des graines (ici 48)
     *
     * @return
     */
    private boolean endOfGame() {
        boolean currentSeedsInCells = this.nbSeedsInGame < 8 ;
        boolean seedsInComputerCells = this.seedsInComputerCells() == 0 ;
        boolean seedsInPlayerCells = this.seedsInPlayerCells() == 0 ;
        boolean maxSeedsByPlayer = player.getSeeds() > (this.totalNbSeed/ 2) || (computer.getSeeds() > this.totalNbSeed/2) ;
        return currentSeedsInCells || seedsInComputerCells || seedsInPlayerCells || maxSeedsByPlayer;
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

    private String printEndGameReason(){
        System.out.print(" Raison de fin du jeu : ");
        if (this.nbSeedsInGame < 8){
            return " Le nombre de graines actuelles dans les cellules est inférieurs à 8 ";
        }
        else if (this.seedsInComputerCells() == 0){
            return " Le nombre de graines des cases correspondant au joueur " + computer.toString() + computer.getPlayerNumber() + " est nulle " ;
        }
        else if (this.seedsInPlayerCells() == 0){
            return " Le nombre de graines des cases correspondant au joueur " + player.toString() + player.getPlayerNumber() + " est nulle " ;
        }
        else if (this.player.getSeeds() > (this.totalNbSeed/ 2)){
            return " Le joueur" + player.toString() + player.getPlayerNumber() + " possède plus que la moitié des graines " ;
        }
        else { //this.computer.getSeeds() > (this.totalNbSeed/ 2)
            return " Le joueur" + computer.toString() + computer.getPlayerNumber() + " possède plus que la moitié des graines " ;
        }
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
        for (int i = nbCells; i < nbCells * 2 ; i++) {
            indexes2 += "|_"+( i+1 )+"_|";
            seeds2 += "|  "+ this.cells[i]+" |";


        }
        System.out.println(Colors.PURPLE + barre);
        System.out.println(" < Plateau 1 > ");
        System.out.println(indexes2);
        System.out.println(seeds2);
        System.out.println(barre);
        System.out.println(" < Plateau 2 > ");
        System.out.println(indexes1);
        System.out.println(seeds1);
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

}

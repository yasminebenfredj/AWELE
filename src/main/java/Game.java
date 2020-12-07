public class Game {
    int nbCells;
    final int nbSeeds;
    final Player player ;
    final Player computer ;
    private int[] cells ;
    private int nbSeedsInGame;



    public static void main(String[] args) {
        Game game = new Game(12,4);
        game.play();
    }

    Game(int nbCells , int nbSeeds){
        this.nbCells = nbCells;
        this.nbSeeds = nbSeeds;
        this.computer = new Player(nbCells,0,true);
        this.player =  new Player(nbCells,0,false);
        this.nbSeedsInGame = nbSeeds * nbCells * 2;
        cellsGeneration();
    }

    public void play(){
        System.out.print("\n<<< ***** >>> Debut du jeu <<< ***** >>>\n");
        int i = 1;

        boolean currentSeedsInCells = this.currentSeedsInCells() >= 8 ;
        boolean seedsInComputerCells = this.seedsInComputerCells(this.computer) > 0 ;
        boolean seedsInPlayerCells = this.seedsInPlayerCells(this.player) > 0 ;

        while (player.getSeeds()< this.nbSeedsInGame/2 && computer.getSeeds() < this.nbSeedsInGame/2 && currentSeedsInCells && seedsInComputerCells && seedsInPlayerCells){ //TODO condition d'arrêt à modifier
            System.out.println("\n>>>>> Début du tour N° : "+ i +" <<<<<");

            if (this.nbSeedsInGame <= 48)
            {
                System.out.println("\n<<< *** >>>\nIl ne reste plus que "+ this.nbSeedsInGame +" graines dans le jeu. Le plateau est merger. \n<<< *** >>>\n");
                this.mergeCells(6);
            }



            System.out.println("\n      ** Joueur 1  " + computer.toString() + " ** ");

            playTurn(computer);

            System.out.println("\n      ** Joueur 2  " + player.toString() + " ** ");
            playTurn(player);
            System.out.println("<<<<< Fin du tour N° : "+ i +" >>>>>\n");

            i++;
        }
        System.out.println("<<< ***** >>> Fin du Jeu <<< ***** >>>\n");


    }

    /**
     * This methods plays a turn for a player
     * @param player a player
     */
    public void playTurn (Player player)
    {
        int choice = player.chooseCell();  // indice de la case choisi
        int nbSeedsIn = this.cells[choice]; // nombre de graines dans la case choisi
        this.cells[choice] = 0 ; // on prend tous les graines de la case choisi pour jouer un tour

        System.out.println("Il recupére les "+ nbSeedsIn +" graines de la case N° : " + (choice + 1) + " .");

        int position = nextPosition(choice) ;
        for (int i = 1  ; i <= nbSeedsIn ; i++ ) {

            this.cells[position] += 1 ;
            System.out.println("Il pose une graine dans la case  N° : " + (position + 1) + " .");
            position = nextPosition(position);

        }

        collectSeeds(player , choice , nbSeedsIn); //on collecte les graines à partir de la dernière case semer (qui est choice)
    }

    /**
     * This method collects the seeds for a player after he plays his turn
     * @param player
     */
    public void collectSeeds(Player player ,int currentIndex ,int nbSeedsIn){
        while (this.cells[currentIndex] == 2 || this.cells[currentIndex] == 3 )
        {
            int gains = this.cells[currentIndex] ;
            if (currentIndex < nbSeedsIn) {
                System.out.println(">> Il a récolté " + gains + " graines de la case " + (currentIndex + 1));
                player.addSeeds(gains); //on ajoute les graines récoltées au graine du joueur
                this.nbSeedsInGame -= gains; // on soustrait de la somme des graine presente dans le jeu
                this.cells[currentIndex] = 0; // la case devient vide
                currentIndex += 1;
            }
            else {
                System.out.println(">> Il a récolté " + gains + " graines de la case " + ( currentIndex + 1 ));
                player.addSeeds(gains); //on ajoute les graines récoltées au graine du joueur
                this.nbSeedsInGame -= gains; // on soustrait de la somme des graine presente dans le jeu
                this.cells[currentIndex] = 0 ; // la case devient vide
                currentIndex -= 1 ;

            }
        }
        System.out.println(">> Il arrive sur une case qui contient " + this.cells[currentIndex] + " graines .Il n'a rien à récolter. \n");
    }

    /**
     * Cette méthode merge les cases du jeu pour passer d'un plateau à 12 cases à un plateau à 6 cases
     * @param newNbCells le nouveau nombre de cellule
     */
    private void mergeCells(int newNbCells) {
        int[] newCells = new int[newNbCells] ;
        for (int i = 0; i < nbCells - 1; i+=2 ) {
            newCells[i] = this.cells[i] + this.cells[i + 1] ;
        }
        this.nbCells = newNbCells;
        this.cells = newCells;
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
     * This method gives the current number of seeds remaining in the cells
     * @return the current number of seeds remaining in the cells
     */
    private int currentSeedsInCells(){
        int currentSeedsInCells = 0 ;
        for (int i = 0; i < this.cells.length; i++) {
            currentSeedsInCells += this.cells[i] ;
        }
        return currentSeedsInCells ;
    }


    /**
     * This method computes the number of seeds in the cells belonging to the computer
     * @param computer the player
     * @return the number of seeds in the cells belonging to the computer
     */
    private int seedsInComputerCells(Player computer) {
        int seedsInPlayerCells = 0 ;
        for (int i = 0; i < this.cells.length; i++) {
            if (i%2 == 0){
                seedsInPlayerCells ++ ;
            }
        }
        return seedsInPlayerCells ;
    }

    /**
     * This method computes the number of seeds in the cells belonging to the player
     * @param player the player
     * @return the number of seeds in the cells belonging to the player
     */
    private int seedsInPlayerCells(Player player) {
        int seedsInPlayerCells = 0 ;
        for (int i = 0; i < this.cells.length; i++) {
            if(i%2 != 0){
                seedsInPlayerCells ++ ;
            }
        }
        return seedsInPlayerCells ;
    }



    private  void printTable()
    {

    }




    /**
     * Cette methode permet de donner la case suivante dans laquel le joueur va poser une graine
     * Si On arrive à la case 1 on repart à celle n°13
     * Si On arrive à la case 24 on repart à celle n°12
     * @param currentPosition
     * @return
     */
    private int nextPosition(int currentPosition)
    {
        if (currentPosition < this.nbCells && currentPosition > 0 )
        {
            return currentPosition - 1 ;
        }
        else if (currentPosition >= this.nbCells && currentPosition < 23 ) {
            return currentPosition + 1 ;
        }
        else if (currentPosition == 0 ) {
            return this.nbCells;
        }
        else {
            return this.nbCells - 1 ;
        }

    }

}

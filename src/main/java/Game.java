public class Game {
    int nbCells;
    final int nbSeeds;
    final Player player ;
    final Player computer ;
    private int[] cells ;
    private int totalNbSeed ;


    public static void main(String[] args) {
        Game game = new Game(12,4);
        game.play();
    }

    Game(int nbCells , int nbSeeds){
        this.nbCells = nbCells;
        this.nbSeeds = nbSeeds;
        this.computer = new Player(nbCells,0,true);
        this.player =  new Player(nbCells,0,false);
        this.totalNbSeed = nbSeeds * nbCells * 2 ;


        cellsGeneration();
    }

    public void play(){
        System.out.print("*** Debut du jeu ***");
        
        while (player.getSeeds()>= this.totalNbSeed/2 || computer.getSeeds() >= this.totalNbSeed/2){ //TODO condition d'arrêt à modifier
            playTurn(computer);
            playTurn(player);
        }

    }

    /**
     * This methods plays a turn for a player
     * @param playerC a player
     */
    public void playTurn(Player playerC) {
        int choice = playerC.chooseCell();  // indice de la case choisi

        int nbSeedsIn = playerC.getCells()[choice]; // nombre de graines dans la case choisi
        playerC.getCells()[choice] = 0 ; // on prend tous les graines de la case choisi pour jouer un tour

        for (int i = 1 ; i <= nbSeedsIn ; i++) {
            if (choice - 1 < 0){
                choice = (choice - 1) % (nbCells*2)  ;
                if((choice) % 2 == 0) {
                    playerC.setOneCell(choice);
                }
                else{
                    computer.setOneCell(choice);
                }
            }
            else {
                choice = choice - 1 ;
                if(choice % 2 == 0) {
                    playerC.setOneCell(choice);
                }
                else{
                    computer.setOneCell(choice);
                }
            }
        }

        collectSeeds(playerC , choice , nbSeedsIn); //on collecte les graines à partir de la dernière case semer (qui est choice)
    }

    /**
     * This method collects the seeds for a player after he plays his turn
     * @param player
     */
    public void collectSeeds(Player player ,int choice ,int nbSeedsIn){
        for (int i = choice; i < choice + nbSeedsIn; i++) {
            if (this.cells[i] == 2 || this.cells[i] == 3){ //si une case contient 2 ou 3 graines les joueurs les récolte
                System.out.println("Le joueur : " + player + "a récolté " + this.cells[i] + " graines de la case " + i + " .");
                player.addSeeds(this.cells[i]); //on ajoute les graines récoltées au graine du joueur
                this.cells[i] = 0 ; // la case devient vide
            }
            else { // si la case ne contient ni 2 ni 3 graines on arrête la récolte
                System.out.println("Le joueur arrive sur une case qui contient " + this.cells[i] + " graines .Il arrête donc sa récolte.");
                break;
            }
        }
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
        for (int i = 0; i <= this.nbCells * 2  ; i++) {
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

}

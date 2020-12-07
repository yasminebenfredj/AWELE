public class Game {
    int nbCells;
    final int nbSeeds;
    final Player player ;
    final Player computer ;
    private int[] cells ;


    public static void main(String[] args) {
        Game game = new Game(12,4);
        game.play();
    }

    Game(int nbCells , int nbSeeds){
        this.nbCells = nbCells;
        this.nbSeeds = nbSeeds;
        this.computer = new Player(nbCells,0,true);
        this.player =  new Player(nbCells,0,false);


        cellsGeneration();
    }

    public void play(){
        seedDistribution();

        System.out.print("*** Debut du jeu ***");

        while (true){ //TODO condition d'arrêt à modifier
            playTurn(computer);
            playTurn(player);
        }

    }

    public void playTurn(Player playerC) {
        int choice = playerC.chooseCell();  // indice de la case choisi

        int nbSeedsIn = playerC.getCells()[choice]; // nombre de graines dans la case choisi
        for (int i = 1 ; i <= nbSeedsIn ; i++) {
            if (choice - i < 0){
                choice = (choice - i) % (nbCells*2) + 1 ;
                if((choice) % 2 == 0) {
                    playerC.setOneCell(choice );
                }
                else{
                    computer.setOneCell(choice);
                }
            }
            else {
                if((choice - i) % 2 == 0) {
                    playerC.setOneCell( choice - i);
                }
                else{
                    computer.setOneCell(choice - i);
                }
            }
        }

        collectSeeds(playerC , choice);
    }

    /**
     * Cette méthode récolte les graines
     * @param player
     */
    public void collectSeeds(Player player ,int choice){
        int[] playerCells = player.getCells();
        int[] newPlayerCells = playerCells.clone() ;

        for (int i = 0; i < playerCells.length; i++) {
            if (playerCells[i] == 2 || playerCells[i] == 3){
                player.addSeeds(playerCells[i]); //on ajoute les graines récoltées au graine du joueur
                newPlayerCells[i] = 0 ; // la case devient vide
                player.setCells(newPlayerCells); //update des cases du joueur
            }
            else { // si la case ne contient ni 2 ni 3 graines on arrête la récolte
                break;
            }
        }
    }

    /**
     * Cette méthode met dans chaque case nbSeeds graines
     */
    private void seedDistribution(){
        int[] tab = new int[nbCells];
        for( int i = 0 ; i < nbCells ; i++){
            tab[i] = nbSeeds;
        }
        player.setCells(tab);
        computer.setCells(tab);
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
     * cette méthode cree les cellules des tables
     */
    private  void cellsGeneration()
    {
        this.cells = new int[nbCells * 2] ;
        for (int i = 0; i <= nbCells * 2  ; i++) {
            cells[i] = nbSeeds;

        }
    }


}

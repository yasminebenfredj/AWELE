public class PlayGame {
    final int nbCells;
    final int nbSeeds;
    final Player player ;
    final Player computer ;
    private int[] cells ;


    public static void main(String[] args) {
        PlayGame playGame = new PlayGame(12,4);
        playGame.play();
    }

    PlayGame(int nbCells , int nbSeeds){
        this.nbCells = nbCells;
        this.nbSeeds = nbSeeds;
        this.computer = new Player(nbCells,0,1);
        this.player =  new Player(nbCells,0,2);

        this.cells = new int[nbCells * 2] ;
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

        collectSeeds(playerC , choic);
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
            }
            else { // si la case ne contient ni 2 ni 3 graines on arrête la récolte
                break;
            }
        }
    }

    private  void seedDistribution(){
        int[] tab = new int[nbCells];
        for( int i = 0 ; i < nbCells ; i++){
            tab[i] = nbSeeds;
        }
        player.setCells(tab);
        computer.setCells(tab);
    }

    /**
     * Cette méthode merge les cases des 2 joueurs pour passer d'un plateau à 12 cases à un plateau à 6 cases
     */
    private void changePlateau(){
        mergeCells(player);

        mergeCells(computer);
    }
    /**
     * Cette méthode merge les cases d'un joueur
     * @param player
     */
    private void mergeCells(Player player) {
        int[] newPlayerCells = new int[6] ;
        int[] playerCells = player.getCells() ;
        for (int i = 0; i < playerCells.length - 1; i+=2 ) {
            newPlayerCells[i] = playerCells[i] + playerCells[i + 1] ;
        }
        player.setCells(newPlayerCells);
    }
}

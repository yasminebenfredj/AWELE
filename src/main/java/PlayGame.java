public class PlayGame {
    final int nbCells = 12;
    final int nbSeeds = 4;
    final Player player ;
    final Player computer ;
    private int[] cells ;


    PlayGame(Player player , Player playerComputer ){
        this.computer = playerComputer ;
        this.player = player ;

        this.cells = new int[nbCells * 2] ;
    }



    public void play(){
        seedDistribution();

        System.out.print("*** Debut du jeu ***");
        start(computer);

    }


    public void start(Player playerC) {
        int choice = playerC.chooseCell();

        int nbSeedsIn = playerC.getCells()[choice];
        for (int i = 0 ; i < nbSeedsIn ; i++) {
            if((choice+i) % 2 == 0) {
                playerC.setOneCell( choice - i);
            }
            else{
                computer.setOneCell(choice - i);
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


















}

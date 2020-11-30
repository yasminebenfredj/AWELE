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
        playTurn(computer);

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

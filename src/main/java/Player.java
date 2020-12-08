import Intelligence.*;

public class Player {
    private Intelligence intelligence;
    private int seeds; // seeds taken by the player
    private int[] indexes ;
    private boolean isComputer;
    private int nbCells;


    public Player(int nbCellsPlayer, int seeds, boolean isComputer){
        this.seeds = seeds ;
        this.isComputer = isComputer;
        this.nbCells = nbCellsPlayer;
        this.initIndexes(nbCells);
        this.initIntelligence();

    }

    // Getters ...
    public int getSeeds() {
        return this.seeds;
    }

    public boolean isComputer() {
        return this.isComputer;
    }

    public void addSeeds(int seeds){
        this.seeds += seeds ;
    }

    /**
     * Permet au joueur de choisir une case à jouer parmi les cienne
     * @return le choix du joueur
     */
    public int chooseCell(){
        int choice = this.intelligence.chooseCell() ;

        while (( choice  > this.nbCells*2 || choice < 1 ||(choice % 2) == 0 )&& !isComputer) {
            System.out.println("Vous n'avez pas accés à cette case, recommencez ...");
            choice = this.intelligence.chooseCell() ;
        }
        return  choice;
    }


    public void initIndexes(int nbCells){
        this.indexes = new int[nbCells];
        for (int i = 0; i < nbCells; i++) {
            if (isComputer) {
                indexes[i] = (i * 2);
            }
            else
            {
                indexes[i] = (i * 2) + 1;
            }
        }
    }


    private void initIntelligence() {
        if(isComputer) {
            this.intelligence = new ComputerStrategy(nbCells, indexes);
        }
        else {
            this.intelligence = new PlayerStrategy(nbCells, indexes);
        }
    }

    /**
     * Cette methode va permettre au joueur d'avoir le nouveau plateau apres avoir merger
     * @param newNbCells nouvelle taille du tableau
     */
    public void mergeCells(int newNbCells) {
        this.nbCells = newNbCells;
        initIndexes(newNbCells);
        intelligence.setIndexes(this.indexes);
        intelligence.setNbCells(newNbCells);

    }
    @Override
    public String toString(){
        return intelligence.toString();
    }

}
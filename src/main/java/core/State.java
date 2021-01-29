package core;

public class State {
    private int nbCells;
    private final int nbSeeds;
    private Player player ;
    private Player computer ;
    private int[] cells ;
    private int nbSeedsInGame;
    private final int totalNbSeed ;
    private boolean isMerged ;
    private int playerNumber;

    public State(int nbCells , int nbSeeds){
        this.nbCells = nbCells;
        this.nbSeeds = nbSeeds;
        this.playerNumber = 0;
        this.isMerged = false;
        this.totalNbSeed = this.nbSeeds * this.nbCells * 2 ;
        this.computer = new Player(1,nbCells,0,true);
        this.player =  new Player(2,nbCells,0,false);
        this.nbSeedsInGame = this.nbSeeds * this.nbCells * 2;
        cellsGeneration();
    }


    public Player getMe()
    {
        if(playerNumber == computer.getPlayerNumber()) {
            return computer;
        }
        else
        {
            return player;
        }
    }
    public Player getOtherPlayer()
    {
        if(playerNumber != computer.getPlayerNumber()) {
            return computer;
        }
        else
        {
            return player;
        }
    }

    /**
     * Cette méthode initialise les cases et met nbSeeds graines dans chaque case
     */
    private  void cellsGeneration() {
        this.cells = new int[this.nbCells * 2] ;
        for (int i = 0; i < this.nbCells * 2  ; i++) {
            this.cells[i] = this.nbSeeds;
        }
    }

    @Override
    public State clone()
    {
        State clone =  new State(nbCells,nbSeeds);
        clone.playerNumber = this.playerNumber;
        clone.isMerged =  this.isMerged ;
        clone.nbCells = this.nbCells ;
        clone.computer  = this.computer.clone();
        clone.player = this.player.clone();
        clone.nbSeedsInGame = this.nbSeedsInGame;
        clone.cells = cells.clone();
        return clone;
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
        boolean currentSeedsInCells = nbSeedsInGame < 8 ;
        return currentSeedsInCells ;
    }

    /**
     * This method computes the number of seeds in the cells belonging to a given player
     * Cette méthode calcule le nombre de graines dans les cases d'un joueur
     * @param player un joueur
     * @return le nombre de graines dans les cases du joueur
     */
    public int seedsPlayerCells(Player player) {
        int seedsInPlayerCells = 0 ;
        for (int i = 0; i < player.getMyIndexes().length; i++) {
            seedsInPlayerCells += cells[player.getMyIndexes()[i]] ;
        }
        return seedsInPlayerCells ;
    }


    /**
     * Cette méthode merge les cases du jeu pour passer d'un plateau à 12 cases à un plateau à 6 cases
     * @param newNbCells le nouveau nombre de cellule
     */
    public void mergeCells(int newNbCells) {
        int[] newCells = new int[newNbCells*2] ;
        for (int i = 0; i < nbCells  ; i++ ) {
            newCells[i] = cells[i*2] + cells[i*2 + 1] ;
        }
        nbCells = newNbCells;
        cells = newCells;

        player.mergeCells(newNbCells);
        computer.mergeCells(newNbCells);

    }
    public boolean isMerged() {
        return isMerged;
    }

    public void setIsMerged() {
        isMerged = true;
    }

    public int[] getCells() {
        return cells;
    }


    public void setCells(int[] cells) {
        this.cells = cells;
    }

    public int getNbCells() {
        return nbCells;
    }


    public int getNbSeeds() {
        return nbSeeds;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }


    public int getNbSeedsInGame() {
        return nbSeedsInGame;
    }

    public void setNbSeedsInGame(int nbSeedsInGame) {
        this.nbSeedsInGame = nbSeedsInGame;
    }

    public int getTotalNbSeed() {
        return totalNbSeed;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

}

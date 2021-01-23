package core;

import Intelligence.*;

public class Player {
    private Intelligence intelligence;
    private int seeds; // seeds taken by the player
    private int[] myIndexes;
    private int[] otherIndexes;

    private boolean isComputer;
    private int nbCells;
    private int playerNumber; // 1 ou 2

    public Player(int playerNumber , int nbCellsPlayer, int seeds, boolean isComputer){
        this.playerNumber = playerNumber;
        this.seeds = seeds ;
        this.isComputer = isComputer;
        this.nbCells = nbCellsPlayer;
        this.initIndexes(nbCells);
        this.initIntelligence();

    }
    public Player(){

    }

    /**
     * Cette methode va permettre d'initialiser l'inteligence que va adapter le joueur:
     *
     * RandomStrategy : est une inteligence qui donne des reponse au hasard
     * PlayerStrategy : est un joueur qui va tapper les reponse en ligne de commande
     * MiniMaxStrategy : est une inteligence qui va utiliser l'algorithme MinMax pour repondre
     * ComputerStrategy : est une autre inteligence qui va utiliser un algorithme de MinMax/AlphaBeta
     */
    private void initIntelligence() {
        if(isComputer) {
            this.intelligence =  new MiniMaxStrategy(nbCells, myIndexes, otherIndexes);
        }
        else {
            this.intelligence = new RandomStrategy(nbCells, myIndexes, otherIndexes);
        }
    }

    /**
     * Permet au joueur de choisir une case à jouer parmi les cienne
     * @return le choix du joueur
     */
    public int chooseCell(State state){
        int choice = this.intelligence.chooseCell(state) ;

        while (( choice  > this.nbCells*2 || choice < 1 ||(choice % 2) == 0 )&& !isComputer) {
            System.out.println("Vous n'avez pas accès à cette case, recommencez ...");
            choice = this.intelligence.chooseCell(state) ;
        }
        return  choice;
    }

    /**
     * Cette methode va permettre de cree le tableau d'indexe des case que posséde un le joueur
     * Si joueur 1 : case paire 0/2/4/6...
     * Si joueur 2 : case impaire 1/3/5...
     * @param nbCells : nombre de cellule dans le tableau
     *
     */
    public void initIndexes(int nbCells){
        this.myIndexes = new int[nbCells];
        this.otherIndexes = new int[nbCells];
        for (int i = 0; i < nbCells; i++) {
            if (isComputer) {
                myIndexes[i] = (i * 2);
                otherIndexes[i] =  (i * 2) + 1;
            }
            else {
                myIndexes[i] = (i * 2) + 1;
                otherIndexes[i]= (i * 2);
            }
        }
    }

    /**
     * Cette methode va permettre au joueur d'avoir le nouveau plateau apres avoir merger
     * @param newNbCells nouvelle taille du tableau
     */
    public void mergeCells(int newNbCells) {
        this.nbCells = newNbCells;
        initIndexes(newNbCells);
        intelligence.setIndexes(this.myIndexes);
        intelligence.setOtherIndexes(this.otherIndexes);
        intelligence.setNbCells(newNbCells);

    }

    // Getters ...
    public int getSeeds() {
        return this.seeds;
    }

    public int getPlayerNumber() {
        return this.playerNumber;
    }

    public boolean isComputer() {
        return this.isComputer;
    }

    public void addSeeds(int seeds){
        this.seeds += seeds ;
    }

    public int[] getMyIndexes() {
        return myIndexes;
    }

    @Override
    public String toString(){
        return intelligence.toString();
    }

    @Override
    public Player clone()
    {
        Player player = new Player();
        player.playerNumber = this.playerNumber ;
        player.seeds = this.seeds  ;
        player.isComputer = this.isComputer ;
        player.nbCells = this.nbCells ;
        player.myIndexes = this.myIndexes;
        player.intelligence = this.intelligence;
        return player;
    }

}
package Intelligence;

import core.GameEngine;
import core.Position;

import java.util.ArrayList;

public class MiniMaxStrategy extends Intelligence{
    private Position currentPosition;
    private boolean isMerged ;

    public MiniMaxStrategy(int nbCellsPlayer, int[] indexes) {
        super(nbCellsPlayer, indexes);
        this.isMerged = false;
    }

    public int[] fullIndexes(Position position){//only indexes that are not empty
        ArrayList<Integer> fullIndexes = new ArrayList<Integer>();
        for (int i = 0; i < super.getIndexes().length; i++) {
            if (position.getCells()[super.getIndexes()[i]] != 0){
                fullIndexes.add(super.getIndexes()[i]);
            }
        }
        int[] indexes = fullIndexes.stream().mapToInt(i ->i).toArray();
        //super.setIndexes(indexes);
        return indexes;
    }

    private ArrayList<Position> childPositions(Position position){
        ArrayList<Position> childPositions = new ArrayList<>();
        int[] cells = position.getCells().clone();//shallow copy
        //int[] indexes = getIndexes().clone();//shallow copy
        int[] indexes = fullIndexes(position);
        int [] newCells ;
        for (int i = 0; i < indexes.length; i++){ //was indexes.length
            if (cells[indexes[i]] !=0){//the cell is not empty
                newCells = simulateTurn(indexes[i] , position.getCells().clone());
                //position.getGame().playTurn(position.getPlayerComputer());
                Position newPosition = new Position(position.getGame(),position.getComputer(),position.getPlayer(),newCells);//@TODO not sure
                childPositions.add(newPosition);
                cells = position.getCells().clone();//shallow copy
            }
        }
        //super.setIndexes();
        return childPositions;
    }

    private int[] simulateTurn(int choice,int[] cells){
        int nbSeedsIn = cells[choice]; // nombre de graines dans la case choisi
        cells[choice] = 0 ; // on prend tous les graines de la case choisi pour jouer un tour

        int initialPosition = choice;
        int position = GameEngine.nextPosition(choice, super.getNbCells(),super.getNbCells()*2+1) ;//this.nbCells*2+1 :n'importe quel nombre  > nbCells*2 marche
        for (int i = 1  ; i <= nbSeedsIn ; i++ ) {

            cells[position] += 1 ;
            choice = position;
            position = GameEngine.nextPosition(position, super.getNbCells(),initialPosition);
        }
        return this.simulateCollectSeeds(cells,choice); //on collecte les graines à partir de la dernière case semer (qui est choice)
    }

    private int[] simulateCollectSeeds(int[] cells,int currentIndex){
        while (cells[currentIndex] == 2 || cells[currentIndex] == 3 ) {
            int gains = cells[currentIndex];

            //@TODO player.addSeeds(gains); //on ajoute les graines récoltées au graines du joueur
            //this.nbSeedsInGame -= gains; // on soustrait de la somme des graines présente dans le jeu
            cells[currentIndex] = 0; // la case devient vide
            currentIndex = GameEngine.precedentPosition(currentIndex, super.getNbCells());
        }
        //this.currentPosition = new Position(this, this.computer,this.player,cells);
        //player.setCurrentPosition(currentPosition);
        return cells;
    }

    private int evaluation(Position position){//@TODO Améliorer
        int nbSeedsComputer = position.getComputer().getSeeds();
        int nbSeedsPlayer = position.getPlayer().getSeeds();
        return nbSeedsComputer - nbSeedsPlayer;
    }

    private int evaluation2(Position position){//@TODO Améliorer
        int nbSeedsComputer = position.getComputer().getSeeds();
        int nbSeedsPlayer = position.getPlayer().getSeeds();
        int seedsDifference = nbSeedsComputer - nbSeedsPlayer;
        if (seedsDifference > 0){
            return 1;
        }
        else if (seedsDifference < 0){
            return -1;
        }
        else {
            return 0;
        }
    }

    private int miniMax(Position position,int depth,double alpha,double beta,boolean maximizingPlayer) {
        if (position.getGame().isMerged() && !isMerged){//@TODO
            System.out.println("Mergedddddddd");
            position.setCells(position.getGame().getCells().clone());

            isMerged = true;
        }

        if (depth == 0 || position.isFinalPosition()){
            if (position.isFinalPosition()){
                int seedsDifference = position.getComputer().getSeeds() - position.getPlayer().getSeeds();
                if (seedsDifference > 0){//computer wins
                    return position.getGame().getNbCells() * 2 *  position.getGame().getNbSeeds();//96
                }
                else if (seedsDifference < 0){//player wins
                    return -position.getGame().getNbCells() * 2 *  position.getGame().getNbSeeds();//-96
                }
                else {//draw
                    return 0;
                }
            }
            else {//depth is zero
                return evaluation(position); //@TODO update currentPosition when needed in the coming code
            }
        }
        if (maximizingPlayer){
            double value = Double.NEGATIVE_INFINITY;
            int random  =  super.getRandom().nextInt(super.getNbCells());
            int index =  super.getIndexes()[random];
            for (int i = 0; i < super.getIndexes().length; i++) {
                //if (position.getCells()[super.getIndexes()[i]] != 0){ // >0
                if (position.getGame().getCells()[super.getIndexes()[i]] != 0){ // >0
                    int[] newCells = simulateTurn(super.getIndexes()[i],position.getCells().clone());
                    //position = new Position(position.getGame(),position.getPlayerComputer(), position.getPlayer(),newCells);
                    position.setCells(newCells);
                    double newScore = miniMax(position,depth - 1,alpha,beta,false);
                    if (newScore > value){
                        value = newScore;
                        index = super.getIndexes()[i];
                    }
                    alpha = Math.max(alpha , value);//pruning
                    if (alpha >= beta){
                        break;
                    }
                }
            }
            return index;
        }
        else {
            double value = Double.POSITIVE_INFINITY;
            int random  =  super.getRandom().nextInt(super.getNbCells());
            int index =  super.getIndexes()[random];
            for (int i = 0; i < super.getIndexes().length; i++) {
                //if (position.getCells()[super.getIndexes()[i]] != 0){ // >0
                if (position.getGame().getCells()[super.getIndexes()[i]] != 0){ // >0
                    int[] newCells = simulateTurn(super.getIndexes()[i],position.getCells().clone());
                    //position = new Position(position.getGame(),position.getPlayerComputer(), position.getPlayer(),newCells);
                    position.setCells(newCells);
                    double newScore = miniMax(position,depth - 1,alpha,beta,true);
                    if (newScore < value){
                        value = newScore;
                        index = super.getIndexes()[i];
                    }
                    beta = Math.min(beta , value);
                    if (alpha >= beta){
                        break;
                    }
                }
            }
            return index;
        }
    }
    @Override
    public int chooseCell(int[] cells) {//@TODO depth 1 ça marche meme avec 1000 partie so ?
        int miniMax = miniMax(currentPosition , 4,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,true);
        return miniMax;
    }

    @Override
    public void setCurrentPosition(Position position) {
        this.currentPosition = position;
    }

    @Override
    public String toString() {
        return " MiniMax Strategy ";
    }
}
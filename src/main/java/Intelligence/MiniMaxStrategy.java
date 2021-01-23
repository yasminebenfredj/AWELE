package Intelligence;

import core.GameEngine;
import core.State;

import java.util.ArrayList;

public class MiniMaxStrategy extends Intelligence{
    private State currentState;
    private boolean isMerged ;

    public MiniMaxStrategy(int nbCellsPlayer, int[] indexes,  int[] otherIndexes) {
        super(nbCellsPlayer, indexes, otherIndexes);
        this.isMerged = false;
    }

    public int[] fullIndexes(State state){//only indexes that are not empty
        ArrayList<Integer> fullIndexes = new ArrayList<Integer>();
        for (int i = 0; i < super.getIndexes().length; i++) {
            if (state.getGame().getCells()[super.getIndexes()[i]] != 0){
                fullIndexes.add(super.getIndexes()[i]);
            }
        }
        int[] indexes = fullIndexes.stream().mapToInt(i ->i).toArray();
        //super.setIndexes(indexes);
        return indexes;
    }

    private ArrayList<State> childPositions(State state){
        ArrayList<State> childStates = new ArrayList<>();
        int[] cells = state.getGame().getCells().clone();//shallow copy
        //int[] indexes = getIndexes().clone();//shallow copy
        int[] indexes = fullIndexes(state);
        int [] newCells ;
        for (int i = 0; i < indexes.length; i++){ //was indexes.length
            if (cells[indexes[i]] !=0){//the cell is not empty
                newCells = simulateTurn(indexes[i] , state.getGame().getCells().clone());
                //position.getGame().playTurn(position.getPlayerComputer());
                State newState = new State(0,state.getGame());//@TODO not sure
                childStates.add(newState);
                cells = state.getGame().getCells().clone();//shallow copy
            }
        }
        //super.setIndexes();
        return childStates;
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

    private int evaluation(State state){//@TODO Améliorer
        int nbSeedsComputer = state.getMe().getSeeds();
        int nbSeedsPlayer = state.getOtherPlayer().getSeeds();
        return nbSeedsComputer - nbSeedsPlayer;
    }

    private int evaluation2(State state){//@TODO Améliorer
        int nbSeedsComputer = state.getGame().computer.getSeeds();
        int nbSeedsPlayer = state.getGame().player.getSeeds();
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

    private int miniMax(State state, int depth, double alpha, double beta, boolean maximizingPlayer) {
        if (state.getGame().isMerged() && !isMerged){//@TODO
            System.out.println("Mergedddddddd");
            state.getGame().setCells(state.getGame().getCells().clone());

            isMerged = true;
        }

        if (depth == 0 || state.getGame().endOfGame()){
            if (state.getGame().endOfGame()){
                int seedsDifference = state.getGame().computer.getSeeds() - state.getGame().player.getSeeds();
                if (seedsDifference > 0){//computer wins
                    return state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();//96
                }
                else if (seedsDifference < 0){//player wins
                    return -state.getGame().getNbCells() * 2 *  state.getGame().getNbSeeds();//-96
                }
                else {//draw
                    return 0;
                }
            }
            else {//depth is zero
                return evaluation(state); //@TODO update currentPosition when needed in the coming code
            }
        }
        if (maximizingPlayer){
            double value = Double.NEGATIVE_INFINITY;
            int random  =  super.getRandom().nextInt(super.getNbCells());
            int index =  super.getIndexes()[random];
            for (int i = 0; i < super.getIndexes().length; i++) {
                //if (position.getCells()[super.getIndexes()[i]] != 0){ // >0
                if (state.getGame().getCells()[super.getIndexes()[i]] != 0){ // >0
                    int[] newCells = simulateTurn(super.getIndexes()[i], state.getGame().getCells().clone());
                    //position = new Position(position.getGame(),position.getPlayerComputer(), position.getPlayer(),newCells);
                    State newstate = state.clone();
                    newstate.getGame().setCells(newCells);
                    double newScore = miniMax(newstate,depth - 1,alpha,beta,false);
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
                if (state.getGame().getCells()[super.getIndexes()[i]] != 0){ // >0
                    int[] newCells = simulateTurn(super.getIndexes()[i], state.getGame().getCells().clone());
                    //position = new Position(position.getGame(),position.getPlayerComputer(), position.getPlayer(),newCells);
                    State newstate = state.clone();
                    newstate.getGame().setCells(newCells);
                    double newScore = miniMax(newstate,depth - 1,alpha,beta,true);
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
    public int chooseCell(State state) {//@TODO depth 1 ça marche meme avec 1000 partie so ?
        this.currentState = state;
        int miniMax = miniMax(currentState.clone(), 15,Double.NEGATIVE_INFINITY,Double.POSITIVE_INFINITY,true);
        return miniMax;
    }


    @Override
    public String toString() {
        return " MiniMax Strategy ";
    }
}
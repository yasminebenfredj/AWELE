import Intelligence.*;

public class Player {
    private Intelligence intelligence;
    private int seeds; // seeds taken by the player
    private int[] indexes ;
    private boolean isComputer;
    private int nbCellsPlayer;


    public Player(int nbCellsPlayer, int seeds, boolean isComputer){
        this.seeds = seeds ;
        this.isComputer = isComputer;
        this.indexes = new int[nbCellsPlayer];
        this.nbCellsPlayer = nbCellsPlayer;
        this.initIndexes();
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


    public int chooseCell(){
        return  this.intelligence.chooseCell() ;
    }


    private void initIndexes(){
        for (int i = 0; i < this.nbCellsPlayer  ; i++) {
            if (isComputer) {
                indexes[i] = (i * 2);
            }
            else
            {
                indexes[i] = (i * 2) + 1;
            }
        }
    }


    private void initIntelligence()
    {
        if(isComputer) {
            this.intelligence = new ComputerStrategy(nbCellsPlayer, indexes);
        }
        else {
            this.intelligence = new PlayerStrategy(nbCellsPlayer, indexes);
        }
    }

    @Override
    public String toString(){
        return intelligence.toString();
    }


}

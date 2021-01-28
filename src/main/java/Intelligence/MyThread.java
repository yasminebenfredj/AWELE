package Intelligence;

import core.State;

import core.GameEngine;
import core.Player;
import core.State;

import java.util.ArrayList;
import java.util.Random;

public class MyThread extends  Thread{
    int MAX= 1000;
    int MIN = -1000;
    Thread unThread ;
    int alpha ;
    int beta;
    int depth;
    core.State state ;
    boolean max;
    private int currentDepth =9 ;

    private int[] indexes;
    private int[] otherIndexes;

    public MyThread(){
        unThread = new Thread(this, "new thread");

    }

    public void setInformation(int depth,int alpha, int beta, boolean max,
                               core.State state, int[] indexes, int[] otherIndexes)
    {

        this.alpha = alpha;
        this.beta = beta;
        this.depth = depth;
        this.state = state;
        this.max = max;
        this.indexes = indexes;
        this.otherIndexes = otherIndexes;

    }

    @Override
    public void run() {

    }
}

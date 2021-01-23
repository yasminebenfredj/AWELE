package core;

import java.util.ArrayList;

public class State {
    private GameEngine game;
    public int gain  = 0;
    public int depth = 1;
    public  int node = 0 ;
    public int choices = 0;

    public State(GameEngine game ){
        this.game = game;
    }

    public boolean isFinalPosition(){
        return this.game.endOfGame();
    }
    public void setGame(GameEngine game) {
        this.game = game;
    }

    public GameEngine getGame() {
        return game;
    }


    @Override
    public State clone()
    {
        State state =  new State(this.game.clone());
        state.depth = depth;
        state.gain = gain;
        state.choices = choices;
        state.node = node;
        return state;
    }
}

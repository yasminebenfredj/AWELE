package core;

public class State {
    private GameEngine game;
    public int gain  = 0;
    public int depth= 1;

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
        return state;
    }
}

package core;

public class State {
    private GameEngine game;
    private int playerNumber;

    public State(int numberPlayer,GameEngine game ){
        this.game = game;
        this.playerNumber = numberPlayer;

    }

    public GameEngine getGame() {
        return game;
    }

    public Player getMe()
    {
        if(playerNumber == game.computer.getPlayerNumber()) {
            return game.computer;
        }
        else
        {
            return game.player;
        }
    }
    public Player getOtherPlayer()
    {
        if(playerNumber != game.computer.getPlayerNumber()) {
            return game.computer;
        }
        else
        {
            return game.player;
        }
    }


    @Override
    public State clone()
    {
        State state =  new State(playerNumber,this.game.clone());
        return state;
    }
}

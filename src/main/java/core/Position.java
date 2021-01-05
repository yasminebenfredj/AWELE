package core;

public class Position {
    private boolean computerPlay; // true if the computer has to play and false otherwise
    private Player player;
    private Player computer;
    private int[] cells;
    private GameEngine game;
    private int[] actions;
    public int id  = 0;
    public int depth= 1;

    public Position(GameEngine game, Player computer , Player player , int[] cells){
        this.game = game;
        this.computer = computer;
        this.player = player;
        this.cells = cells;
        this.actions = new int[game.getNbCells()];
    }


    public boolean isFinalPosition(){
        return this.game.endOfGame();
    }
    // Getters ...
    public Player getPlayer() {
        return this.player;
    }

    public void setGame(GameEngine game) {
        this.game = game;
    }

    public Player getComputer() {
        return this.computer;
    }

    public GameEngine getGame() {
        return game;
    }

    public int[] getCells() {
        return cells;
    }

    public void setCells(int[] cells) {
        this.cells = cells;
    }



    @Override
    public Position clone()
    {
        Position position =  new Position(this.game.clone(), this.computer.clone(), this.player.clone(), this.cells.clone());
        position.depth = depth;
        position.id = id;
        return position;
    }
}

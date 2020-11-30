public class Game {
    private Player player;
    private Player playerComputer;

    Game(Player player , Player playerComputer ){
        this.player = player ;
        this.playerComputer = playerComputer ;

    }

    public void play(){
        Player player = new Player(12 , 0) ;
        Player playerComputer = new Player(12 , 0) ;
        Game game = new Game(player , playerComputer) ;
    }

    
}

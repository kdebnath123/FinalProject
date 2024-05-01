public class Game {

    private GameView window;
    private Round round;

    public static final int BIG_BLIND = 100;
    public static final int FOLD = -1, CHECK_CALL = 0;

    Player[] players;
    Deck deck;

    // TODO: once each round is finished then move up to game

    public Game(){

        players = new Player[4];

        players[0] = new Player("B", 1000);
        players[1] = new Player("SB", 1000);
        players[2] = new Player("BB", 1000);
        players[3] = new Player("UTG", 1000);


        this.round = new Round(players);


        this.window = new GameView(this);
    }

    public void play(){
        //window.repaint();
        round.play();
    }


    public static void main(String[] args) {
        // Creates the viewer's window
        Game game = new Game();
        game.play();
    }

}

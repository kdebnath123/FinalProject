public class Game {

    private GameView window;
    private Round round;

    Player[] players;
    Deck deck;

    // TODO: once each round is finished then move up to game

    public Game(){
        this.deck = new Deck();

        players = new Player[2];

        players[0] = new Player("1", 1000);
        players[1] = new Player("2", 1000);


        this.round = new Round(players, deck);


        this.window = new GameView(this);
    }

    public void play(){
        window.repaint();
    }


    public static void main(String[] args) {
        // Creates the viewer's window
        Game game = new Game();
        game.play();


    }

}

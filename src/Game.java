import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {

    private GameView window;
    private Round round;

    public static final int BIG_BLIND = 100;
    public static final int FOLD = -1, CHECK_CALL = 0;

    private boolean actionIsOpen;

    private int state;


    public static final int[] PLAYER_X = {790, 600, 300, 100},
                              PLAYER_Y = {225, 365, 365, 225};
    public static final int NEW_ROUND = 0,

            DEAL = 1,
            PRE_FLOP = 2,
            FLOP = 3,
            TURN = 4,
            RIVER = 5,
            SHOWDOWN = 6;

    Player[] players;

    // TODO: once each round is finished then move up to game

    public Game(){

        players = new Player[4];

        players[0] = new Player("B", 1000, PLAYER_X[0], PLAYER_Y[0]);
        players[1] = new Player("SB", 1000, PLAYER_X[1], PLAYER_Y[1]);
        players[2] = new Player("BB", 1000, PLAYER_X[2], PLAYER_Y[2]);
        players[3] = new Player("UTG", 1000, PLAYER_X[3], PLAYER_Y[3]);


        state = NEW_ROUND;
        actionIsOpen = false;

        this.round = new Round(players);

        this.window = new GameView(this, round);
        window.addKeyListener(this);

        round.addWindow(window);

    }

    public Player[] getPlayers() {
        return players;
    }

    public static void main(String[] args) {
        // Creates the viewer's window
        Game game = new Game();


    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if(!actionIsOpen) {

            switch (e.getKeyCode()) {

                case KeyEvent.VK_N:
                    setState(DEAL);
                    break;
                case KeyEvent.VK_ENTER:
                    setState(getState() + 1);
                    break;
            }

        }
        else{




        }


    }

    public int getState() {
        return state;
    }

    public void setState(int gameState) {

        this.state = gameState;
        switch (gameState){
            case DEAL:
                round.reset();
                round.deal();
                break;

            case PRE_FLOP:
                round.preFlop();
                break;
            case FLOP:
                round.flop();
                break;
            case TURN:
                round.turn();
                 break;
            case RIVER:
                round.river();
                break
            case SHOWDOWN:
                round.showdown();


        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

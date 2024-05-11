import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/** Top level class that controls all **/
public class Game implements KeyListener {

    /** Shared data **/
    private GameView window;
    private Round round;


    /** Game data **/
    public static final int BIG_BLIND = 100;

    /** Player data **/
    private Player[] players;
    public static final int[] PLAYER_X = {790, 600, 300, 100},
            PLAYER_Y = {225, 365, 365, 225},

    BUTTON_X = {760, 570, 270, 210};
    private int playersToAct, callAmount, activeSeat;
    private Player activePlayer;


    /** State data **/
    private int state;
    public static final int NEW_ROUND = 0,

            DEAL = 1,
            PRE_FLOP = 2,
            FLOP = 3,
            TURN = 4,
            RIVER = 5,
            SHOWDOWN = 6,
            STEAL = 7;


    /** Constructor **/
    public Game(){

        // Creates players
         players = new Player[4];
        players[0] = new Player("D", 1000, PLAYER_X[0], PLAYER_Y[0], BUTTON_X[0]);
        players[1] = new Player("A", 1000, PLAYER_X[1], PLAYER_Y[1], BUTTON_X[1]);
        players[2] = new Player("B", 1000, PLAYER_X[2], PLAYER_Y[2], BUTTON_X[2]);
        players[3] = new Player("C", 1000, PLAYER_X[3], PLAYER_Y[3], BUTTON_X[3]);


        state = NEW_ROUND;
        this.round = new Round(players);

        // link front and back end
        this.window = new GameView(this, round);
        window.addKeyListener(this);

    }

    /** Main method **/
    public static void main(String[] args) {
        // Creates the game
        Game game = new Game();
    }



    /** Controls Keyboard input which controls the main game loop **/
    @Override
    public void keyPressed(KeyEvent e) {

        // depending on game state allow different actions
        switch (state){
            case NEW_ROUND:
                if(e.getKeyCode() == KeyEvent.VK_N){
                    this.setState(DEAL);
                }
                break;

            case DEAL:
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    this.setState(PRE_FLOP);
                }
                break;

            case PRE_FLOP:
            case FLOP:
            case TURN:
            case RIVER:
                // Find active player and process valid actions
                activePlayer = round.getActivePlayers().get(activeSeat);
                activePlayer.setTheAction(true);
                window.repaint();

                switch (e.getKeyCode()){
                    case KeyEvent.VK_F:
                    case KeyEvent.VK_C:
                    case KeyEvent.VK_R:
                        action(e.getKeyChar() + "");
                }
                break;
            case SHOWDOWN:
            case STEAL:
                this.setState(NEW_ROUND);
                break;
        }


    }

    /** Processes valid action of the active player **/
    private void action(String act) {
        switch (act) {

            // If a player folds they are removed from the hand
            case "f":
                System.out.println(activePlayer.getName() + " Folded");
                round.getActivePlayers().remove(activeSeat);
                break;


                // If they check/call the action is calculated, needed money bet, and seat moved forward
            case "c":

                // Action type calculated based on pot investment
                if (callAmount == activePlayer.getPotInvestment()){
                    activePlayer.setAction("Check");
                }
                else {
                    activePlayer.setAction("Call for $ " + (callAmount - activePlayer.getPotInvestment()));
                }
                // Money bet
                round.increasePot(activePlayer.bet(callAmount - activePlayer.getPotInvestment()));
                // action given to next player
                activeSeat++;
                break;

                // If they raise, the amount to stay in is raised by 100
                case "r":
                    int raiseAmount = BIG_BLIND;
                    activePlayer.setAction("Raises $" + 100);

                    round.increasePot(activePlayer.bet(raiseAmount + callAmount -
                            activePlayer.getPotInvestment()));

                    // Raising requires all players to be re-visited, akin to starting a new street
                    startStreet(callAmount + raiseAmount, (activeSeat + 1) %
                            round.getActivePlayers().size());
                    break;
        }

        // Take the action away from player
        activePlayer.setTheAction(false);
        // Attempt to end street
        endStreet();
        // Update front-end
        window.repaint();
    }

    /** Controls the game flow and determines what should be done on each street**/
    public void setState(int gameState) {

        this.state = gameState;

        // Depending on street, round is used to control the needed steps
        switch (gameState){
            case DEAL:
                round.reset();
                round.deal();
                break;

            case PRE_FLOP:
                startStreet(BIG_BLIND, Round.UTG);
                break;
            case FLOP:
                round.flop();
                startStreet(0, Round.SB);
                break;
            case TURN:
                round.turn();
                startStreet(0, Round.SB);
                 break;
            case RIVER:
                round.river();
                startStreet(0, Round.SB);
                break;
            case SHOWDOWN:
                round.showdown();
                break;
            case STEAL:
                round.steal();
                break;
        }
        window.repaint();
    }

    /** Opens the action for the table **/
    public void startStreet(int callAmount, int activeSeat){

        // Calculates how many players need to have the action, and allows the first player to get the action
        playersToAct = round.getActivePlayers().size() - 1;
        this.callAmount = callAmount;
        this.activeSeat = activeSeat;
    }

    /** Closes the action for the table or if not finished moves it to the next player **/
    public void endStreet(){

        // Checks if all others have folded
        if(round.getActivePlayers().size() == 1){
            setState(STEAL);
            return;
        }

        // If all players have acted, move to next street
        if(playersToAct == 0){
            round.resetPlayerInvestment();
            round.resetPlayerAction();
            setState(getState() + 1);
        }
        else{
            // Move to next player
            activeSeat = activeSeat % round.getActivePlayers().size();
            playersToAct--;
        }
    }

    /** Getter **/
    public int getState() {
        return state;
    }


    /** unused keyboard methods **/
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

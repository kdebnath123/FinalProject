import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game implements KeyListener {

    private GameView window;
    private Round round;

    public static final int BIG_BLIND = 100;
    public static final int FOLD = -1, CHECK_CALL = 0;

    private int playersToAct, callAmount, startSeat, activeSeat;
    private Player activePlayer;

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

        players[0] = new Player("D", 1000, PLAYER_X[0], PLAYER_Y[0]);
        players[1] = new Player("A", 1000, PLAYER_X[1], PLAYER_Y[1]);
        players[2] = new Player("B", 1000, PLAYER_X[2], PLAYER_Y[2]);
        players[3] = new Player("C", 1000, PLAYER_X[3], PLAYER_Y[3]);


        state = NEW_ROUND;

        this.round = new Round(players);

        this.window = new GameView(this, round);
        window.addKeyListener(this);

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
        //System.out.println(e.getKeyChar() + "   " + state);


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
                if(e.getKeyCode() == KeyEvent.VK_N){
                    this.setState(NEW_ROUND);
                }
                break;
        }


    }

    //Does an action
    private void action(String act) {
        switch (act) {

            case "f":
                System.out.println(activePlayer.getName() + " Folded");
                round.getActivePlayers().remove(activeSeat);
                break;

                case "c":

                    if (callAmount == activePlayer.getPotInvestment()){
                        System.out.println(activePlayer.getName() + " Checks");
                    }
                    else {
                        System.out.println(activePlayer.getName() + " Calls for $" + (callAmount - activePlayer.getPotInvestment()));
                    }
                    round.increasePot(activePlayer.bet(callAmount - activePlayer.getPotInvestment()));
                    activeSeat++;
                    break;



                case "r":
                    int raiseAmount = 100;
                    /*
                    if(callAmount + Game.BIG_BLIND > bank){
                        System.out.println("Not enough $ to raise, auto-calling");
                    }

                    // Forces user to input a number between Call Amount + BB and bankroll
                    while (true) {
                        // Try/Catch learned from Stack Overflow
                        try {
                            System.out.print("Raise a valid amount (min raise BB): ");
                            raiseAmount = input.nextInt();

                            if (raiseAmount >= Game.BIG_BLIND && raiseAmount <= bank - callAmount) {
                                input.nextLine();
                                break;
                            }
                        }
                        catch (InputMismatchException e) {
                            input.next();
                        }
                    }

                     */

                    round.increasePot(activePlayer.bet(raiseAmount + callAmount - activePlayer.getPotInvestment()));
                    break;
        }

        activePlayer.setTheAction(false);
        endStreet();
        window.repaint();
    }

    public int getState() {
        return state;
    }

    public void setState(int gameState) {

        System.out.println("Moving to " + gameState);

        this.state = gameState;
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
        }
        window.repaint();
    }


    public void startStreet(int callAmount, int activeSeat){
        // Starting with UTG give each player the action, allow each player to see their cards
        playersToAct = round.getActivePlayers().size() - 1;
        this.callAmount = callAmount;
        this.activeSeat = activeSeat;
    }

    // Called after player is done moving
    public boolean endStreet(){
        if(playersToAct == 0){
            System.out.println("Street pot: " + round.getPot());
            round.resetPlayerInvestment();
            setState(getState() + 1);
            return true;
        }
        else{
            activeSeat = activeSeat % round.getActivePlayers().size();
            playersToAct--;
        }
        return false;
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

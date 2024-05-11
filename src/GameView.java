import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GameView extends JFrame{


    /** Window attributes **/
    public static final int WINDOW_HEIGHT= 540, WINDOW_WIDTH = 960,
            DECK_X = 300, CARDS_Y = 233,
            FIRST_CARD_X = 353, BUFFER = 5;

    public static final String TITLE = "Poker";

    /** Background image **/
    private Image background, cardBack;

    /** Shared data **/
    private Game game;
    private Round round;


    /** Constructor **/
    public GameView(Game game, Round round) {

        // Initialize instance variables.
        this.game = game;
        this.round = round;
        this.background = new ImageIcon("Resources/Poker BG.png").getImage();
        this.cardBack = new ImageIcon("Resources/Cards/back.png").getImage();

        // Set up the window and the buffer strategy.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

    }

    /** Main paint method**/
    public void paint(Graphics g) {


        g.setColor(Color.white);

        // Paint the new game screen or standard board depending on state
        switch (game.getState()) {

            case Game.NEW_ROUND:
                paintNewGameScreen(g);
                break;
            default:
                paintBoard(g);
                break;
        }
    }

    /** New Game paint method**/
    private void paintNewGameScreen(Graphics g) {

        //Draws background
        g.drawImage(background, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, this);

        // Draws 'new round' in a box
        paintStringInBox(g, "[N]ew Round", (WINDOW_WIDTH - g.getFontMetrics().stringWidth("[N]ew Round")) / 2,
                (int)(WINDOW_HEIGHT * .75), BUFFER * 4);
    }

    /** Paints the table for any state of active game **/
    public void paintBoard(Graphics g){

        //Paint background
        g.setColor(Color.WHITE);
        g.drawImage(background, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, this);

        // Show the pot amount
        paintStringInBox(g, "Pot: " + round.getPot(), (WINDOW_WIDTH - g.getFontMetrics().stringWidth("Pot: " + round.getPot())) / 2,
                CARDS_Y - 60, BUFFER * 2);


        // Draws each active player in the hand
        ArrayList<Player> activePlayers = round.getActivePlayers();
        for (Player p: activePlayers) {
            p.drawPlayer(g, this);
        }
        paintButton(g);


        //Paint Deck
        Card.drawFaceDown(g, DECK_X, CARDS_Y, this);


        ArrayList<Card> community = round.getCommunity();

        // Draw burn pile
        if(!community.isEmpty()){
            Card.drawFaceDown(g, DECK_X + (6 * (BUFFER + Card.WIDTH)), CARDS_Y, this);
        }

        // Draw the community cards
        for (int i = 0; i < community.size(); i++){
            community.get(i).drawCard(g, FIRST_CARD_X + (i * (BUFFER + Card.WIDTH)), CARDS_Y, this);

        }
    }

    /** Helper method which paints a String with a box around it **/
    public static void paintStringInBox(Graphics g, String toPrint, int x, int y, int bufferAmt){

        // Gets the string's height/width
        int str_length = g.getFontMetrics().stringWidth(toPrint);
        int str_height = g.getFontMetrics().getHeight();

        // Calculates the needed shifts to draw a centered box around the string
        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(x - bufferAmt, y - str_height, str_length + bufferAmt * 2,
                str_height + bufferAmt, 5, 5);

        // Draws the string
        g.setColor(Color.WHITE);
        g.drawString(toPrint, x, y);
    }

    /** Paints the button icon using hardcoded player x/y values **/
    public void paintButton(Graphics g){

        int str_length = g.getFontMetrics().stringWidth("D");
        g.setColor(Color.WHITE);
        // Centers the button around the "D" icon
        g.fillOval(round.getButtonX() - (str_length / 2), round.getButtonY() - 15, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("D", round.getButtonX(), round.getButtonY());
    }


}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GameView extends JFrame{


    /** Window attributes **/
    public static final int WINDOW_HEIGHT= 540, WINDOW_WIDTH = 960,
            DECK_X = 300, CARDS_Y = 233,
            FIRST_CARD_X = 353, BUFFER = 5;

    public static final int TEXT_HEIGHT = 16;

    public static final String TITLE = "Poker";

    /** Background image **/
    private Image background, cardBack;

    /** Shared data **/
    private Game game;
    private Round round;

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

    public void paint(Graphics g) {
        // New Game Screen
        // Resets background


        g.setColor(Color.white);

        //g.drawImage(background, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, this);

        //Draw the deck
       // g.drawImage(back, deck_x, cards_y, test_width, test_height, this);

        switch (game.getState()) {

            case Game.NEW_ROUND:
                paintNewGameScreen(g);
                break;
            default:
                paintBoard(g);
                break;
        }
    }

    private void paintNewGameScreen(Graphics g) {


        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);

        paintStringInBox(g, "[N]ew Game", (int)(WINDOW_HEIGHT * .75), 25);
    }

    private void paintDeal(Graphics g) {
        paintBoard(g);
    }
    private void paintStreet(Graphics g, int gameState) {
        paintBoard(g);
    }
    private void paintShowdown(Graphics g) {
    }

    public void paintBoard(Graphics g){

        //Paint bg
        g.setColor(Color.WHITE);
        g.drawImage(background, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, this);


        //Paint Deck
        Card.drawFaceDown(g, DECK_X, CARDS_Y, this);

        ArrayList<Card> community = round.getCommunity();

        for (int i = 0; i < community.size(); i++){
            community.get(i).drawCard(g, FIRST_CARD_X + (i * (BUFFER + Card.WIDTH)), CARDS_Y, this);

        }

        paintStringInBox(g,"Pot: " + round.getPot(), CARDS_Y - 60, 10);

        ArrayList<Player> activePlayers = round.getActivePlayers();


        for (Player p: activePlayers) {
            p.drawPlayer(g, this);
        }
    }

    public static void paintStringInBox(Graphics g, String toPrint, int y, int bufferAmt){

        int str_length = g.getFontMetrics().stringWidth(toPrint);
        int str_height = g.getFontMetrics().getHeight();

        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect((WINDOW_WIDTH - str_length) / 2 - bufferAmt, y - str_height, str_length + bufferAmt * 2,
                str_height + bufferAmt, 10, 10);

        g.setColor(Color.WHITE);
        g.drawString(toPrint, (WINDOW_WIDTH -  str_length) / 2, y);
    }


}

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

        g.setColor(Color.white);

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


        g.drawImage(background, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, this);

        paintStringInBox(g, "[N]ew Round", (WINDOW_WIDTH - g.getFontMetrics().stringWidth("[N]ew Round")) / 2,
                (int)(WINDOW_HEIGHT * .75), BUFFER * 4);
    }

    public void paintBoard(Graphics g){

        //Paint bg
        g.setColor(Color.WHITE);
        g.drawImage(background, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, this);

        paintStringInBox(g, "Pot: " + round.getPot(), (WINDOW_WIDTH - g.getFontMetrics().stringWidth("Pot: " + round.getPot())) / 2,
                CARDS_Y - 60, BUFFER * 2);



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


        for (int i = 0; i < community.size(); i++){
            community.get(i).drawCard(g, FIRST_CARD_X + (i * (BUFFER + Card.WIDTH)), CARDS_Y, this);

        }
    }

    public static void paintStringInBox(Graphics g, String toPrint, int x, int y, int bufferAmt){



        int str_length = g.getFontMetrics().stringWidth(toPrint);
        int str_height = g.getFontMetrics().getHeight();

        g.setColor(Color.DARK_GRAY);
        g.fillRoundRect(x - bufferAmt, y - str_height, str_length + bufferAmt * 2,
                str_height + bufferAmt, 5, 5);

        g.setColor(Color.WHITE);
        g.drawString(toPrint, x, y);
    }

    public void paintButton(Graphics g){

        int str_length = g.getFontMetrics().stringWidth("D");
        g.setColor(Color.WHITE);
        g.fillOval(round.getButtonX() - (str_length / 2), round.getButtonY() - 15, 20, 20);
        g.setColor(Color.BLACK);
        g.drawString("D", round.getButtonX(), round.getButtonY());
    }


}

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameView extends JFrame {


    /** Window attributes **/
    public static final int WINDOW_HEIGHT= 540, WINDOW_WIDTH = 960,
            X_OFFSET = 50, Y_OFFSET = 75,
            LINE_HEIGHT = 25,

    deck_x = 300,
    test_x = 353, cards_y = 233, test_width = 47, test_height = 70, buffer_x = 5;


    public static final String TITLE = "Poker";

    /** Background image **/
    private Image background, test_card, back;

    /** Shared data **/
    private Game g;

    public GameView(Game g) {

        // Initialize instance variables.
        this.g = g;
        this.background = new ImageIcon("Resources/Poker BG.png").getImage();
        this.test_card = new ImageIcon("Resources/Cards/1.png").getImage();
        this.back = new ImageIcon("Resources/Cards/back.png").getImage();

        // Set-up the window and the buffer strategy.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

    }

    public void paint(Graphics g) {
        //Pre-flop
        paintPF(g);


        // Flop

        // Turn

        // River


        // Background

        // Community cards

        // Pot

        // Hole cards depending on player
    }



    public void paintPF(Graphics g){

            // Resets background
            g.setColor(Color.WHITE);
            g.drawImage(background, 0, 0,WINDOW_WIDTH, WINDOW_HEIGHT, this);

            g.drawImage(back, deck_x, cards_y, test_width, test_height, this);

            for (int i = 0; i < 3; i++){
                g.drawImage(test_card, test_x + (i * (buffer_x + test_width)), cards_y, test_width, test_height, this);

                if(i == 5){
                    //String test = test_x + (i * (buffer_x + test_width))
                    //g.drawString(test, 100, 100, this);
                }

            }

            // Draws instructions once
            /*if(g.isFirstTime()) {
                g.drawString("Welcome to close the box.", X_OFFSET, Y_OFFSET );
                g.drawString("Each turn roll 2 dice, and sum the numbers.", X_OFFSET, Y_OFFSET + LINE_HEIGHT);
                g.drawString("Then 'shut' any combination of boxes that adds to the sum.", X_OFFSET, Y_OFFSET + 2* LINE_HEIGHT);
                g.drawString("Try to get the lowest score.", X_OFFSET, Y_OFFSET + 3* LINE_HEIGHT);
                g.drawString("Input to confirm.", X_OFFSET, Y_OFFSET + 4* LINE_HEIGHT);
                return;
            }


            // Draw dice
            diceOne.draw(g, this);
            diceTwo.draw(g, this);

            // Draw each box
            for (Box b: boxes) {
                b.draw(g, this);
            }

            // Won/Loss screen
            if(d.hasWon()) {
                g.drawString("YOU CLEARED THE BOARD GOOD JOB!", X_OFFSET, Y_OFFSET);
                g.drawString("YOU WON!!!!", X_OFFSET, Y_OFFSET + LINE_HEIGHT);
                return;
            }
            if(d.hasLost()) {
                g.drawString("No More Possible moves :(", (int)(WIDTH / 3.0), HEIGHT - Y_OFFSET + LINE_HEIGHT);
                g.drawString("FINAL SCORE: " + d.getScore(), WIDTH - 4*(X_OFFSET), HEIGHT - Y_OFFSET + LINE_HEIGHT);
                return;
            }

            // Only displays if sum/score game is in progress
            g.drawString("SUM: "+ d.getSum(), X_OFFSET, HEIGHT - Y_OFFSET + LINE_HEIGHT);
            g.drawString("SCORE: "+ d.getScore(), WIDTH - 3*X_OFFSET, HEIGHT - Y_OFFSET + LINE_HEIGHT);

        }


             */
    }





}

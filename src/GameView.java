import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameView extends JFrame {


    /** Window attributes **/
    public static final int WINDOW_HEIGHT= 1600, WINDOW_WIDTH = 2560,
            X_OFFSET = 50, Y_OFFSET = 75,
            LINE_HEIGHT = 25;
    public static final String TITLE = "Poker";

    /** Background image **/
    private Image background;

    /** Shared data **/
    private Game g;

    public GameView(Game g) {

        // Initialize instance variables.
        this.g = g;
        this.background = new ImageIcon("").getImage();

        // Setup the window and the buffer strategy.
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle(TITLE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);

    }

    public void paint(Graphics g) {
        //Pre-flop

        // Flop

        // Turn

        // River


        // Background

        // Community cards

        // Pot

        // Hole cards depending on player
    }


}

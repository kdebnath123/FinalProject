import javax.swing.*;
import java.awt.*;

// Kirin Debnath
// Adapation from CardGame
// Last updated: December 7th 2023
public class Card {

    // Instance variables
    private String rank;
    private String suit;
    private int score;

    private Image frontImage;
    private static Image backImage;
    public static int WIDTH = 47, HEIGHT = 70;


    // Card constructor
    // Each card has a rank, suit, and point value
    public Card(String rank, String suit, int score){
        this.rank = rank;
        this.suit = suit;
        this.score = score;

        this.backImage = new ImageIcon("Resources/Cards/back.png").getImage();
    }

    public Card(String rank, String suit, int score, int imageIndex){
        this.rank = rank;
        this.suit = suit;
        this.score = score;

        this.frontImage = new ImageIcon("Resources/Cards/" + imageIndex +".png").getImage();
        this.backImage = new ImageIcon("Resources/Cards/back.png").getImage();
    }

    // Gets rank
    public String getRank() {
        return rank;
    }

    // Gets suit
    public String getSuit() {
        return suit;
    }

    public int getScore() {
        return score;
    }

    // ToString method for card class
    public String toString() {
        return rank + " of " + suit;
    }

    public void drawCard(Graphics g, int x, int y, GameView view){
        g.drawImage(frontImage, x,y, WIDTH, HEIGHT, view);
    }
    public static void drawFaceDown(Graphics g, int x, int y, GameView view){
        g.drawImage(backImage, x,y, WIDTH, HEIGHT, view);
    }




}
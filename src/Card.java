import javax.swing.*;
import java.awt.*;

// Kirin Debnath
// Adapation from CardGame
// Last updated: December 7th 2023
public class Card {

    // Instance variables
    private String rank;
    private String suit;

    private Image frontImage, backImage;


    // Card constructor
    // Each card has a rank, suit, and point value
    public Card(String rank, String suit, int imageIndex){
        this.rank = rank;
        this.suit = suit;

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


    // ToString method for card class
    public String toString() {
        return rank + " of " + suit;
    }

    public Image getFrontImage(){
        return frontImage;
    }


}
// Kirin Debnath
// Adapation from CardGame
// Last updated: December 7th 2023
public class Card {

    // Instance variables
    private String rank;
    private String suit;
    private int points;

    // Card constructor
    // Each card has a rank, suit, and point value
    public Card(String rank, String suit, int points){
        this.rank = rank;
        this.suit = suit;
        this.points = points;

    }

    // Gets rank
    public String getRank() {
        return rank;
    }

    // Sets rank
    public void setRank(String rank) {
        this.rank = rank;
    }

    // Gets suit
    public String getSuit() {
        return suit;
    }

    // Sets suit
    public void setSuit(String suit) {
        this.suit = suit;
    }

    // Gets points
    public int getPoints() {
        return points;
    }

    // Sets points
    public void setPoints(int points) {
        this.points = points;
    }

    // ToString method for card class
    public String toString() {
        return rank + " of " + suit;
    }

}
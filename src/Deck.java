// Kirin Debnath
// Adapation from CardGame
// Last updated: December 7th 2023
import java.util.ArrayList;

public class Deck {

    public  static final String[] RANKS = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K"};
    public static final String[] SUITS = {"Spades", "Hearts", "Diamonds", "Clubs"};

    public static final int ACE = 14, JACK = 11, QUEEN = 12, KING = 13;

    public static final int[] SCORE = {ACE, 2, 3, 4, 5, 6, 7, 8, 9, 10, JACK, QUEEN, KING};

    // Instance variables to hold each card
    private ArrayList<Card> deck;
    //Number of cards left
    private int cardsLeft;

    // Deck Constructor
    public Deck() {

        this.deck = new ArrayList<Card>();

        // Creates deck with rank of every suit
        for(int i = 0; i < RANKS.length; i++) {
            for (String suit: SUITS) {

                // Adds new card object to deck
                // deck.size + 1 accounts for non-zero indexed rss images

                deck.add(new Card(RANKS[i], suit, SCORE[i], deck.size() + 1));
            }
        }

        this.cardsLeft = deck.size();
    }

    // Returns true iff 0 cards left in deck
    public boolean isEmpty(){
        return cardsLeft == 0;
    }

    // Gets the number of cards left in the deck
    public int getCardsLeft(){
        return cardsLeft;
    }

    // Deals a card by returning the top card of deck
    public Card deal(){

        if(isEmpty()){
            return null;
        }

        // Gets top card off of deck
        return deck.get(--cardsLeft);

    }

    // Shuffles the deck
    // Precondition: All cards must be returned to deck
    public void shuffle(){


        int randomNum;

        for (int i = deck.size() - 1; i > 0; i--){

            // Generates random number from 0 to i
            randomNum = (int)(Math.random() * (i + 1));


            // Swaps cards and random number and i
            // Takes advantage of set method returning previously stored card: removes need for temp card
            deck.set(randomNum, deck.set(i, deck.get(randomNum)));

        }

        cardsLeft = deck.size();

    }

    // Prints all cards in deck
    public void printDeck(){

        System.out.println("\n\n\nNUMBER OF CARDS " + cardsLeft);

        // Prints each card in deck
        for(Card cards : deck){
            System.out.println(cards);
        }
    }
}


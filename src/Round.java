import java.util.ArrayList;

public class Round {

    Player[] players;
    int pot;
    ArrayList<Card> community;
    Deck deck;

    public Round(Player[] players, Deck deck) {
        this.players = players;
        pot = 0;
        this.deck = deck;
        community = new ArrayList<Card>();
    }

    public void play(){}



    public void deal(){}
    public void preFlop(){}
    public void flop(){}
    public void turn(){}
    public void river(){}





}

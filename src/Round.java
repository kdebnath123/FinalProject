import java.util.ArrayList;

public class Round {

    Player[] players;
    int numPlayers;
    int pot;
    int buttonSeat;
    ArrayList<Card> community;
    Deck deck;


    public Round(Player[] players, Deck deck) {
        this.players = players;
        numPlayers = players.length;
        pot = 0;
        this.deck = deck;
        community = new ArrayList<Card>();
    }

    public void play(){
        deck.shuffle();
        deal();

    }

    public void deal(){

        // Post big and small blinds
        players[(buttonSeat + 1) % numPlayers].bet();



        // deal two cards to each player




    }

    public void deal(Deck deck){

    }
    public void preFlop(){
        // Starting with UTG give each player the action, allow each player to see their cards

    }
    public void flop(){}
    public void turn(){}
    public void river(){}


    public void moveButton(){
        buttonSeat = (buttonSeat + 1) % players.length;
    }





}

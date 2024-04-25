import java.awt.image.BufferedImageFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class Round {


    ArrayList<Player> activePlayers;
    int numActivePlayers;
    int pot;
    int buttonSeat;
    ArrayList<Card> community;
    Deck deck;



    public Round(Player[] players, Deck deck) {

        numActivePlayers = players.length;
        activePlayers = new ArrayList<Player>();

        activePlayers.addAll(Arrays.asList(players));

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
        pot += activePlayers.get((buttonSeat + 2) % numActivePlayers).bet(Game.BIG_BLIND);
        pot += activePlayers.get((buttonSeat + 2) % numActivePlayers).bet(Game.BIG_BLIND / 2);


        // deal two cards to each player starting with button
        for (int i = 0; i < numActivePlayers; i++) {
            activePlayers.get((i + buttonSeat) % numActivePlayers).receiveCards(deck.deal(), deck.deal());
        }


    }


    public void preFlop(){
        // Starting with UTG give each player the action, allow each player to see their cards
        int i = 0;
        int callAmount = Game.BIG_BLIND;

        while (i < numActivePlayers){

            int currentSeat = (i + buttonSeat + 3) % numActivePlayers;

            int action = activePlayers.get(currentSeat).action(callAmount);

            switch (action) {

                case Game.FOLD:
                    activePlayers.remove(currentSeat);
                    break;

                case Game.CHECK_CALL:
                    pot += callAmount;
                    break;

                default:
                    pot += action;
            }





        }


    }
    public void flop(){}
    public void turn(){}
    public void river(){}


    public void moveButton(){
        buttonSeat = (buttonSeat + 1) % numActivePlayers;
    }





}

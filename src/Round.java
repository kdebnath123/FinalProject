import java.util.ArrayList;
import java.util.Arrays;

public class Round {


    private ArrayList<Player> activePlayers;
    private int numActivePlayers;
    private int pot = 0;
    private ArrayList<Card> community;
    private Deck deck;



    public Round(Player[] players, Deck deck) {

        numActivePlayers = players.length;
        activePlayers = new ArrayList<Player>();

        activePlayers.addAll(Arrays.asList(players));

        this.deck = deck;
        community = new ArrayList<Card>();
    }

    public void play(){



        deck.shuffle();
        deal();

        preFlop();

    }

    public void deal(){

        System.out.println("Now dealing:");

        // Post big and small blinds
        pot += activePlayers.get(1).bet(Game.BIG_BLIND );
        pot += activePlayers.get(0).bet(Game.BIG_BLIND / 2);
        System.out.println("Pot is: $" + pot);

        // deal two cards to each player starting with SB
        for (int i = 0; i < numActivePlayers; i++) {
            activePlayers.get(i).receiveCards(deck.deal(), deck.deal());
        }

        for(Player p: activePlayers){
            System.out.println(p);
        }


    }


    public void preFlop(){
        // Starting with UTG give each player the action, allow each player to see their cards
        tableAction(Game.BIG_BLIND, 2);

        System.out.println("Pre-flop: " + pot);
    }
    public void flop(){

        for (Player p: activePlayers){
            p.resetPotInvestment();
        }

        // Burn
        deck.deal();

        community.add(deck.deal());
        community.add(deck.deal());
        community.add(deck.deal());

        System.out.println("Flop:");

        for(Card c : community){
            System.out.println(c);
        }

        tableAction(0, 0);

        System.out.println("Total Pot:" + pot);

    }
    public void turn(){

        for (Player p: activePlayers){
            p.resetPotInvestment();
        }
        // Burn
        deck.deal();

        community.add(deck.deal());

        System.out.println("Turn:");

        for(Card c : community){
            System.out.println(c);
        }

        tableAction(0, 0);

        System.out.println("Total Pot:" + pot);

    }
    public void river(){

        for (Player p: activePlayers){
            p.resetPotInvestment();
        }

        // Burn
        deck.deal();

        community.add(deck.deal());

        System.out.println("River:");

        for(Card c : community){
            System.out.println(c);
        }

        tableAction(0, 0);

        System.out.println("Total Pot:" + pot);

    }


    // Remove the first element of the array and move to last position which shifts everything, saves many many math operonds
    public void moveButton(){
        activePlayers.add(activePlayers.remove(0));
    }

    /*** given a starting position and call amount, go around the table and allow each player to check/call/raise ***/
    public void tableAction (int callAmount, int startingPlayer){

        System.out.println(callAmount + " to stay-in");


        // Starting with given player give, each player the action
        int i = 0;
        while (i < activePlayers.size()){

            int currentSeat = (i + startingPlayer) % activePlayers.size();
            Player currentPlayer = activePlayers.get(currentSeat);


            int action = currentPlayer.action(callAmount);

            switch (action) {

                case Game.FOLD:
                    activePlayers.remove(currentSeat);
                    break;

                case Game.CHECK_CALL:
                    pot += currentPlayer.bet(callAmount - currentPlayer.getPotInvestment());
                    i++;
                    break;

                default:
                    pot += currentPlayer.bet(action + callAmount - currentPlayer.getPotInvestment());
                    tableAction(action + callAmount, currentSeat + 1);
                    return;
            }


        }
    }

    public void increasePot(int value){
        pot += value;
    }





}

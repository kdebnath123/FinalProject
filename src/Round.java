import java.util.ArrayList;
import java.util.Arrays;

public class Round {


    private ArrayList<Player> activePlayers;
    private final Player[] permanentPlayers;
    private int pot;
    private ArrayList<Card> community;
    private Deck deck;

    public static final int SB = 0, BB = 1, UTG = 2;



    public Round(Player[] players) {

        permanentPlayers = players;
        activePlayers = new ArrayList<Player>();
        this.deck = new Deck();
        community = new ArrayList<Card>();

        reset();
    }

    public void play(){



        deck.shuffle();
        deal();

        preFlop();

    }

    public void deal(){

        System.out.println("Now dealing:");

        // Post big and small blinds
        pot += activePlayers.get(BB).bet(Game.BIG_BLIND );
        pot += activePlayers.get(SB).bet(Game.BIG_BLIND / 2);
        System.out.println("Pot is: $" + pot);

        // deal two cards to each player starting with SB
        for (int i = 0; i < activePlayers.size(); i++) {
            activePlayers.get(i).receiveCards(deck.deal());
            activePlayers.get(i).receiveCards(deck.deal());
        }

        for(Player p: activePlayers){
            System.out.println(p);
        }


    }


    public void preFlop(){
        // Starting with UTG give each player the action, allow each player to see their cards
        tableAction(Game.BIG_BLIND, UTG);

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

        tableAction(0, SB);

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

        tableAction(0, SB);

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

        tableAction(0, SB);

        System.out.println("Total Pot:" + pot);

    }


    // Remove the first element of the array and move to last position which shifts everything, saves many many math operonds
    public void moveButton(){
        activePlayers.add(activePlayers.remove(SB));
    }

    /*** given a starting position and call amount, go around the table and allow each player to check/call/raise ***/
    public void tableAction (int callAmount, int startingPlayer){

        System.out.println(callAmount + " to stay-in");


        // Starting with given player give, each player the action
        int i = 0;
        while (i < activePlayers.size()) {

            if(activePlayers.size() == 1){
                winsHand(activePlayers.getFirst());
            }


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

    public void reset(){
        activePlayers.clear();
        activePlayers.addAll(Arrays.asList(permanentPlayers));
        moveButton();

        pot = 0;


        for (Player p: activePlayers) {
            p.resetPotInvestment();
            p.clearHoleCards();
        }
        community.clear();

    }

    // Controls what happens when someone wins
    private void winsHand(Player winner) {
        winner.addChips(pot);
    }
}

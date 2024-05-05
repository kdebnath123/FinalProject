import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Round {


    private int pot;
    private Player[] permanentPlayers;
    private ArrayList<Player> activePlayers;
    private ArrayList<Card> community;

    public static final int B = 0, SB = 1, BB = 2, UTG =3;
    private Deck deck;

    public Round(Player[] players) {

        permanentPlayers = players;

        activePlayers = new ArrayList<>();
        community = new ArrayList<>();
        deck = new Deck();
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
            System.out.println(activePlayers.get(i));
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

        System.out.println(community);

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

        System.out.println(community);

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

        System.out.println(community);

        tableAction(0, SB);

        System.out.println("Total Pot:" + pot);

    }

    public void showdown() {

        int max = Calc.MISS;
        // calc each player and find the best one
        for (Player p: activePlayers) {
            max = Math.max(p.calcHandStrength(community), max);
            System.out.println(p.getHoleCards());
            System.out.println(p.getName() + " has " + p.getHandStrength());
        }

        System.out.println("Max hand: " + max);

        int numWinners = 0;
        for (Player p: activePlayers) {
            if(p.getHandStrength() == max){
                numWinners++;
            }
        }

        for (Player p: activePlayers) {
            if(p.getHandStrength() == max){
                winsHand(p, pot / numWinners);
            }
        }
    }
    // Remove the first element of the array and move to last position which shifts everything, saves many many math operonds
    public void moveButton(){
        activePlayers.add(activePlayers.remove(SB));
    }

    /*** given a starting position and call amount, go around the table and allow each player to check/call/raise ***/
    public void tableAction (int callAmount, int startingPlayer){


        System.out.println(callAmount + " to stay-in");


        // Starting with given player give, each player the action

        int currentSeat = startingPlayer;
        for (int i = 0, n = activePlayers.size(); i < n; i++) {


            if(activePlayers.size() == 1){
                winsHand(activePlayers.getFirst(), pot);
                return;
            }


            currentSeat = (currentSeat) % activePlayers.size();
            Player currentPlayer = activePlayers.get(currentSeat);


            int action = currentPlayer.action(callAmount);

            switch (action) {

                case Game.FOLD:
                    activePlayers.remove(currentSeat);
                    break;

                case Game.CHECK_CALL:
                    pot += currentPlayer.bet(callAmount - currentPlayer.getPotInvestment());
                    currentSeat++;
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
        deck.shuffle();

    }

    // Controls what happens when someone wins
    private void winsHand(Player winner, int amount) {
        winner.addChips(amount);
        System.out.println("Winner:" + winner.getName());
    }

    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }


    public int getPot() {
        return pot;
    }

    public ArrayList<Card> getCommunity() {
        return community;
    }

    public Deck getDeck() {
        return deck;
    }

    public void addWindow(GameView window){
        this.window = window;
    }
}

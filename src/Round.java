import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Round {


    private int pot;
    private ArrayList<Player> permanentPlayers;
    private ArrayList<Player> activePlayers;
    private ArrayList<Card> community;

    public static final int B = 3, SB = 0, BB = 1, UTG =2;
    private Deck deck;

    public Round(Player[] players) {

        permanentPlayers = new ArrayList<>(List.of(players));

        activePlayers = new ArrayList<>();
        community = new ArrayList<>();
        deck = new Deck();
    }

    public void deal() {
        // Post big and small blinds
        pot += activePlayers.get(BB).bet(Game.BIG_BLIND);
        pot += activePlayers.get(SB).bet(Game.BIG_BLIND / 2);

        // deal two cards to each player starting with SB
        for (int i = 0; i < activePlayers.size(); i++) {
            activePlayers.get(i).receiveCards(deck.deal());
            activePlayers.get(i).receiveCards(deck.deal());
        }
    }


    /*public void preFlop(){
        // Starting with UTG give each player the action, allow each player to see their cards
        tableAction(Game.BIG_BLIND, UTG);

        System.out.println("Pre-flop: " + pot);
    }*/
    public void flop(){
        // Burn
        deck.deal();

        community.add(deck.deal());
        community.add(deck.deal());
        community.add(deck.deal());
    }
    public void turn(){

        // Burn
        deck.deal();

        community.add(deck.deal());
    }
    public void river(){
        // Burn
        deck.deal();

        community.add(deck.deal());
    }

    public void showdown() {

        int max = Calc.MISS;
        // calc each player and find the best one
        for (Player p: activePlayers) {
            //Show all hands
            p.setTheAction(true);

            max = Math.max(p.calcHandStrength(community), max);
        }


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
        resetPot();

    }

    public void steal() {


        winsHand(activePlayers.getFirst(), pot);
        resetPot();

    }


    // Remove the first element of the array and move to last position which shifts everything, saves many many math operonds
    public void moveButton(){
        permanentPlayers.add(permanentPlayers.remove(SB));
    }

    public void reset(){
        activePlayers.clear();
        moveButton();
        activePlayers.addAll(permanentPlayers);


        this.resetPot();


        for (Player p: activePlayers) {
            p.resetPotInvestment();
            p.clearHoleCards();
            p.setTheAction(false);
            p.resetAction();
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

    public void increasePot(int value){
        pot += value;
    }

    public ArrayList<Card> getCommunity() {
        return community;
    }

    public Deck getDeck() {
        return deck;
    }


    public void resetPlayers(Player[] players) {
        activePlayers.addAll(List.of(players));
    }

    public void resetPot() {
        pot = 0;
    }

    public void resetPlayerInvestment() {
        for (Player p: activePlayers){
            p.resetPotInvestment();
        }
    }

    public int getButtonX(){
        return  permanentPlayers.getLast().getButtonX();
    }
    public int getButtonY(){
        return  permanentPlayers.getLast().getY();
    }

    public void resetPlayerAction() {
        for (Player p: activePlayers) {
            p.resetAction();
        }
    }
}

import java.util.ArrayList;
import java.util.List;

/** Controls each round of the game **/
public class Round {

    /** Player data **/
    private int pot;
    private ArrayList<Player> permanentPlayers;
    private ArrayList<Player> activePlayers;

    public static final int B = 3, SB = 0, BB = 1, UTG =2;

    /** Card data **/
    private Deck deck;
    private ArrayList<Card> community;

    /** Constructor **/
    public Round(Player[] players) {

        permanentPlayers = new ArrayList<>(List.of(players));

        activePlayers = new ArrayList<>();
        community = new ArrayList<>();
        deck = new Deck();
    }

    /** Handles the deal actions **/
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

    /** Handles the flop **/
    public void flop(){
        // Burn
        deck.deal();

        // Reveal 3 community cards
        community.add(deck.deal());
        community.add(deck.deal());
        community.add(deck.deal());
    }

    /** Handles the turn **/
    public void turn(){

        // Burn
        deck.deal();

        // Reveal another card
        community.add(deck.deal());
    }

    /** Handles the flop **/
    public void river(){
        // Burn
        deck.deal();

        // Reveals final card
        community.add(deck.deal());
    }

    /** Handles the showdown **/
    public void showdown() {

        int max = Calc.MISS;

        // Calculate each player's best hand and find the winning hand
        for (Player p: activePlayers) {
            //Show all hands
            p.setTheAction(true);

            max = Math.max(p.calcHandStrength(community), max);
        }

        // Count the number of winners
        int numWinners = 0;
        for (Player p: activePlayers) {
            if(p.getHandStrength() == max){
                numWinners++;
            }
        }

        // Chop the pot based on winner amount
        for (Player p: activePlayers) {
            if(p.getHandStrength() == max){
                winsHand(p, pot / numWinners);
            }
        }

        resetPot();

    }

    /** Handles when a player steals the pot **/
    public void steal() {

        // Gives the last player standing the pot
        winsHand(activePlayers.getFirst(), pot);
        resetPot();

    }

    /** Gives chips to winning player **/
    private void winsHand(Player winner, int amount) {
        winner.addChips(amount);
    }


    /** Moves the button each round **/
    public void moveButton(){
        // Moves the first element to the back of the array, which 'shifts' everyone down
        permanentPlayers.add(permanentPlayers.remove(SB));
    }

    /** Resets to be ready for next round of play**/
    public void reset(){

        // Reload all players in
        activePlayers.clear();
        moveButton();
        activePlayers.addAll(permanentPlayers);


        this.resetPot();

        // Reset each player
        for (Player p: activePlayers) {
            p.resetPotInvestment();
            p.clearHoleCards();
            p.setTheAction(false);
            p.resetAction();
        }

        community.clear();
        deck.shuffle();
    }

    /** Helper reset methods */
    public void resetPot() {
        pot = 0;
    }

    public void resetPlayerInvestment() {
        for (Player p: activePlayers){
            p.resetPotInvestment();
        }
    }

    public void resetPlayerAction() {
        for (Player p: activePlayers) {
            p.resetAction();
        }
    }


    /** Getters **/
    public ArrayList<Player> getActivePlayers() {
        return activePlayers;
    }

    public int getPot() {
        return pot;
    }

    public ArrayList<Card> getCommunity() {
        return community;
    }

    public int getButtonX(){
        return  permanentPlayers.getLast().getButtonX();
    }
    public int getButtonY(){
        return  permanentPlayers.getLast().getY();
    }

    /** Increases pot value */
    public void increasePot(int value){
        pot += value;
    }
}

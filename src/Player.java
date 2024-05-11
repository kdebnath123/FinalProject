import java.awt.*;
import java.util.ArrayList;

/** Allows players to have attributes and saved data **/
public class Player {

    /** Player attributes **/
    private String name;
    private int bank;
    private ArrayList<Card> holeCards;
    private int handStrength;
    private int potInvestment;

    private int x, y, buttonX;
    private String action;

    private boolean hasTheAction = false;


    /** Constructor **/
    public Player(String name, int chips, int x, int y, int buttonX) {
        this.x = x;
        this.y = y;
        this.buttonX = buttonX;
        this.action = null;

        this.name = name;
        this.bank = chips;
        this.holeCards = new ArrayList<>();

    }

    public int calcHandStrength(ArrayList<Card> community) {
        handStrength = Calc.evalPlayer(holeCards, community);
        return handStrength;
    }


    /** Setters (and glorified setters) **/
    public void receiveCards(Card card) {
        holeCards.add(card);
    }

    public int bet(int value){
        bank -= value;
        potInvestment += value;

        return value;
    }
    public void setTheAction(boolean action) {
        hasTheAction = action;
    }

    public void addChips(int value) {
        bank += value;
    }


    public void setAction(String action){
        this.action = action;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getButtonX() {
        return buttonX;
    }





    /** Getters **/
    public String getName() {
        return name;
    }

    public int getHandStrength() {
        return handStrength;
    }


    public int getPotInvestment() {
        return potInvestment;
    }



    /** Reset helper methods **/

    public void resetPotInvestment(){
        potInvestment = 0;
    }


    public void clearHoleCards() {
        holeCards.clear();
    }

    public void resetAction(){
        this.action = null;
    }


    /** Allows player to draw itself **/
    public void drawPlayer(Graphics g, GameView view){

        // Informs other players about action made
        if (action != null){
            g.drawString(action, x, y - 24);
            view.paintStringInBox(g, action, x, y -24, GameView.BUFFER);
        }

        // Draws player info
        g.setColor(Color.white);
        g.drawString(name + " @ $" + bank, x, y);

        // Draws cards
        for (int i = 0; i < holeCards.size(); i++){
            // If it is their turn allow them to see the cards
            if(hasTheAction) {

                // Shows available moves
                g.setColor(Color.YELLOW);
                g.drawString("[R]aise", 25,  100);
                g.drawString("[C]heck/[C]all", 25, 120);
                g.drawString("[F]old", 25, 140);

                // Draws each card
                holeCards.get(i).drawCard(g, x + (i * (GameView.BUFFER + Card.WIDTH)), y + GameView.BUFFER, view);
            } else{
                Card.drawFaceDown(g, x + (i * (GameView.BUFFER + Card.WIDTH)), y + GameView.BUFFER, view);
            }

        }

    }
}

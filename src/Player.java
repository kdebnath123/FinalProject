import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;

public class Player {
    private String name;
    private int bank;
    private int handStrength;
    private int potInvestment;
    private ArrayList<Card> holeCards;
    private int x, y, buttonX;
    private String action;

    private boolean hastheAction = false;


    public Player(String name, int chips, int x, int y, int buttonX) {
        this.x = x;
        this.y = y;
        this.buttonX = buttonX;
        this.action = null;

        this.name = name;
        this.bank = chips;
        this.holeCards = new ArrayList<>();

    }


    public void receiveCards(Card card) {
        holeCards.add(card);
    }

    public int bet(int value){
        bank -= value;
        potInvestment += value;

        return value;
    }


    // Getters
    public String getName() {
        return name;
    }
    public int getChips() {
        return bank;
    }
    public ArrayList<Card> getHoleCards() {
        return holeCards;
    }


    public boolean hasTheAction() {
        return hastheAction;
    }
    public void setTheAction(boolean action) {
        hastheAction = action;
    }

    public int getPotInvestment() {
        return potInvestment;
    }

    public void resetPotInvestment(){
        potInvestment = 0;
    }

    public String toString() {
        return getName() + "'s hand: " + holeCards.getFirst() + " / " + holeCards.getLast() + " @" + bank;
    }

    public void clearHoleCards() {
        holeCards.clear();
    }

    public void addChips(int value) {
        bank += value;
    }

    public int getBank() {
        return bank;
    }

    public int getHandStrength() {
        return handStrength;
    }

    public int calcHandStrength(ArrayList<Card> community) {
        handStrength = Calc.evalPlayer(holeCards, community);
        return handStrength;
    }

    public void drawPlayer(Graphics g, GameView view){

        // Informs other players about action made
        if (action != null){
            g.drawString(action, x, y - 24);
            view.paintStringInBox(g, action, x, y -24, GameView.BUFFER);
        }

        g.setColor(Color.white);
        g.drawString(name + " @ $" + bank, x, y);

        for (int i = 0; i < holeCards.size(); i++){
            if(hastheAction) {

                g.setColor(Color.YELLOW);
                g.drawString("[R]aise", 25,  100);
                g.drawString("[C]heck/[C]all", 25, 120);
                g.drawString("[F]old", 25, 140);


                holeCards.get(i).drawCard(g, x + (i * (GameView.BUFFER + Card.WIDTH)), y + GameView.BUFFER, view);
            } else{
                Card.drawFaceDown(g, x + (i * (GameView.BUFFER + Card.WIDTH)), y + GameView.BUFFER, view);
            }

        }

    }

    public void setAction(String action){
        this.action = action;
    }

    public void resetAction(){
        this.action = null;
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
}

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private String name;
    private int bank;
    private int potInvestment;
    private ArrayList<Card> holeCards;

    private Scanner input;


    public Player(String name, int chips) {
        this.name = name;
        this.bank = chips;
        this.holeCards = new ArrayList<>();

        //TODO: remove
        this.input = new Scanner(System.in);
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


    // return amt to increase pot by
    public int action(int callAmount) {

        //TODO: impement keylistner



        if (callAmount - potInvestment > bank){
            System.out.println("Invalid amount, auto folding");
            return Game.FOLD;
        }

        while(true){

            //System.out.println("Enter action: [F]old, [C]heck/[C]all, [R]aise ");
            System.out.print(getName() + "'s action: ");
            String c = input.nextLine();


            switch (c){

                case "F":
                    System.out.println(getName() + " Folded");
                    return Game.FOLD;
                case "C":

                    if (callAmount == potInvestment){
                        System.out.println(getName() + " Checks");
                    }
                    else {
                        System.out.println(getName() + " Calls for $" + (callAmount - potInvestment));
                    }

                    return Game.CHECK_CALL;



                case "R":
                    int raiseAmount;

                    if(callAmount + Game.BIG_BLIND > bank){
                        System.out.println("Not enough $ to raise, auto-calling");
                    }

                    // Forces user to input a number between Call Amount + BB and bankroll
                    while (true) {
                        // Try/Catch learned from Stack Overflow
                        try {
                            System.out.print("Raise a valid amount (min raise BB): ");
                            raiseAmount = input.nextInt();

                            if (raiseAmount >= Game.BIG_BLIND && raiseAmount <= bank - callAmount) {
                                input.nextLine();
                                break;
                            }
                        }
                        catch (InputMismatchException e) {
                            input.next();
                        }
                    }

                    System.out.println(getName() + " Raises");
                    return raiseAmount;
            }
        }
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
}

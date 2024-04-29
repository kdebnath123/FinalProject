import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private String name;
    private int bank;
    private int potInvestment;
    private Card[] holeCards;
    public static final int HOLE_NUM = 2;

    private Scanner input;


    public Player(String name, int chips) {
        this.name = name;
        this.bank = chips;
        this.holeCards = new Card[HOLE_NUM];

        //TODO: remove
        this.input = new Scanner(System.in);
    }


    public void receiveCards(Card first, Card second) {
        holeCards[0] = first;
        holeCards[1] = second;
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
    public Card[] getHoleCards() {
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

    public String toString() {
        return getName() + "'s hand: " + holeCards[0] + " / " + holeCards[1] + " @" + bank;
    }
}

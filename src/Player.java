import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player {
    private String name;
    private int bank;
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


    public int action(int callAmount) {

        //TODO: impement keylistner

        System.out.println(getName() + "'s action:");

        if (callAmount > bank){
            System.out.println("Invalid amount, auto folding");
            return Game.FOLD;
        }

        while(true){

            System.out.println("Enter action: [F]old, [C]heck/[C]all, [R]aise ");
            String c = input.nextLine();


            switch (c){

                case "F":
                    System.out.println(getName() + " Folded");
                    return Game.FOLD;
                case "C":

                    if (callAmount == 0){
                        System.out.println(getName() + "Checks");
                    }
                    else {
                        System.out.println(getName() + "Calls");
                    }

                    bet(callAmount);
                    return Game.CHECK_CALL;



                case "R":
                    int raiseAmount;

                    // Forces user to input a number between Call Amount and bankroll
                    while (true) {
                        // Try/Catch learned from Stack Overflow
                        try {
                            System.out.print("Raise a valid amount: ");
                            raiseAmount = input.nextInt();

                            if (raiseAmount > callAmount + 1 && raiseAmount <= bank) {
                                input.nextLine();
                                break;
                            }
                        }
                        catch (InputMismatchException e) {
                            input.next();
                        }
                    }

                    return bet(raiseAmount);
            }
        }
    }
}

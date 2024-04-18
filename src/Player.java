public class Player {
    private String name;
    private int chips;
    private Card[] holeCards;
    public static final int HOLE_NUM = 2;


    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.holeCards = new Card[HOLE_NUM];
    }

    public void fold(){}
    public void check(){}
    public void call(){}
    public void raise(){}


    public void deal(Deck deck){

    }



    // Getters
    public String getName() {
        return name;
    }
    public int getChips() {
        return chips;
    }
    public Card[] getHoleCards() {
        return holeCards;
    }
}

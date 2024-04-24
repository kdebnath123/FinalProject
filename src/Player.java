public class Player {
    private String name;
    private int bank;
    private Card[] holeCards;
    public static final int HOLE_NUM = 2;


    public Player(String name, int chips) {
        this.name = name;
        this.bank = chips;
        this.holeCards = new Card[HOLE_NUM];
    }

    public void fold(){}
    public void check(){}
    public void call(){}
    public void raise(){}




    public void bet(int value){
        bank -= value;
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
}

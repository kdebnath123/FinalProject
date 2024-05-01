import java.util.ArrayList;

public class Calc {
        public static final int ROYAL_FLUSH = 90000,
                                STRAIGHT_FLUSH = 80000,
                                QUADS = 70000,
                                FULL_HOUSE = 60000,
                                FLUSH = 50000,
                                STRAIGHT = 40000,
                                TRIPS = 30000,
                                TWO_PAIR = 20000,
                                PAIR = 10000;

        public static final int MISS = -1, AS_PRIMARY = 100, AS_SECONDARY = 1;

        // TODO: REMOVE TEST METHOD
        public static void main(String[] args) {

                ArrayList<Card> possibleHole = new ArrayList<>();
                ArrayList<Card> possibleCommunity = new ArrayList<>();

                /* // TEST WITH CERTIAN HAN
                possibleHole.add(new Card("8", Deck.SUITS[1], 8));
                possibleHole.add(new Card("7", Deck.SUITS[1], 7));
                possibleCommunity.add(new Card("6", Deck.SUITS[0], 6));
                possibleCommunity.add(new Card("5", Deck.SUITS[0], 5));
                possibleCommunity.add(new Card("4", Deck.SUITS[0], 4));
                possibleCommunity.add(new Card("3", Deck.SUITS[0], 3));
                possibleCommunity.add(new Card("2", Deck.SUITS[0], 2));
                */

                Deck deck = new Deck();
                deck.shuffle();


                for (int i = 0; i < 5; i++) {
                        possibleCommunity.add(deck.deal());
                }
                possibleHole.add(deck.deal());
                possibleHole.add(deck.deal());

                int result = Calc.evalPlayer(possibleHole, possibleCommunity);

        }

        public static int evalPlayer(ArrayList<Card> hole, ArrayList<Card> community){
                //Combine hole and community cards
                ArrayList<Card> total = new ArrayList<>();
                total.addAll(hole);
                total.addAll(community);

                //Sort the seven card hand
                total = Calc.sort(total);

                //Find all 5 card combinations from 7 card hand
                ArrayList<ArrayList<Card>> combinations = Calc.createCombinations(total);

                // Calc Score for each 5 card hand
                ArrayList<Integer> scores = new ArrayList<>();
                for (ArrayList<Card> fiveCard: combinations) {
                        scores.add(Calc.calcValue(fiveCard));
                }

                // Find the max possible score
                int max = -1;
                for (int score: scores){
                        if(score > max){
                                max = score;
                        }
                }
                return max;
        }

        private static ArrayList<ArrayList<Card>> createCombinations(ArrayList<Card> SevenCard){

                ArrayList<ArrayList<Card>> ordered = new ArrayList<>();

                for(int i = 0, n = SevenCard.size(); i < n; i++){
                        for(int j = i + 1; j < n; j++){
                                ArrayList<Card> temp = new ArrayList<>(SevenCard);
                                temp.remove(j);
                                temp.remove(i);
                                ordered.add(temp);
                        }
                }

                return ordered;
        }

        private static ArrayList<Card> sort(ArrayList<Card> sevenCard){
                return mergeSort(sevenCard, 0, sevenCard.size() - 1);

        }
        /*** Implementation of merge sort on an ArrayList of Cards ***/
        private static ArrayList<Card> mergeSort(ArrayList<Card> arr, int left, int right) {

                // Base case when only one element
                if (right - left == 0) {
                        ArrayList<Card> newArr = new ArrayList<>();
                        newArr.add(arr.get(left));
                        return newArr;
                }

                int med = (right + left) / 2;
                // Recurse on left and right side of arr to further divide
                ArrayList<Card> arrLeft = mergeSort(arr, left, med);
                ArrayList<Card> arrRight = mergeSort(arr, med + 1, right);

                // Merge sorted left/right sides
                return merge(arrLeft, arrRight);
        }

        /*** Merges two sorted Array List of Cards into sorted ArrayList of Cards ***/
        private static ArrayList<Card> merge(ArrayList<Card> arr1, ArrayList<Card> arr2) {

                ArrayList<Card> merged = new ArrayList<>();

                int a = 0, b = 0;

                // Compare and add elements to merged Array List while both lists have unmerged elements
                while(a < arr1.size() && b < arr2.size()) {
                        // Add earlier element to Array List
                        if (arr1.get(a).getScore() >= (arr2.get(b)).getScore()) {
                                merged.add(arr1.get(a++));
                        }
                        else {
                                merged.add(arr2.get(b++));
                        }

                }

                // Add remaining list to merged ArrayList
                while(a < arr1.size()) {
                        merged.add(arr1.get(a++));
                }

                while(b < arr2.size()) {
                        merged.add(arr2.get(b++));
                }

                return merged;
        }


        private static int calcValue(ArrayList<Card> fiveCard){
                // check for straight flush or royal
                int result = hasStraightOrRoyalFlush(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for Quads
                result = hasQuads(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for full house (pair) + (trips)
                result = hasFullHouse(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for flush
                result = hasFlush(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for a straight
                result = hasStraight(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for trips
                result = hasTrips(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for two pair
                result = hasTwoPair(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for a pair
                result = hasPair(fiveCard);
                if(result != MISS){
                        return result;
                }

                // check for a high card
                return hasHighCard(fiveCard) * AS_PRIMARY;
        }

        /**
         * Checks for Royal or Straight Flush
         * Exists when player has a flush and straight
         * Royal iff Ace high straight + flush
         * **/
        private static int hasStraightOrRoyalFlush(ArrayList<Card> total) {


                if (hasFlush(total) == MISS){
                        return MISS;
                }

                int straightResult = hasStraight(total);

                // High card on the straight
                int straightHigh = (straightResult / 100) % 100;


                if(straightResult == MISS){
                        return MISS;
                }
                else if (straightHigh == Deck.ACE){
                        return ROYAL_FLUSH;
                }
                else{
                        return STRAIGHT_FLUSH + straightHigh * AS_PRIMARY;
                }


        }

        /*** works **/
        private static int hasQuads(ArrayList<Card> total) {

                int tripsResult = hasTrips(total);
                int tripsCard = (tripsResult / 100) % 100;

                if (tripsResult == MISS) {
                        return MISS;
                }

                //Remove all instances of the trips card which also removes the forth card of potential quads
                ArrayList<Card> noTrips = removeInstance(total, tripsCard);


                if (noTrips.size() == 1) {
                        return QUADS + tripsCard * AS_PRIMARY + (hasHighCard(noTrips) * AS_SECONDARY);
                }

                return MISS;

        }


        private static int hasFullHouse(ArrayList<Card> total) {

                // Check for trips
                int tripsResult = hasTrips(total);
                int tripsCard = (tripsResult / 100) % 100;

                if (tripsResult == MISS){
                        return MISS;
                }


                // Check the unused cards for a pair
                ArrayList<Card> handNoTrips = removeInstance(total, tripsCard);
                int pairResult = hasPair(handNoTrips);
                int pairCard = (pairResult / 100) % 100;

                if(pairResult == MISS){
                        return  MISS;
                }

                return FULL_HOUSE + (tripsCard * AS_PRIMARY) + (pairCard * AS_SECONDARY);
        }

        /** Works **/
        private static int hasFlush(ArrayList<Card> total) {

                String neededSuit = total.get(0).getSuit();

                for (Card c: total) {
                      if(!c.getSuit().equals(neededSuit)){
                              return MISS;
                      }
                }

                return FLUSH + total.getFirst().getScore() * AS_PRIMARY;
        }
        /** Works **/
        private static int hasStraight(ArrayList<Card> total) {
                int highCard = total.get(0).getScore();

                for (Card c: total) {
                        if(c.getScore() != highCard--){
                                return MISS;
                        }
                }


                return STRAIGHT + (total.getFirst().getScore() * AS_PRIMARY);
        }
        /** Works **/
        private static int hasTrips(ArrayList<Card> total) {

                for(int i = 0, n = total.size() - 2; i < n; i++){

                        if(total.get(i).getScore() == total.get(i + 1).getScore() &&
                                total.get(i + 1).getScore() == total.get(i + 2).getScore()){

                                int tripsCard = total.get(i).getScore();
                                return TRIPS + (tripsCard * AS_PRIMARY) +
                                        (hasHighCard(removeInstance(total, tripsCard)) * AS_SECONDARY);
                        }
                }

                return MISS;
        }
        /** Works **/
        private static int hasTwoPair(ArrayList<Card> total) {

                // Check for pair
                int fistPairResult = hasPair(total);
                int firstPairCard = (fistPairResult / 100) % 100;

                if (fistPairResult == MISS){
                        return MISS;
                }


                // Check the unused cards for a pair
                ArrayList<Card> NoHighPair = removeInstance(total, firstPairCard);
                int secondPairResult = hasPair(NoHighPair);
                int secondPairCard = (secondPairResult / 100) % 100;

                if(secondPairResult == MISS){
                        return  MISS;
                }

                return TWO_PAIR + (firstPairCard * AS_PRIMARY) + (secondPairCard * AS_SECONDARY);
        }

        /** Returns the highest pair found **/
        private static int hasPair(ArrayList<Card> total) {
                for(int i = 0, n = total.size() - 1; i < n; i++){

                        if(total.get(i).getScore() == total.get(i + 1).getScore()){
                                int pairCard = total.get(i).getScore();
                                return PAIR + (pairCard * AS_PRIMARY) + (hasHighCard(removeInstance(total, pairCard)) * AS_SECONDARY);
                        }
                }

                return MISS;
        }

        private static int hasHighCard(ArrayList<Card> total) {

                if(total.isEmpty()){
                        return 0;
                }

                return total.getFirst().getScore();
        }

        /***
         * Removes all instances of a given card
         * @return
         */
        private static ArrayList<Card> removeInstance(ArrayList<Card> total, int toRemove) {

                ArrayList<Card> removed = new ArrayList<>();

                for (Card c: total) {
                        if(c.getScore() != toRemove){
                                removed.add(c);
                        }
                }

                return removed;
        }


}

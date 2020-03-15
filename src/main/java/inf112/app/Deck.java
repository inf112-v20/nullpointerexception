package inf112.app;
import java.util.ArrayList;
import java.util.Random;


public class Deck {
    private ArrayList<Card> deck;
    private ArrayList<Integer> priority; // to keep track of used priorities

    public Deck() {
        priority = new ArrayList<>();
        deck = new ArrayList<>();
        makeDeck(new int[] {18, 12, 6, 6, 18, 18, 6});
        deck = shuffleDeck();

    }

    /**
     * Creates a new shuffled deck.
     * @return ArrayList
     */
    private ArrayList<Card> shuffleDeck() {
        Random ran = new Random();
        ArrayList<Card> shuffledDeck = new ArrayList<>();
        for (Card card: deck) {
            shuffledDeck.add(ran.nextInt(Math.max(1, shuffledDeck.size())), card);
        }
        return shuffledDeck;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Creates a unique random priority between 100 and 990
     * @return int
     */
    private Integer assignPriority() {
        Random random = new Random();
        int prior = random.nextInt(99);
        while (prior >= 10 && !priority.contains(prior)) {
            prior = random.nextInt(99);
        }
        priority.add(prior);
        return prior * 10;
    }

    /**
     * Creates a new deck of Cards
     * @param numbers array with the different numbers of Cards
     */
    private void makeDeck(int[] numbers) {
        CardDirection[] directions = new CardDirection[]{
                CardDirection.MOVE1, CardDirection.MOVE2, CardDirection.MOVE3, CardDirection.BACKUP,
                CardDirection.TURNRIGHT, CardDirection.TURNLEFT, CardDirection.TURN180};
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < numbers[i]; j++) {
                deck.add(new Card(assignPriority(), directions[i]));
            }
        }
    }
    public Card dealCard() {
        Card card = deck.get(0);
        deck.remove(0);
        return card;
    }
}

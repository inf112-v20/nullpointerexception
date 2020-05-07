package inf112.app.cards;

import java.util.ArrayList;
import java.util.Random;


public class Deck {
    private ArrayList<Card> deck;
    private final ArrayList<Integer> priority; // to keep track of used priorities
    private ArrayList<Card> discardPile;

    public Deck() {
        priority = new ArrayList<>();
        deck = new ArrayList<>();
        discardPile = new ArrayList<>();
        makeDeck(new int[]{18, 12, 6, 6, 18, 18, 6});
        deck = shuffleDeck(deck);
    }

    /**
     * Creates a new shuffled deck.
     *
     * @return ArrayList
     */
    private ArrayList<Card> shuffleDeck(ArrayList<Card> d) {
        Random ran = new Random();
        ArrayList<Card> shuffledDeck = new ArrayList<>();
        for (Card card : d) {
            shuffledDeck.add(ran.nextInt(Math.max(1, shuffledDeck.size())), card);
        }
        return shuffledDeck;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    /**
     * Sets the discardPile given an ArrayList.
     *
     * @param d ArrayList<Card>
     */
    public void setDiscardPile(ArrayList<Card> d) {
        for (Card card : d) {
            discardPile.add(card);
        }
    }

    /**
     * Creates a unique random priority between 100 and 990
     *
     * @return int
     */
    private Integer assignPriority() {
        Random random = new Random();
        int prior = random.nextInt(99);
        while (prior <= 10 || priority.contains(prior)) {
            prior = random.nextInt(99);
        }
        priority.add(prior);
        return prior * 10;
    }

    /**
     * Creates a new deck of Cards
     *
     * @param numbers array with the different numbers of Cards
     */
    private void makeDeck(int[] numbers) {
        CardType[] directions = new CardType[]{
                CardType.MOVE1, CardType.MOVE2, CardType.MOVE3, CardType.BACKUP,
                CardType.TURNRIGHT, CardType.TURNLEFT, CardType.TURN180};
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < numbers[i]; j++) {
                deck.add(new Card(assignPriority(), directions[i]));
            }
        }
    }

    /**
     * Deals an arraylist of cards of size n
     *
     * @param n size of the arraylist
     * @return arraylist of cards
     */
    public ArrayList<Card> dealCards(int n) {
        ArrayList<Card> cards = new ArrayList<>();
        if (deck.size() < n)
            shuffleDiscardPile();
        for (int i = 0; i < n; i++) {
            cards.add(deck.remove(0));
        }
        return cards;
    }

    /**
     * Shuffels the discardPile and adds it to the deck.
     */
    public void shuffleDiscardPile() {
        deck.addAll(shuffleDeck(discardPile));
        discardPile = new ArrayList<>();
    }
}

package inf112.app;
import java.util.ArrayList;
import java.util.Random;


public class Deck {
    private ArrayList<Card> deck;
    public Deck() {
        Random random = new Random();
        deck = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Card card = new Card(random.nextInt(400), CardDirection.TURNRIGHT);
            deck.add(card);
        }
        for (int i = 0; i < 4; i++) {
            Card card = new Card(random.nextInt(400), CardDirection.TURNLEFT);
            deck.add(card);
        }
        deck = shuffleDeck();

    }
    private ArrayList<Card> shuffleDeck() {
        Random ran = new Random();
        ArrayList<Card> temp = new ArrayList();
        for (Card card: deck) {
            temp.add(ran.nextInt(Math.max(1, temp.size())), card);
        }
        return temp;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
}

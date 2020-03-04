package inf112.app;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Deck {
    private ArrayList<Card> deck;
    private ArrayList<Integer> priority;

    public Deck() {
        priority = new ArrayList<>();
        deck = new ArrayList<>();
        makeDeck(new int[] {18, 12, 6, 6, 18, 18, 6});
        deck = shuffleDeck();

    }
    private ArrayList<Card> shuffleDeck() {
        Random ran = new Random();
        ArrayList<Card> temp = new ArrayList<>();
        for (Card card: deck) {
            temp.add(ran.nextInt(Math.max(1, temp.size())), card);
        }
        return temp;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
    private Integer assignPriority() {
        Random random = new Random();
        int prior = random.nextInt(99);
        while (prior >= 10 && !priority.contains(prior)) {
            prior = random.nextInt(99);
        }
        priority.add(prior);
        return prior * 10;
    }
    private void makeDeck(int[] temp) {
        CardDirection[] directions = new CardDirection[]{
                CardDirection.Move1, CardDirection.Move2, CardDirection.Move3, CardDirection.Backup,
                CardDirection.TURNRIGHT, CardDirection.TURNLEFT, CardDirection.TURN180};
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < temp[i]; j++) {
                deck.add(new Card(assignPriority(), directions[i]));
            }
        }
    }
}

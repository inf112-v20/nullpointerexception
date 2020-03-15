package inf112.app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardTest {
    private Card card;
    private Deck deck;

    public CardTest() {
        card = new Card(100, CardDirection.MOVE1);
        deck = new Deck();
    }

    @Test
    public void getDirTest() {
        assertEquals(CardDirection.MOVE1, card.getDir());
    }
    @Test
    public void getPriorityTest() {
        assertEquals(100, card.getPriority());
    }

    @Test
    public void getDeckTest() {
        assertEquals(84, deck.getDeck().size());
    }

    @Test
    public void shuffleTest() {
        int idx = 0;
        int count = 1;
        while (idx < deck.getDeck().size() && deck.getDeck().get(idx).getDir().equals(
                deck.getDeck().get(idx + 1).getDir())) {
            count++;
            idx++;
        }
        assertNotEquals(5, count);
    }
}

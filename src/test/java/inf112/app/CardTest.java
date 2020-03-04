package inf112.app;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardTest {
    Card card;
    Deck deck;
    public CardTest() {
        card = new Card(100, CardDirection.Move1);
        deck = new Deck();
    }

    @Test
    public void getDirTest() {
        assertEquals(CardDirection.Move1, card.getDir());
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
    public void ShuffleTest() {
        int idx = 0;
        int count = 1;
        while (idx < deck.getDeck().size() && deck.getDeck().get(idx).getDir().equals(
                deck.getDeck().get(idx + 1).getDir())) {
            count ++;
            idx ++;
        }
        assertNotEquals(5, count);
    }
}

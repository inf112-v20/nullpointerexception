package inf112.app;

import inf112.app.player.Direction;
import inf112.app.player.Player;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CardTest {
    private Card card;
    private Deck deck;
    private Player player;

    public CardTest() {
        card = new Card(100, CardType.MOVE1);
        deck = new Deck();
        player = new Player(Direction.SOUTH);
    }

    @Test
    public void getTypeTest() {
        assertEquals(CardType.MOVE1, card.getType());
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
    public void dealCardTest() {
        deck = new Deck();
        Card c = deck.getDeck().get(0);
        ArrayList<Card> dealtCards = deck.dealCards(9);
        assertEquals(dealtCards.size(), 9);
        assertEquals(c, dealtCards.get(0));
    }

    @Test
    public void shuffleTest() {
        int idx = 0;
        int count = 1;
        while (idx < deck.getDeck().size() && deck.getDeck().get(idx).getType().equals(
                deck.getDeck().get(idx + 1).getType())) {
            count++;
            idx++;
        }
        assertNotEquals(5, count);
    }
}

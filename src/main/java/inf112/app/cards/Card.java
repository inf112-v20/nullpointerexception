package inf112.app.cards;


public class Card {
    private final Integer priority;
    private final CardType type;

    public Card(int priority, CardType type) {
        this.priority = priority;
        this.type = type;
    }

    /**
     * @return priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return type The CardType of the card
     */
    public CardType getType() {
        return type;
    }


    @Override
    public String toString() {
        return "Card{" +
                "priority=" + priority +
                ", type=" + type +
                '}';
    }
}

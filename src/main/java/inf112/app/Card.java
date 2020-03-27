package inf112.app;


public class Card {
    private int priority;
    private CardType type;
    private int steps;


    public Card(int priority, CardType dir) {
        this.priority = priority;
        this.type = dir;
        setSteps();

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

    private void setSteps() {
        switch (getType()) {
            case MOVE1:
                steps = 1;
                break;
            case MOVE2:
                steps = 2;
                break;
            case MOVE3:
                steps = 3;
                break;
            case BACKUP:
                steps = 1;
                break;
            default:
                steps = 0;
                break;

        }

    }

    /**
     * @return steps The number of tiles a card moves a player.
     */
    public int getSteps() { return steps; }
}

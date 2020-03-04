package inf112.app;


public class Card {
    private int priority;
    private CardDirection dir;


    public Card(int priority, CardDirection dir) {
        this.priority = priority;
        this.dir = dir;
    }

    /**
     *
     * @return priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     *
     * @return dir
     */
    public CardDirection getDir() {
        return dir;
    }
}

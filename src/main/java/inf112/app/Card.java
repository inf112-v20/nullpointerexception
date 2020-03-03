package inf112.app;

import inf112.app.player.Direction;

public class Card {
    private int priority;
    private CardDirection dir;

    public Card(int priority, CardDirection dir) {
        this.priority = priority;
        this.dir = dir;
    }

    public int getPriority() {
        return priority;
    }
    public CardDirection getDir() {
        return dir;
    }
}

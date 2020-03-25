package inf112.app;


public class Card {
    private int priority;
    private CardDirection dir;
    private int steps;


    public Card(int priority, CardDirection dir) {
        this.priority = priority;
        this.dir = dir;
        setSteps();

    }

    /**
     * @return priority
     */
    public int getPriority() {
        return priority;
    }

    /**
     * @return dir
     */
    public CardDirection getDir() {
        return dir;
    }

    private void setSteps() {
        switch (getDir()) {
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

    public int getSteps() { return steps; }

    public String toString() {
        return dir.toString();
    }
}

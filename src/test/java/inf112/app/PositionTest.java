package inf112.app;

import inf112.app.player.Direction;
import inf112.app.player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionTest {

    private Position pos;
    private int x;
    private int y;
    private Direction dirN;

    @Before
    public void init() {
        x = 1;
        y = 2;
        dirN = Direction.NORTH;
        pos = new Position(x, y);
    }

    @Test
    public void getXTest() {
        assertEquals(pos.getX(), 1);
    }

    @Test
    public void getYTest() {
        assertEquals(pos.getY(), 2);
    }

    @Test
    public void getNextPosTest() {
        Position northPos = new Position(1, 3);
        assertEquals(pos.getNextPos(dirN), northPos);
    }



}

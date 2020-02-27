package inf112.app;

import inf112.app.player.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DirectionsTest {
    private Direction dirN;
    private Direction dirS;
    private Direction dirE;
    private Direction dirW;

    public DirectionsTest() {
        dirN = Direction.NORTH;
        dirS = Direction.SOUTH;
        dirE = Direction.EAST;
        dirW = Direction.WEST;
    }


    @Test
    public void checkPositionWest() {
        assertEquals(dirW.getName(), "W");
    }
    @Test
    public void checkPositionEast() {
        assertEquals(dirE.getName(), "E");
    }
    @Test
    public void checkPositionNort() {
        assertEquals(dirN.getName(), "N");
    }
    @Test
    public void checkPositionSouth() {
        assertEquals(dirS.getName(), "S");
    }

    @Test
    public void checkPositionReverseNorth() {
        assertEquals(dirN.reverseDirection(), Direction.SOUTH);
    }
    @Test
    public void checkPositionReverseSouth() {
        assertEquals(dirS.reverseDirection(), Direction.NORTH);
    }
    @Test
    public void checkPositionReverseEast() {
        assertEquals(dirE.reverseDirection(), Direction.WEST);
    }
    @Test
    public void checkPositionReverseWest() {
        assertEquals(dirW.reverseDirection(), Direction.EAST);
    }


}

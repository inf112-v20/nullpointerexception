package inf112.app;

import inf112.app.player.Direction;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DirectionsTest {
    private Direction dirN;
    private Direction dirS;
    private Direction dirE;
    private Direction dirW;
    private Direction dirNone;

    /**
     * Testing Direction enum
     *
     * @param
     */

    public DirectionsTest() {
        dirN = Direction.NORTH;
        dirS = Direction.SOUTH;
        dirE = Direction.EAST;
        dirW = Direction.WEST;
    }

    /**
     * Testing getName for all directions
     *
     * @param
     */
    @Test
    public void checkDirectionWest() {
        assertEquals(dirW.getName(), "W");
    }

    @Test
    public void checkDirectionEast() {
        assertEquals(dirE.getName(), "E");
    }

    @Test
    public void checkDirectionNorth() {
        assertEquals(dirN.getName(), "N");
    }

    @Test
    public void checkDirectionSouth() {
        assertEquals(dirS.getName(), "S");
    }

    /**
     * Testing reverseDirection for all directions
     *
     * @param
     */

    @Test
    public void checkDirectionReverseNorth() {
        assertEquals(dirN.reverseDirection(), Direction.SOUTH);
    }

    @Test
    public void checkDirectionReverseSouth() {
        assertEquals(dirS.reverseDirection(), Direction.NORTH);
    }

    @Test
    public void checkDirectionReverseEast() {
        assertEquals(dirE.reverseDirection(), Direction.WEST);
    }

    @Test
    public void checkDirectionReverseWest() {
        assertEquals(dirW.reverseDirection(), Direction.EAST);
    }

    /**
     * Testing turnLeft for all directions
     *
     * @param
     */
    @Test
    public void checkDirectionTurnLeftNORTH() {
        assertEquals(dirN.turnLeft(), Direction.WEST);
    }

    @Test
    public void checkDirectionTurnLeftSOUTH() {
        assertEquals(dirS.turnLeft(), Direction.EAST);
    }

    @Test
    public void checkDirectionTurnLeftEAST() {
        assertEquals(dirE.turnLeft(), Direction.NORTH);
    }

    @Test
    public void checkDirectionTurnLeftWEST() {
        assertEquals(dirW.turnLeft(), Direction.SOUTH);
    }

    /**
     * Testing turnRight for all directions
     *
     * @param
     */
    @Test
    public void checkDirectionTurnRightNORTH() {
        assertEquals(dirN.turnRight(), Direction.EAST);
    }

    @Test
    public void checkDirectionTurnRightSOUTH() {
        assertEquals(dirS.turnRight(), Direction.WEST);
    }

    @Test
    public void checkDirectionTurnRightEAST() {
        assertEquals(dirE.turnRight(), Direction.SOUTH);
    }

    @Test
    public void checkDirectionTurnRightWEST() {
        assertEquals(dirW.turnRight(), Direction.NORTH);
    }
}

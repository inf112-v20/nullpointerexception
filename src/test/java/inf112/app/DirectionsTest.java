package inf112.app;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DirectionsTest {

    @Test
    public void checkPositionNull() {
        assertEquals(Directions.getName(null), "");
    }
    @Test
    public void checkPositionWest() {
        assertEquals(Directions.getName(Directions.WEST), "W");
    }
    @Test
    public void checkPositionEast() {
        assertEquals(Directions.getName(Directions.EAST), "E");
    }
    @Test
    public void checkPositionNort() {
        assertEquals(Directions.getName(Directions.NORTH), "N");
    }
    @Test
    public void checkPositionSouth() {
        assertEquals(Directions.getName(Directions.SOUTH), "S");
    }
    @Test
    public void checkPositionReverseNull() {
        assertEquals(Directions.reverse(null), null);
    }
    @Test
    public void checkPositionReverseNorth() {
        assertEquals(Directions.reverse(Directions.NORTH), Directions.SOUTH);
    }
    @Test
    public void checkPositionReverseSouth() {
        assertEquals(Directions.reverse(Directions.SOUTH), Directions.NORTH);
    }
    @Test
    public void checkPositionReverseEast() {
        assertEquals(Directions.reverse(Directions.EAST), Directions.WEST);
    }
    @Test
    public void checkPositionReverseWest() {
        assertEquals(Directions.reverse(Directions.WEST), Directions.EAST);
    }



}

package inf112.app;

import inf112.app.player.Direction;
import inf112.app.player.Player;
import inf112.app.player.Position;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;

    @Before
    public void init() {
        player = new Player(new Position(0, 0), null);
    }

    /**
     * Checks if the players setPos() works correctly
     */
    @Test
    public void setPlayersPositionTest() {
        Position newPosition = new Position(5, 5);
        //Setting players position to (5,5)
        player.setPos(newPosition);
        //position at (5,5)
        Position testPosition = new Position(5, 5);
        assertEquals(player.getPos().getX(), testPosition.getX());
        assertEquals(player.getPos().getY(), testPosition.getY());
    }


    /**
     * Checks if the players setDirection() works correctly
     */
    @Test
    public void setPlayersDirectionTest() {
        player.setDirection(Direction.NORTH);
        Direction north = Direction.NORTH;
        assertEquals(player.getDirection(), north);
    }

    @Test
    public void handleDamageTest() {
        player.handleDamage();
        assertEquals(player.getHitPoints(), 8);
    }

    @Test
    public void repairHitPointsTest() {
        player.repairHitPoints();
        assertEquals(player.getHitPoints(), 9);
    }

    @Test
    public void isDeadTest() {
        assertFalse(player.isDead());
    }

    @Test
    public void loseLifeTest() {
        player.loseLife();
        assertEquals(player.getLifeCount(), 2);
        assertTrue(player.isDead());
    }

    @Test
    public void respawnTest() {
        player.respawn();
        assertFalse(player.isDead());
    }

    @Test
    public void setAndGetSpawnTest() {
        Position newSpawn = new Position(10, 10);
        player.setSpawnPoint(newSpawn);
        player.setPos(player.getSpawnPoint());
        assertEquals(player.getPos(), newSpawn);
    }
}
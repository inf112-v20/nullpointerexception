package inf112.app.player;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.Card;

import java.util.ArrayList;

public interface IActor {

    int MAX_HP = 9;
    int MAX_LIFE = 3;

    /**
     * Subtracts healthscore and lives if healtscore is less than 1. Also sets the player to dead when there are no more lives.
     */
    void handleDamage();

    /**
     * Lets a player gain healthscore if it isn´t already at it´s maximum.
     */
    void repairHitPoints();

    /**
     * Player loses a life and updates isDead if there are no more lives.
     */
    void loseLife();

    /**
     * Returns whether or not the player is dead
     *
     * @return boolean
     */
    boolean isDead();

    /**
     * Changes the position of the player to a predetermined position
     *
     * @return the new position
     */
    Position checkpoint();

    /**
     * Sets the current player state to default
     *
     * @return the new player state
     */
    TiledMapTileLayer.Cell setTexture();

    /**
     * Checks which imagine to show to the screen depending on player state
     */
    void updateTexture();

    /**
     * Gets the current position of the player
     *
     * @return position
     */
    Position getPos();

    /**
     * Changes the position of the player to a new position
     *
     * @param pos the new position
     */
    void setPos(Position pos);

    /**
     * Gets the current direction of the player
     *
     * @return direction
     */
    Direction getDirection();

    /**
     * Changes the direction of the player to a new direction
     *
     * @param dir direction
     */
    void setDirection(Direction dir);

    void setHand();

    Position getSpawnPoint();

    void setSpawnPoint(Position spawnPoint);

    void resetDealtCards();

    ArrayList<Card> getDealtCards();

    int getHitPoints();

    int getLifeCount();
}

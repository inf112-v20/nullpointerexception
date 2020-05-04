package inf112.app.player;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import inf112.app.Card;

import java.util.ArrayList;

public interface IActor {

    int MAX_HP = 9;
    int MAX_LIFE = 3;

    /**
     * Sets the current player state to default
     *
     * @return the new player state
     */
    TiledMapTileLayer.Cell setTexture();

    /**
     * Adds a card to the hand from initial hand.
     */
    void setHand();

    /**
     * Changes the position of the player to a predetermined position
     *
     * @return the new position
     */
    Position checkpoint();

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

    /**
     * @return spawnPoint The spawn point of the player
     */
    Position getSpawnPoint();

    /**
     * Changes the spawn point of the actor
     *
     * @param spawnPoint new spawn
     */
    void setSpawnPoint(Position spawnPoint);

    /**
     * @return The players hit points
     */
    int getHitPoints();

    /**
     * @return lifeCount
     */
    int getLifeCount();


    /**
     * Returns a card in hand given index
     *
     * @param index int
     * @return Card
     */
    Card getCard(int index);

    /**
     * @return initHand An arraylist of cards from dealtCards.
     */
    ArrayList<Card> getDealtCards();

    /**
     * Adds a program card to dealtcards.
     *
     * @param card Card
     */
    void setDealtCards(ArrayList<Card> card);

    /**
     * Resets the initHand
     */
    void resetDealtCards();

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
     * Returns whether or not the player is on a flag
     */
    void isOnFlag(Integer tileID);

    /**
     * Returns whether or not the player is dead
     *
     * @return boolean
     */
    boolean isDead();

    /**
     * Returns if the player have won
     *
     * @return boolean
     */
    boolean win();

    /**
     * @return player sin hand
     */
    ArrayList<Card> getHand();

    /**
     * @return list of cards to discard
     */
    ArrayList<Card> discard();
}

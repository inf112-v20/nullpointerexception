package inf112.app.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.Card;
import inf112.app.Game;

import java.util.ArrayList;


public class Player {

    //Vector holds players position
    private Vector2 player;
    private TiledMapTileLayer.Cell playerCell;
    private Position pos;
    private Direction dir;
    private Game game;
    private ArrayList<Card> hand;
    private int healthScore;
    private int lives;
    private Position spawnPoint;
    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param game game object
     */
    public Player(Game game) {

        this.game = game;
        dir = Direction.SOUTH;
        healthScore = 9;
        lives = 3;
        this.hand = new ArrayList<>();

        Texture robotTextures = new Texture("assets/robot.png");
        TextureRegion[][] robotTexture = TextureRegion.split(robotTextures,
                (int) Game.TILE_SIZE,
                (int) Game.TILE_SIZE);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTexture[0][0]));

        player = new Vector2();
        pos = new Position((int) player.x, (int) player.y);
        spawnPoint = pos;

    }

    /**
     * Constructor mainly for testing purposes
     */
    public Player() {
        dir = Direction.SOUTH;
        player = new Vector2();
        pos = new Position((int) player.x, (int) player.y);
    }

    /**
     * Refreshing the former players position to null
     * Implements the board-movement of a player
     * Prints out the current position
     *
     * @param keycode - an integer representation of different possible inputs
     * @return true/false
     */


    /**
     * subtracts healthscore and lives if healtscore is less than 1. Also sets the player to dead when there are no more lives.
     */
    public void looseHealthScore() {
        if (this.healthScore <= 1) {
            if (this.lives < 1) {
                //updateState(); to dead
            } else {
                this.lives -= 1;
            }
        } else {
            this.healthScore -= 1;
        }
    }

    /**
     * Lets a player gain healthscore if it isn´t already at it´s maximum.
     */
    public void gainHealthScore() {
        if (this.healthScore < 9) {
            this.healthScore += 1;//Usikker på om dette er riktig regel
        }
    }


    /**
     * Changes the position of the player to a new position
     *
     * @param newPos the new position
     */
    public void setPos(Position newPos) {
        pos = new Position(newPos);
    }

    /**
     * Changes the position of the player to a predetermined position
     *
     * @return the new position
     */
    public Position checkpoint() {
        return pos = new Position(0, 0);
    }

    /**
     * Sets the current player state to default
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setImage() { return playerCell; }

    /**
     * Checks which imagine to show to the screen depending on player state
     */
    public void updateState() {
        playerCell.setRotation(dir.getID());
    }

    /**
     * Gets the current position of the player
     *
     * @return position
     */
    public Position getPos() {
        return pos;
    }

    /**
     * Gets the current direction of the player
     *
     * @return direction
     */
    public Direction getDirection() {
        return dir;
    }

    /**
     * Changes the direction of the player to a new direction
     *
     * @param dir direction
     */
    public void setDirection(Direction dir) {
        this.dir = dir;
    }

    /**
     * Adds a card to the hand.
     * @param card
     */
    public void setHand(Card card) { hand.add(card); }

    /**
     * Returns a card in hand given index
     * @param index int
     * @return Card
     */
    public Card getCard(int index) { return hand.get(index); }

    public void setSpawnPoint(Position spawnPoint) {
        this.spawnPoint = spawnPoint;
        this.pos = spawnPoint;
    }

    public Position getSpawnPoint() { return spawnPoint; }
}


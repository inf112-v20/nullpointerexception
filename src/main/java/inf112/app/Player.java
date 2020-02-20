package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;


import java.util.Map;

public class Player extends InputAdapter {

    private TiledMapTileLayer.Cell playerCell;
    private TiledMapTileLayer.Cell playerDiedCell;
    private TiledMapTileLayer.Cell playerWonCell;
    //Vector holds players position
    private Vector2 player;
    private Position pos;
    private Directions direction;
    private Game game;

    private Map<String, TiledMapTileLayer> layers;

    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param layers board object
     * @param game   game object
     */
    public Player(Map<String, TiledMapTileLayer> layers, Game game) {
        this.layers = layers;
        this.game = game;
        direction = Directions.SOUTH;

        Texture playerTexture = new Texture("assets/player.png");
        TextureRegion[][] playerPictures = TextureRegion.split(playerTexture,
                (int) Board.TILE_SIZE,
                (int) Board.TILE_SIZE);

        Texture robotTextures = new Texture("assets/robot.png");
        TextureRegion[][] robotTexture = TextureRegion.split(robotTextures,
                (int) Board.TILE_SIZE,
                (int) Board.TILE_SIZE);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTexture[0][0]));
        playerDiedCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][1]));
        playerWonCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(playerPictures[0][2]));

        player = new Vector2();
        pos = new Position((int) player.x, (int) player.y);

        Gdx.input.setInputProcessor(this);
    }

    /**
     * Refreshing the former players position to null
     * Implements the board-movement of a player
     * Prints out the current position
     *
     * @param keycode - an integer representation of different possible inputs
     * @return true/false
     */
    @Override
    public boolean keyUp(int keycode) {
        layers.get("player").setCell((int) player.x, (int) player.y, null);

        switch(keycode) {
            case Input.Keys.RIGHT:
                if (direction != Directions.EAST)
                    direction = Directions.EAST;
                else if (game.outOfMap(getX() + 1, getY()) && game.canMove(getX() + 1, getY(), direction))
                    player.setZero();
                else if (game.canMove(getX() + 1, getY(), direction) && direction == Directions.EAST)
                    player.x += 1;
                break;
            case Input.Keys.LEFT:
                if (direction != Directions.WEST)
                    direction = Directions.WEST;
                else if (game.outOfMap(getX() - 1, getY()) && game.canMove(getX() - 1, getY(), direction))
                    player.setZero();
                else if (game.canMove(getX() - 1, getY(), direction) && direction == Directions.WEST)
                    player.x -= 1;
                break;

        }

        if (keycode == Input.Keys.UP)
            if (direction != Directions.NORTH)
                direction = Directions.NORTH;
            else if (game.outOfMap(getX(), getY() + 1) && game.canMove(getX(), getY() + 1, direction))
                player.setZero();
            else if (game.canMove(getX(), getY() + 1, direction))
                player.y += 1;


        if (keycode == Input.Keys.DOWN) {
            if (direction != Directions.SOUTH)
                direction = Directions.SOUTH;
            else if (game.outOfMap(getX(), getY() -1) && game.canMove(getX(), getY() - 1, direction))
                player.setZero();
            else if (game.canMove(getX(), getY() - 1, direction) && direction == Directions.SOUTH)
                player.y -= 1;

        }
        return super.keyDown(keycode);
    }

    /**
     * Sets the current player state to dead
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setPlayerToDead() {
        return playerDiedCell;
    }

    /**
     * Sets the current player state to won
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setPlayerToWon() {
        return playerWonCell;
    }

    /**
     * Sets the current player state to default
     *
     * @return the new player state
     */
    public TiledMapTileLayer.Cell setPlayerToDefault() {
        return playerCell;
    }

    /**
     * Returns the x position on the grid for the player
     *
     * @return x value
     */
    public int getX() {
        return (int) player.x;
    }

    /**
     * Returns the y position on the grid for the player
     *
     * @return y value
     */
    public int getY() {
        return (int) player.y;
    }

    /**
     * Checks which imagine to show to the screen depending on player state
     */
    public void checkPlayerState() {
        //If a player is on the position of the flag, change the image of a player to winning-state
        if (layers.get("flag").getCell(getX(), getY()) != null)
            layers.get("player").setCell(getX(), getY(), setPlayerToWon());
            //If a player stands on the hole-cell, change the image to dying-state
        else if (layers.get("hole").getCell(getX(), getY()) != null)
            layers.get("player").setCell(getX(), getY(), setPlayerToDead());
        else
            layers.get("player").setCell(getX(), getY(), setPlayerToDefault());

        checkPlayerDirection();
    }

    /**
     * Checks which direction the player is facing, and rotates the texture accordingly
     */
    private void checkPlayerDirection() {
        if (direction == Directions.NORTH) {
            playerCell.setRotation(2);
            playerDiedCell.setRotation(2);
            playerWonCell.setRotation(2);
        }
        if (direction == Directions.EAST) {
            playerCell.setRotation(1);
            playerDiedCell.setRotation(1);
            playerWonCell.setRotation(1);
        }
        if (direction == Directions.SOUTH) {
            playerCell.setRotation(0);
            playerDiedCell.setRotation(0);
            playerWonCell.setRotation(0);
        }
        if (direction == Directions.WEST) {
            playerCell.setRotation(3);
            playerDiedCell.setRotation(3);
            playerWonCell.setRotation(3);
        }
    }
}

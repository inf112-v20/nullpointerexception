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
    //Vector holds players position
    private Vector2 player;
    private Position pos;
    private Directions dir;
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
        dir = Directions.SOUTH;

        Texture robotTextures = new Texture("assets/robot.png");
        TextureRegion[][] robotTexture = TextureRegion.split(robotTextures,
                (int) Board.TILE_SIZE,
                (int) Board.TILE_SIZE);

        playerCell = new TiledMapTileLayer.Cell().setTile(new StaticTiledMapTile(robotTexture[0][0]));

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
        layers.get("player").setCell(pos.getX(), pos.getY(), null);
/*
        switch(keycode) {
            case Input.Keys.RIGHT:
                if (direction != Directions.EAST)
                    direction = Directions.EAST;
                else if (game.outOfBoard(pos.getNextPos(direction)) && game.canMove(pos.getNextPos(direction), direction))
                    checkpoint();
                else if (game.canMove(pos.getNextPos(direction), direction))
                    pos = new Position(pos.getNextPos(direction));
                break;
            case Input.Keys.LEFT:
                if (direction != Directions.WEST)
                    direction = Directions.WEST;
                else if (game.outOfBoard(pos.getNextPos(direction)) && game.canMove(pos.getNextPos(direction), direction))
                    checkpoint();
                else if (game.canMove(pos.getNextPos(direction), direction))
                    pos = new Position(pos.getNextPos(direction));
                break;
            case Input.Keys.UP:
                if (direction != Directions.NORTH)
                    direction = Directions.NORTH;
                else if (game.outOfBoard(pos.getNextPos(direction)) && game.canMove(pos.getNextPos(direction), direction))
                    checkpoint();
                else if (game.canMove(pos.getNextPos(direction), direction))
                    pos = new Position(pos.getNextPos(direction));
                break;
            case Input.Keys.DOWN:
                if (direction != Directions.SOUTH)
                    direction = Directions.SOUTH;
                else if (game.outOfBoard(pos.getNextPos(direction)) && game.canMove(pos.getNextPos(direction), direction))
                    checkpoint();
                else if (game.canMove(pos.getNextPos(direction), direction))
                    pos = new Position(pos.getNextPos(direction));
                game.movePlayer();
                break;*/

        switch (keycode) {
            case Input.Keys.RIGHT:
                if (dir != Directions.EAST)
                    dir = Directions.EAST;
                else
                    game.movePlayer(pos);
                break;
            case Input.Keys.LEFT:
                if (dir != Directions.WEST)
                    dir = Directions.WEST;
                else
                    game.movePlayer(pos);
                break;
            case Input.Keys.UP:
                if (dir != Directions.NORTH)
                    dir = Directions.NORTH;
                else
                    game.movePlayer(pos);
                break;
            case Input.Keys.DOWN:
                if (dir != Directions.SOUTH)
                    dir = Directions.SOUTH;
                else
                    game.movePlayer(pos);
                break;
        }

        game.checkCurrentTile(pos);
        updatePlayerState();
        return super.keyDown(keycode);
    }

    public void movePlayer(Position newPos) {
        pos = new Position(newPos);
    }

    public Position checkpoint() {
        return pos = new Position(0, 0);
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
     * Checks which imagine to show to the screen depending on player state
     */
    public void updatePlayerState() {
        layers.get("player").setCell(pos.getX(), pos.getY(), setPlayerToDefault());
        playerCell.setRotation(dir.getID());
    }

    public Position getPlayerPos() {
        return pos;
    }

    public Directions getDirection() {
        return dir;
    }
}

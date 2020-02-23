package inf112.app.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.math.Vector2;
import inf112.app.Game;



public class Player extends InputAdapter {

    //Vector holds players position
    private Vector2 player;
    private TiledMapTileLayer.Cell playerCell;
    private Position pos;
    private Direction dir;
    private Game game;

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

        Texture robotTextures = new Texture("assets/robot.png");
        TextureRegion[][] robotTexture = TextureRegion.split(robotTextures,
                (int) Game.TILE_SIZE,
                (int) Game.TILE_SIZE);

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

        switch (keycode) {
            case Input.Keys.RIGHT:
                if (dir != Direction.EAST)
                    dir = Direction.EAST;
                else
                    game.movePlayer(pos, dir);
                break;
            case Input.Keys.LEFT:
                if (dir != Direction.WEST)
                    dir = Direction.WEST;
                else
                    game.movePlayer(pos, dir);
                break;
            case Input.Keys.UP:
                if (dir != Direction.NORTH)
                    dir = Direction.NORTH;
                else
                    game.movePlayer(pos, dir);
                break;
            case Input.Keys.DOWN:
                if (dir != Direction.SOUTH)
                    dir = Direction.SOUTH;
                else
                    game.movePlayer(pos, dir);
                break;
            case Input.Keys.Q:
                game.checkCurrentTile(pos);
        }

        return super.keyDown(keycode);
    }

    public void setPos(Position newPos) {
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
        playerCell.setRotation(dir.getID());
    }

    public Position getPlayerPos() {
        return pos;
    }

    public Direction getDirection() {
        return dir;
    }

    public void setDirection(Direction turn) {
        dir = turn;
    }
}

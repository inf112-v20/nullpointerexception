package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Game extends ScreenAdapter {
    private Board board;
    private Player player;

    private OrthogonalTiledMapRenderer renderer;

    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game(){
        board = new Board();
        player = new Player(board.getBoardLayers(), this);

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false,
                board.getBoardWidth() * Board.TILE_SIZE,
                board.getBoardHeight() * Board.TILE_SIZE);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(board.getBoard());
        renderer.setView(camera);
    }

    /**
     * A loop method which renders the changes on the screen
     * Shows the player default/winning/dying state on the board
    */
     @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

        player.checkPlayerState();

        renderer.render();
    }

    /**
     * Called when the Application is destroyed. Preceded by a call to pause().
     */
    @Override
    public void dispose() {
        renderer.dispose();
    }

    /**
     * Checks if there is a wall in the current tile or the next tile
     * which the player is trying to move into.
     *
     * @param newX x coordinate player is trying to move towards
     * @param newY y coordinate player is trying to move towards
     * @param dir  direction the player is facing/wanting to move towards
     * @return boolean true or false if the tile has a wall
     */
    private boolean hasWall(int newX, int newY, Directions dir) {
        //Checks if there is a wall in the direction the player is trying to move
        if (board.getBoardLayers().get("wall" + Directions.getName(dir)).getCell(player.getX(), player.getY()) != null)
            return true;
        //Checks if there is a wall in the next tile, from the direction the player is coming from
        return board.getBoardLayers().get("wall" + Directions.getName(Directions.reverse(dir))).getCell(newX, newY) != null;
    }

    /**
     * Checks if the player can move in a certain direction
     *
     * @param newX x coordinate player is trying to move towards
     * @param newY y coordinate player is trying to move towards
     * @param dir  direction the player is facing/wanting to move towards
     * @return boolean true or false if player can move
     */
    public boolean canMove(int newX, int newY, Directions dir) {
        if (hasWall(newX, newY, dir))
            return false;
        if (newX >= board.getBoardWidth() || newX < 0)
            return false;
        return newY < board.getBoardHeight() && newY >= 0;
    }

}
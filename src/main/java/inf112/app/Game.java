package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.app.Board.Board;
import inf112.app.Board.BoardObjects;
import inf112.app.Board.Player.Directions;
import inf112.app.Board.Player.Player;
import inf112.app.Board.Player.Position;
import inf112.app.Board.Tiles.Turnwheel;

public class Game extends ScreenAdapter {
    private Board board;
    private Player player;
    private BoardObjects boardObjects;

    private OrthogonalTiledMapRenderer renderer;

    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game() {
        board = new Board();
        boardObjects = new BoardObjects(board.getBoardLayers(), this);
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

        player.updatePlayerState();

        renderer.render();
    }

    /**
     * Called when the Application is destroyed. Preceded by a call to pause().
     */
    @Override
    public void dispose() {
        renderer.dispose();
    }

    public boolean outOfBoard(Position newPos) {
        if (newPos.getX() < 0 || newPos.getX() >= board.getBoardWidth())
            return true;
        return newPos.getY() < 0 || newPos.getY() >= board.getBoardHeight();
    }

    public boolean canMove(Position newPos, Directions direction) {
        return !boardObjects.tileHasWall(player.getPlayerPos(), newPos, direction);
    }

    public void movePlayer(Position pos) {
        if (!canMove(pos.getNextPos(player.getDirection()), player.getDirection())) {
            System.out.println("Something is blocking");
            return;
        }
        if (outOfBoard(player.getPlayerPos().getNextPos(player.getDirection())))
            player.checkpoint();
        else
            player.movePlayer(player.getPlayerPos().getNextPos(player.getDirection()));
        //System.out.println("Player moved");
    }

    public void checkCurrentTile(Position pos) {
        if (boardObjects.tileHasFlag(pos)) {
            System.out.println("Player is standing on a flag!");
        }
        if (boardObjects.tileHasHole(pos)) {
            player.checkpoint();
            System.out.println("Player stepped in a hole!");
        }
        if (boardObjects.tileHasTrack(pos, player.getDirection())) {
//            Conveyor conveyor = new Conveyor(board.getBoardLayers().get("conveyorbelt").getCell(pos.getX(), pos.getY()));

            System.out.println("Player is standing on a track!");
        }
        if (boardObjects.tileHasTurnWheel(pos)) {
            Turnwheel turnwheel = new Turnwheel(board.getBoardLayers().get("turnwheel").getCell(pos.getX(), pos.getY()));
            if (turnwheel.getID() == 53)
                player.setDirection(Directions.turnLeft(player.getDirection()));
            else
                player.setDirection(Directions.turnRight(player.getDirection()));
            System.out.println("Player was turned by a turnwheel");
        }

        if (boardObjects.tileHasLaser(pos)) {
            System.out.println("Player is standing on a laser!");
        }
        if (boardObjects.tileHasRepair(pos)) {
            System.out.println("Player is standing on a repair kit!");
        }
    }
}
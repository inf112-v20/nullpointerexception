package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import inf112.app.board.Board;
import inf112.app.board.BoardObjects;
import inf112.app.player.Direction;
import inf112.app.player.Player;
import inf112.app.player.Position;

public class Game extends ScreenAdapter {
    public static final float TILE_SIZE = 300;

    private Board board;
    private Player player;
    private BoardObjects boardObjects;


    private OrthogonalTiledMapRenderer renderer;


    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game() {
        //String boardName1 = "Risky_Exchange.tmx";
        String boardName2 = "Whirlwind Tour.tmx";

        board = new Board(boardName2);
        boardObjects = new BoardObjects(board.getBoardLayers(), this);
        player = new Player(this);
        updatePlayer();

        OrthographicCamera camera = new OrthographicCamera();
        camera.setToOrtho(false,
                board.getBoardWidth() * TILE_SIZE,
                board.getBoardHeight() * TILE_SIZE);
        camera.update();

        renderer = new OrthogonalTiledMapRenderer(board.getBoard());
        renderer.setView(camera);
    }

    public void updatePlayer() {
        board.getBoardLayers()
                .get("player")
                .setCell(player.getPos().getX(), player.getPos().getY(), player.setImage());
    }

    /**
     * A loop method which renders the changes on the screen
     * Shows the player default/winning/dying state on the board
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);

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
        if (newPos.getX() < 0 || newPos.getX() >= board.getBoardWidth()) {
            System.out.println("player moved out of the board");
            return true;
        }

        //if ((board.getBoardLayers().get("hole").getCell(newPos.getX(), newPos.getX()) != null)) {return true;}

        return newPos.getY() < 0 || newPos.getY() >= board.getBoardHeight();
    }

    public boolean canMove(Position newPos, Direction direction) {
        return !boardObjects.tileHasWall(player.getPos(), newPos, direction);
    }

    public Position movePlayer(Position pos, Direction dir) {
        board.getBoardLayers().get("player").setCell(pos.getX(), pos.getY(), null);

        if (!canMove(pos.getNextPos(dir), dir)) {
            System.out.println("Something is blocking");
        } else if (outOfBoard(pos.getNextPos(dir)))
            resetPlayer(pos);
        else
            player.setPos(pos.getNextPos(dir));
        updatePlayer();
        player.updateState();

        return player.getPos();
    }

    public Position getPlayerPos() {
        return player.getPos();
    }

    public void turnPlayer(Direction dir) {
        player.setDirection(dir);
        player.updateState();
    }

    public void resetPlayer(Position pos) {
        board.getBoardLayers().get("player").setCell(pos.getX(), pos.getY(), null);
        player.checkpoint();
        updatePlayer();
    }

    public void checkCurrentTile(Position pos) {
        if (boardObjects.tileHasFlag(pos)) {
            System.out.println("player is standing on a flag!");
        }
        if (boardObjects.tileHasHole(pos)) {
            System.out.println("player stepped in a hole!");
        }
        if (boardObjects.tileHasConveyor(pos, player.getDirection())) {
            System.out.println("PLayer was moved by a conveyorbelt");
        }
        if (boardObjects.tileHasTurnWheel(pos, player.getDirection())) {
            System.out.println("player was turned by a turnwheel");
        }
        if (boardObjects.tileHasLaser(pos)) {
            System.out.println("player is standing on a laser!");
        }
        if (boardObjects.tileHasRepair(pos)) {
            System.out.println("player is standing on a repair kit!");
        }

    }
}
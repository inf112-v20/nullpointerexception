package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import inf112.app.board.Board;
import inf112.app.board.BoardObjects;
import inf112.app.player.Direction;
import inf112.app.player.Player;
import inf112.app.player.Position;

public class Game extends InputAdapter {
    public static final float TILE_SIZE = 300;

    private Board board;
    private Player player;
    private BoardObjects boardObjects;


    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game() {
        //String boardName = "boards/Risky_Exchange.tmx";
        String boardName = "boards/Whirlwind Tour.tmx";

        board = new Board(boardName);
        boardObjects = new BoardObjects(board.getBoardLayers(), this);
        player = new Player(this);
        updatePlayer();

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
                if (player.getDirection() != Direction.EAST)
                    player.setDirection(Direction.EAST);
                else
                    movePlayer(player.getPos(), player.getDirection());
                break;
            case Input.Keys.LEFT:
                if (player.getDirection() != Direction.WEST)
                    player.setDirection(Direction.WEST);
                else
                    movePlayer(player.getPos(), player.getDirection());
                break;
            case Input.Keys.UP:
                if (player.getDirection() != Direction.NORTH)
                    player.setDirection(Direction.NORTH);
                else
                    movePlayer(player.getPos(), player.getDirection());
                break;
            case Input.Keys.DOWN:
                if (player.getDirection() != Direction.SOUTH)
                    player.setDirection(Direction.SOUTH);
                else
                    movePlayer(player.getPos(), player.getDirection());
                break;
            case Input.Keys.Q:
                checkCurrentTile(player);
                break;
            default:
        }
        player.updateState();
        return super.keyDown(keycode);
    }

    /**
     * Puts the player imagine in a cell. Updates everytime a player moves or changes direction
     */
    public void updatePlayer() {
        board.getBoardLayers()
                .get("player")
                .setCell(player.getPos().getX(), player.getPos().getY(), player.setImage());
    }


    /**
     * Checks if the position the player wants to move to is valid
     *
     * @param newPos the position player wants to move to
     * @return boolean true or false
     */
    public boolean outOfBoard(Position newPos) {
        if (newPos.getX() < 0 || newPos.getX() >= board.getBoardWidth()) {
            System.out.println("player moved out of the board");
            return true;
        }
        if (newPos.getY() < 0 || newPos.getY() >= board.getBoardHeight()) {
            System.out.println("player moved out of the board");
            return true;
        }
        return false;
    }

    /**
     * Checks if the player is blocked by something or can move
     *
     * @param newPos    the position the player will have if he moves
     * @param direction direction of the player
     * @return true if player can move
     */
    public boolean canMove(Position newPos, Direction direction) {
        return !boardObjects.tileHasWall(player.getPos(), newPos, direction);
    }

    /**
     * Moves player in the direction given if the player is not blocked. Resets player if player is out of board
     *
     * @param pos position of the player
     * @param dir direction to move towards
     * @return the new position of the player
     */
    public Position movePlayer(Position pos, Direction dir) {
        board.getBoardLayers().get("player").setCell(pos.getX(), pos.getY(), null);

        if (!canMove(pos.getNextPos(dir), dir)) {
            System.out.println("Something is blocking");
        } else if (outOfBoard(pos.getNextPos(dir)))
            resetPlayer();
        else
            player.setPos(pos.getNextPos(dir));
        updatePlayer();
        player.updateState();

        return player.getPos();
    }

    /**
     * Returns player position
     * @return position of the player
     */
    public Position getPlayerPos() {
        return player.getPos();
    }

    /**
     * Sets a new player direction
     * @param dir direction
     */
    public void turnPlayer(Direction dir) {
        player.setDirection(dir);
        player.updateState();
    }

    /**
     * sets the player to a new position
     */
    public void resetPlayer() {
        board.getBoardLayers().get("player").setCell(player.getPos().getX(), player.getPos().getY(), null);
        player.checkpoint();
        updatePlayer();
    }

    /**
     * Checks what objects is on the player tile.
     * @param player player object
     */
    public void checkCurrentTile(Player player) {
        if (boardObjects.tileHasFlag(player.getPos())) {
            System.out.println("player is standing on a flag!");
        }
        if (boardObjects.tileHasHole(player.getPos())) {
            resetPlayer();
            System.out.println("player stepped in a hole!");
        }
        if (boardObjects.hasConveyor(player.getPos())) {
            conveyor();
            System.out.println("PLayer was moved by a conveyorbelt");
        }
        if (boardObjects.tileHasTurnWheel(player.getPos(), player.getDirection())) {
            System.out.println("player was turned by a turnwheel");
        }
        if (boardObjects.tileHasLaser(player.getPos())) {
            System.out.println("player is standing on a laser!");
        }
        if (boardObjects.tileHasRepair(player.getPos())) {
            System.out.println("player is standing on a repair kit!");
        }
    }

    /**
     * Moves the player if he is standing on a conveyor tile
     */
    private void conveyor() {
        Direction conveyorDir = boardObjects.conveyorDirection(player.getPos());

        if (outOfBoard(getPlayerPos().getNextPos(conveyorDir))) {
            resetPlayer();
            return;
        }

        movePlayer(player.getPos(), conveyorDir);
        conveyorTurn(conveyorDir);

        if (boardObjects.hasExpressConveyor(player.getPos())) {
            conveyorExpress();
        }

    }

    /**
     * Moves the player one additional time if the player is on an express type conveyor
     */
    private void conveyorExpress() {
        Direction conveyorDir = boardObjects.conveyorDirection(player.getPos());

        if (outOfBoard(getPlayerPos().getNextPos(conveyorDir))) {
            resetPlayer();
            return;
        }
        movePlayer(player.getPos(), conveyorDir);
        conveyorTurn(conveyorDir);
    }

    /**
     * If the conveyor moves a player it also turns the player if he is moved into a turn
     *
     * @param oldDirection the previous direction of the conveyor
     */
    private void conveyorTurn(Direction oldDirection) {
        Direction conveyorDir = boardObjects.conveyorDirection(player.getPos());

        if (conveyorDir != oldDirection) {
            if (oldDirection.turnLeft() == conveyorDir)
                turnPlayer(player.getDirection().turnLeft());
            else
                turnPlayer(player.getDirection().turnRight());
        }
    }

    public Board getBoard() {
        return board;
    }
}
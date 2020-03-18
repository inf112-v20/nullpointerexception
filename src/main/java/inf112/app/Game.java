package inf112.app;

import inf112.app.board.Board;
import inf112.app.board.BoardObjects;
import inf112.app.player.Direction;
import inf112.app.player.Player;
import inf112.app.player.Position;

public class Game {
    public static final float TILE_SIZE = 300;

    private Board board;
    private Player player;
    private BoardObjects boardObjects;
    private int turn;


    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game() {
        //String boardName = "boards/Risky_Exchange.tmx";
        String boardName = "boards/Whirlwind Tour.tmx";
        Deck deck = new Deck();
        turn = 0;
        board = new Board(boardName);
        boardObjects = new BoardObjects(board.getBoardLayers(), this);
        player = new Player(this);
        updatePlayer();
        for (int i = 0; i < 5; i++) {
            player.setHand(deck.dealCard());
        }
        Input input = new Input(player, this);


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
            player.looseHealthScore();
            return true;
        }
        if (newPos.getY() < 0 || newPos.getY() >= board.getBoardHeight()) {
            System.out.println("player moved out of the board");
            player.looseHealthScore();
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

    public void tryToMove() {
        Card card = new Card(100, CardDirection.BACKUP);
        Position pos = player.getPos();
        Direction dir = player.getDirection();
        if (card.getSteps() == 0) {
            movePlayer2(dir, pos, card.getDir());
        } else {
            for (int i = 0; i < card.getSteps(); i++) {
                pos = player.getPos();
                dir = player.getDirection();
                if (!canMove(pos.getNextPos(dir), dir)) {
                    System.out.println("Something is blocking!");
                    break;
                } else if (outOfBoard(pos.getNextPos(dir))) {
                    resetPlayer();
                    System.out.println("Player moved out of the board!");
                    break;
                } else
                    movePlayer(pos, dir);
            }
        }
    }

    public void movePlayer2(Direction dir, Position pos, CardDirection cardDir) {
        board.getBoardLayers().get("player").setCell(pos.getX(), pos.getY(), null);
        switch (cardDir) {
            case BACKUP:
                player.setSpawnPoint(pos);
                break;
            case TURN180:
                turnPlayer(dir.reverseDirection());
                break;
            case TURNLEFT:
                turnPlayer(dir.turnLeft());
                break;
            case TURNRIGHT:
                turnPlayer(dir.turnRight());
                break;
            default:
                player.setPos(pos.getNextPos(dir));
                break;


        }
        updatePlayer();
        player.updateState();
        turn = (turn + 1) % 5;


    }

    /**
     * Returns player position
     *
     * @return position of the player
     */
    public Position getPlayerPos() {
        return player.getPos();
    }

    /**
     * Sets a new player direction
     *
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
        board.getBoardLayers().get("player").setCell(player.getSpawnPoint().getX(), player.getSpawnPoint().getY(), null);
        player.setSpawnPoint(player.getSpawnPoint());
        updatePlayer();
    }

    /**
     * Checks what objects is on the player tile.
     *
     * @param player player object
     */
    public void checkCurrentTile(Player player) {
        if (boardObjects.tileHasHole(player.getPos())) {
            resetPlayer();
            System.out.println("player stepped in a hole!");
            return;
        }
        if (boardObjects.tileHasFlag(player.getPos())) {
            System.out.println("player is standing on a flag!");
        }
        if (boardObjects.hasConveyor(player.getPos())) {
            conveyor();
            if (boardObjects.hasExpressConveyor(player.getPos())) {
                conveyor();
            }
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
    }


    /**
     * If the conveyor moves a player it also turns the player if he is moved into a turn
     *
     * @param oldDirection the previous direction of the conveyor
     */
    private void conveyorTurn(Direction oldDirection) {
        if (!boardObjects.hasConveyor(player.getPos()))
            return;

        Direction conveyorDir = boardObjects.conveyorDirection(player.getPos());

        if (boardObjects.tileHasFlag(player.getPos()))
            return;

        if (!conveyorDir.equals(oldDirection)) {
            if (oldDirection.turnLeft().equals(conveyorDir))
                turnPlayer(player.getDirection().turnLeft());
            else
                turnPlayer(player.getDirection().turnRight());
        }
    }

    public Board getBoard() {
        return board;
    }

}
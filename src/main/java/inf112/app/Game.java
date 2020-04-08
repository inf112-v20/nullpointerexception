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

    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game() {
        //String boardName = "boards/Risky_Exchange.tmx";
        String boardName = "boards/Whirlwind Tour.tmx";
        Deck deck = new Deck();
        board = new Board(boardName);
        boardObjects = new BoardObjects(board.getBoardLayers(), this);
        player = new Player();
        updatePlayer();
        player.setDealtCards(deck.dealCards(Math.min(9, player.getHitPoints())));
        player.setHand();
        deck.setDiscardPile(player.getDealtCards());
        player.resetDealtCards();
        new Input(player, this);
    }

    /**
     * Puts the player imagine in a cell. Updates everytime a player moves or changes direction
     */
    private void updatePlayer() {
        board.getBoardLayers()
                .get("player")
                .setCell(player.getPos().getX(), player.getPos().getY(), player.setPlayerTexture());
    }


    /**
     * Checks if the position the player wants to move to is valid
     *
     * @param newPos the position player wants to move to
     * @return boolean true or false
     */
    private boolean outOfBoard(Position newPos) {
        if (newPos.getX() < 0 || newPos.getX() >= board.getBoardWidth()) {
            System.out.println("player moved out of the board");
            player.loseLife();
            return true;
        }
        if (newPos.getY() < 0 || newPos.getY() >= board.getBoardHeight()) {
            System.out.println("player moved out of the board");
            player.loseLife();
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
    private boolean canNotMove(Position newPos, Direction direction) {
        return boardObjects.tileHasWall(player.getPos(), newPos, direction);
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

        if (canNotMove(pos.getNextPos(dir), dir)) {
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
        Card card = new Card(100, CardType.BACKUP);
        /*
        Card card = player.getCard(0);
        deck.setDiscardPile(card);
        */
        Position pos = player.getPos();
        Direction dir = player.getDirection();

        if (card.getSteps() == 0) {
            movePlayer2(dir, pos, card.getType());
        } else if (card.getType() == CardType.BACKUP) {
            movePlayer(pos, dir.reverseDirection());
        }
        else {
            for (int i = 0; i < card.getSteps(); i++) {
                pos = player.getPos();
                dir = player.getDirection();
                if (canNotMove(pos.getNextPos(dir), dir)) {
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

    private void movePlayer2(Direction dir, Position pos, CardType cardType) {
        board.getBoardLayers().get("player").setCell(pos.getX(), pos.getY(), null);
        switch (cardType) {
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


    }

    /**
     * Returns player position
     *
     * @return position of the player
     */
    private Position getPlayerPos() {
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
    private void resetPlayer() {
        board.getBoardLayers().get("player").setCell(player.getPos().getX(), player.getPos().getY(), null);
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
            player.handleDamage();
        }
        if (boardObjects.tileHasRepair(player.getPos())) {
            System.out.println("player is standing on a repair kit!");
            player.repairHitPoints();
        }
        player.printStatus();
        System.out.println();
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
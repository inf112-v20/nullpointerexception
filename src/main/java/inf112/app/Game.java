package inf112.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.app.board.Board;
import inf112.app.board.BoardObjects;
import inf112.app.player.*;

import java.util.*;

public class Game {
    public static final float TILE_SIZE = 300;

    private final Board board;
    private final BoardObjects boardObjects;

    private Deck deck;

    private final Player player;
    private final ArrayList<IActor> actors;

    private List<TextureRegion> robotTextures;
    private ArrayList<Position> spawnPoints;
    private Map<Integer, Position> flags;

    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game() {
        //String boardName = "boards/Risky_Exchange.tmx";
        //String boardName = "boards/Whirlwind Tour.tmx";
        String boardName = "boards/Robot Stew.tmx";

        board = new Board(boardName);
        boardObjects = new BoardObjects(board.getBoardLayers(), this);

        robotTextures();
        spawnPoints();
        flags();

        actors = new ArrayList<>();

        player = new Player(spawnPoints.remove(0), robotTextures.remove(4), flags);
        setActorTexture(player);

        int activeActors = 1;
        for (int i = 0; i < activeActors; i++) {
            actors.add(new Actor(spawnPoints.remove(0), robotTextures.remove(0), flags));
            setActorTexture(actors.get(i));
        }
        //actors.add(player);

        dealCards();
        new Input(player, this);
    }


    /**
     * Creates player textures from an assets file
     */
    private void robotTextures() {
        Texture robotImage = new Texture("assets/robots.png");
        TextureRegion[][] robotTexture = TextureRegion.split(robotImage,
                (int) Game.TILE_SIZE,
                (int) Game.TILE_SIZE);

        robotTextures = new ArrayList<>();

        for (TextureRegion[] textures : robotTexture) {
            robotTextures.addAll(Arrays.asList(textures));
        }
    }

    /**
     * Creates a list of all the initial spawn points actors can have
     */
    private void spawnPoints() {
        spawnPoints = new ArrayList<>();
        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (boardObjects.tileHasSpawn(new Position(x, y)))
                    spawnPoints.add(new Position(x, y));
            }
        }
    }

    private void flags() {
        flags = new HashMap<>();
        for (int x = 0; x < board.getBoardWidth(); x++) {
            for (int y = 0; y < board.getBoardHeight(); y++) {
                if (boardObjects.tileHasFlag(new Position(x, y))) {
                    Integer flagID = board.getBoardLayers().get("flag").getCell(x, y).getTile().getId();
                    flags.put(flagID, new Position(x, y));
                }
            }
        }
    }

    private void dealCards() {
        deck = new Deck();
        player.setDealtCards(deck.dealCards(Math.min(9, player.getHitPoints())));
        //player.setHand();
        //deck.setDiscardPile(player.getDealtCards());
        //player.resetDealtCards();

        for (IActor actor : actors) {
            actor.setDealtCards(deck.dealCards(Math.min(9, actor.getHitPoints())));
            actor.setHand();
            deck.setDiscardPile(actor.getDealtCards());
            actor.resetDealtCards();
        }
    }


    /**
     * Puts the actor imagine in a cell.
     *
     * @param actor actor
     */
    private void setActorTexture(IActor actor) {
        board.getBoardLayers().get("actor").setCell(actor.getPos().getX(), actor.getPos().getY(), actor.setTexture());
    }

    /**
     * Removes the actor imagine in a cell.
     *
     * @param actor actor
     */
    private void removeActorTexture(IActor actor) {
        board.getBoardLayers().get("actor").setCell(actor.getPos().getX(), actor.getPos().getY(), null);
    }

    /**
     * Checks if there is an actor in a certain position, and returns the actor
     *
     * @param position position to check
     * @return actor
     */
    private IActor getActor(Position position) {
        for (IActor actor : actors) {
            if (actor.getPos().equals(position))
                return actor;
        }
        return null;
    }

    /**
     * Checks if the position the player wants to move to is valid
     *
     * @param newPos the position player wants to move to
     * @return boolean true or false
     */
    private boolean outOfBoard(Position newPos) {
        if (boardObjects.tileHasHole(newPos))
            return true;
        if (newPos.getX() < 0 || newPos.getX() >= board.getBoardWidth())
            return true;
        return newPos.getY() < 0 || newPos.getY() >= board.getBoardHeight();
    }

    /**
     * Checks if the player is blocked by something or can move
     *
     * @param newPos the position the player will have if he moves
     * @param actor  direction of the player
     * @return true if player can move
     */
    private boolean canMove(IActor actor, Position newPos, Direction direction) {
        return !boardObjects.tileHasWall(actor.getPos(), newPos, direction);
    }

    /**
     * Moves all the actors
     */
    public void moveActors() {
        for (IActor actor : actors) {
            moveActor(actor, actor.getDirection());
            checkPosition(actor);
            shootLaser(actor.getPos().getNextPos(actor.getDirection()), actor.getDirection());
        }
        shootLaser(player.getPos().getNextPos(player.getDirection()), player.getDirection());
    }

    /**
     * Moves all the actors
     */
    public void moveActorsByCards() {
        for (IActor actor : actors) {
            movedByCard(actor, actor.getCard(0).getType());
            checkPosition(actor);
            shootLaser(actor.getPos().getNextPos(actor.getDirection()), actor.getDirection());
        }

    }

    public void resetActors() {
        for (IActor actor : actors) {
            removeActorTexture(actor);
            actor.setPos(actor.getSpawnPoint());
            setActorTexture(actor);
            shootLaser(actor.getPos().getNextPos(actor.getDirection()), actor.getDirection());
        }
    }

    /**
     * Moves player in the direction given if the player is not blocked. Resets player if player is out of board
     *
     * @return the new position of the player
     */
    public Boolean moveActor(IActor actor, Direction direction) {
        Position newPos = new Position(actor.getPos().getNextPos(direction));

        if (!canMove(actor, newPos, direction)) {
            System.out.println("Something is blocking");
            return false;
        }

        removeActorTexture(actor);

        if (boardObjects.tileHasActor(newPos)) {
            IActor otherActor = getActor(newPos);
            if (otherActor == null)
                return false;
            else if (!moveActor(otherActor, direction)) {
                setActorTexture(actor);
                System.out.println("didnt move");
                return false;
            }
        }

        if (outOfBoard(newPos)) {
            actor.loseLife();
            System.out.println("Actor respawned");
            spawnActor(actor);
        } else
            actor.setPos(newPos);

        setActorTexture(actor);
        return true;
    }

    private void spawnActor(IActor actor) {
        if (getActor(actor.getSpawnPoint()) == null) {
            actor.setPos(actor.getSpawnPoint());
            System.out.println("on spawnpoint");
        } else {
            ArrayList<Position> spawnPositions = new ArrayList<>();
            for (Direction dir : Direction.values()) {
                if (!outOfBoard(actor.getSpawnPoint().getNextPos(dir)) && getActor(actor.getSpawnPoint().getNextPos(dir)) == null) {
                    spawnPositions.add(actor.getSpawnPoint().getNextPos(dir));
                }
            }
            actor.setPos(spawnPositions.get(0));
            System.out.println("Next to spawnpoint");
        }
    }

    /**
     * Shoots lasers from the players and walls
     */
    private void shootLaser(Position position, Direction direction) {
        if (outOfBoard(position))
            return;
        if (getActor(position) != null) {
            IActor actor = getActor(position);
            actor.handleDamage();
            System.out.println("A player got shot.");
            return;
        }
        if (boardObjects.tileHasWall(position, position.getNextPos(direction), direction))
            return;
        shootLaser(position.getNextPos(direction), direction);
    }


    /**
     * Moves an actor depending on its card
     *
     * @param actor    actor
     * @param cardType card type
     */
    public void movedByCard(IActor actor, CardType cardType) {
        removeActorTexture(actor);

        switch (cardType) {
            case MOVE1:
                moveActor(actor, actor.getDirection());
                break;
            case MOVE2:
                moveActor(actor, actor.getDirection());
                moveActor(actor, actor.getDirection());
                break;
            case MOVE3:
                moveActor(actor, actor.getDirection());
                moveActor(actor, actor.getDirection());
                moveActor(actor, actor.getDirection());
                break;
            case TURN180:
                actor.setDirection(actor.getDirection().reverseDirection());
                break;
            case TURNLEFT:
                actor.setDirection(actor.getDirection().turnLeft());
                break;
            case TURNRIGHT:
                actor.setDirection(actor.getDirection().turnRight());
                break;
            default:
                actor.setPos(actor.getPos().getNextPos(actor.getDirection()));
                break;
        }
        setActorTexture(player);
    }


    /**
     * Checks what objects is on the player tile.
     *
     * @param actor actor object
     */
    public void checkPosition(IActor actor) {
        if (boardObjects.tileHasHole(actor.getPos())) {
            actor.setPos(actor.getSpawnPoint());
            System.out.println("player stepped in a hole!");
            return;
        }
        if (boardObjects.tileHasFlag(actor.getPos())) {
            System.out.println("player is standing on a flag!");
        }
        if (boardObjects.hasConveyor(actor.getPos())) {
            conveyor(actor);
            if (boardObjects.hasExpressConveyor(actor.getPos())) {
                conveyor(actor);
            }
            System.out.println("PLayer was moved by a conveyorbelt");
        }
        if (boardObjects.tileHasTurnWheel(actor.getPos(), actor.getDirection())) {
            System.out.println("player was turned by a turnwheel");
            turnWheel(board.getBoardLayers().get("turnwheel").getCell(
                    actor.getPos().getX(), actor.getPos().getY()).getTile().getId() == 53, actor);
        }
        if (boardObjects.tileHasLaser(actor.getPos())) {
            System.out.println("player is standing on a laser!");
            player.handleDamage();
        }
        if (boardObjects.tileHasRepair(actor.getPos())) {
            System.out.println("player is standing on a repair kit!");
            actor.repairHitPoints();
        }
    }

    /**
     * Moves the player if he is standing on a conveyor tile
     *
     * @param actor actor
     */
    private void conveyor(IActor actor) {
        Direction conveyorDir = boardObjects.conveyorDirection(actor.getPos());
        moveActor(actor, conveyorDir);
        conveyorTurn(actor, conveyorDir);
    }


    /**
     * If the conveyor moves a player it also turns the player if he is moved into a turn
     *
     * @param oldDirection the previous direction of the conveyor
     */
    private void conveyorTurn(IActor actor, Direction oldDirection) {
        if (!boardObjects.hasConveyor(actor.getPos()))
            return;

        Direction conveyorDir = boardObjects.conveyorDirection(actor.getPos());

        if (boardObjects.tileHasFlag(actor.getPos()))
            return;

        if (!conveyorDir.equals(oldDirection)) {
            if (oldDirection.turnLeft().equals(conveyorDir))
                actor.setDirection(actor.getDirection().turnLeft());
            else
                actor.setDirection(actor.getDirection().turnRight());
        }
    }

    private void turnWheel(boolean leftTurn, IActor actor) {
        if (leftTurn) {
            actor.setDirection(actor.getDirection().turnLeft());
        }
        else {
            actor.setDirection(actor.getDirection().turnRight());
        }


    }

    public Board getBoard() {
        return board;
    }

    public int getPlayersLifeCount() {
        return player.getLifeCount();
    }
    public int getPlayersHitPoints() {
        return player.getHitPoints();
    }

    public ArrayList<Card> getPlayersDealtCards() {
        return player.getDealtCards();
    }

    public Deck getDeckObject() {
        return deck;
    }
    public Player getPlayerObject(){
        return player;
    }
}
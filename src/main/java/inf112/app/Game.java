package inf112.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.app.board.Board;
import inf112.app.board.BoardObjects;
import inf112.app.cards.Card;
import inf112.app.cards.CardType;
import inf112.app.cards.Deck;
import inf112.app.player.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {
    public static final float TILE_SIZE = 300;

    private final Board board;
    private final BoardObjects boardObjects;

    private final Deck deck;

    private final Player player;
    private final ArrayList<IActor> actors;
    private ArrayList<IActor> deadActors;

    private List<TextureRegion> robotTextures;
    private ArrayList<Position> spawnPoints;

    /**
     * Initializing a board, camera, renderer and player in addition to creating the needed TiledMap layers.
     */
    public Game() {
//        String boardName = "boards/Risky_Exchange.tmx";
//        String boardName = "boards/Whirlwind Tour.tmx";
        String boardName = "boards/Robot Stew.tmx";

        board = new Board(boardName);
        boardObjects = new BoardObjects(board.getBoardLayers());

        robotTextures();
        spawnPoints();

        actors = new ArrayList<>();
        deadActors = new ArrayList<>();

        player = new Player(spawnPoints.remove(1), robotTextures.remove(4));
        setActorTexture(player);

        int activeActors = 7;
        for (int i = 0; i < activeActors; i++) {
            actors.add(new Actor(spawnPoints.remove(0), robotTextures.remove(0)));
            setActorTexture(actors.get(i));
        }
        deck = new Deck();
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


    /**
     * Deals cards to all the players
     */
    public void dealCards() {
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
     *
     * @param i counter
     */
    public void moveActorsByCards(int i) {
        if (!player.isDead())
            actors.add(player);
        sort(actors, i);

        actors.removeAll(deadActors);

        for (IActor actor : actors) {
            moveByCard(actor, actor.getCard(i).getType());
        }

        actors.removeAll(deadActors);

        for (IActor actor : actors) {
            checkPosition(actor);
        }
        for (IActor actor : actors) {
            if (canMove(actor, actor.getPos().getNextPos(actor.getDirection()), actor.getDirection()))
                shootLaser(actor.getPos().getNextPos(actor.getDirection()), actor.getDirection());
        }

        actors.remove(player);
    }

    /**
     * Moves player in the direction given if the player is not blocked. Resets player if player is out of board
     *
     * @return the new position of the player
     */
    public Boolean moveActor(IActor actor, Direction direction) {
        Position newPos = new Position(actor.getPos().getNextPos(direction));

        if (!canMove(actor, newPos, direction) || actor.isDead()) {
            return false;
        }

        removeActorTexture(actor);

        if (boardObjects.tileHasActor(newPos)) {
            IActor otherActor = getActor(newPos);
            if (otherActor == null)
                return false;
            else if (!moveActor(otherActor, direction)) {
                setActorTexture(actor);
                return false;
            }
        }

        if (outOfBoard(newPos) && !deadActors.contains(actor)) {
            killActor(actor);
            return true;
        } else
            actor.setPos(newPos);

        setActorTexture(actor);
        return true;
    }

    /**
     * Moves an actor depending on its card
     *
     * @param actor    actor
     * @param cardType card type
     */
    public void moveByCard(IActor actor, CardType cardType) {
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
            default: // Move Back
                moveActor(actor, actor.getDirection().reverseDirection());
                break;
        }
    }

    public void spawnActors() {
        for (IActor actor : deadActors) {
            spawnActor(actor);
            setActorTexture(actor);
            actors.add(actor);
            actor.respawn();
        }
        deadActors = new ArrayList<>();
        actors.remove(player);
    }

    /**
     * spawns the actor at it's spawnpoint
     *
     * @param actor current actor
     */
    private void spawnActor(IActor actor) {
        if (getActor(actor.getSpawnPoint()) == null) {
            actor.setPos(actor.getSpawnPoint());
        } else {
            ArrayList<Position> spawnPositions = new ArrayList<>();
            for (Direction dir : Direction.values()) {
                if (!outOfBoard(actor.getSpawnPoint().getNextPos(dir)) && getActor(actor.getSpawnPoint().getNextPos(dir)) == null) {
                    spawnPositions.add(actor.getSpawnPoint().getNextPos(dir));
                }
            }
            actor.setPos(spawnPositions.get(0));
        }
    }

    private void killActor(IActor actor) {
        removeActorTexture(actor);
        actor.loseLife();
        actor.setPos(new Position(-1, -1));
        deadActors.add(actor);
    }

    /**
     * takes the discarded cards from player and puts it in the discarpile in Deck.
     */
    public void discard() {
        for (IActor actor : actors) {
            deck.setDiscardPile(actor.discard());
        }
        deck.setDiscardPile(player.discard());
    }

    /**
     * Shoots lasers from the players and walls
     */
    private void shootLaser(Position position, Direction direction) {
        if (outOfBoard(position))
            return;
        if (getActor(position) != null) {
            IActor actor = getActor(position);
            if (actor == null)
                return;
            actor.handleDamage();
            return;
        }
        if (boardObjects.tileHasWall(position, position.getNextPos(direction), direction))
            return;

        shootLaser(position.getNextPos(direction), direction);
    }

    public void laserTest() {
        actors.add(player);
        for (IActor actor : actors) {
            if (canMove(actor, actor.getPos().getNextPos(actor.getDirection()), actor.getDirection()))
                shootLaser(actor.getPos().getNextPos(actor.getDirection()), actor.getDirection());
            if (actor.isDead() && !deadActors.contains(actor))
                killActor(actor);
        }

        actors.remove(player);
    }

    /**
     * Checks what objects is on the player tile.
     *
     * @param actor actor object
     */
    public void checkPosition(IActor actor) {
        if (boardObjects.tileHasFlag(actor.getPos())) {
            actor.isOnFlag(board.getBoardLayers().get("flag").getCell(actor.getPos().getX(), actor.getPos().getY()).getTile().getId());
            actor.repairHitPoints();
            actor.setSpawnPoint(actor.getPos());
        }
        if (boardObjects.hasConveyor(actor.getPos())) {
            conveyor(actor);
            if (boardObjects.hasExpressConveyor(actor.getPos())) {
                conveyor(actor);
            }
        }
        if (boardObjects.tileHasTurnWheel(actor.getPos(), actor.getDirection())) {
            turnWheel(board.getBoardLayers().get("turnwheel").getCell(
                    actor.getPos().getX(), actor.getPos().getY()).getTile().getId() == 53, actor);
        }
        if (boardObjects.tileHasLaser(actor.getPos())) {
            actor.handleDamage();
            if (actor.isDead()) {
                killActor(actor);
            }
        }
        if (boardObjects.tileHasRepair(actor.getPos())) {
            actor.repairHitPoints();
            actor.setSpawnPoint(actor.getPos());
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

    /**
     * Turns the actor right if a left rotating turnwheel and right otherwise
     *
     * @param leftTurn if its a left rotating turnwheel
     * @param actor    the actor on the turnwheel
     */
    private void turnWheel(boolean leftTurn, IActor actor) {
        if (leftTurn) {
            actor.setDirection(actor.getDirection().turnLeft());
        } else {
            actor.setDirection(actor.getDirection().turnRight());
        }

    }

    /**
     *
     * @return the board
     */
    public Board getBoard() {
        return board;
    }


    /**
     *
     * @return the cards dealt to player
     */
    public ArrayList<Card> getPlayersDealtCards() {
        return player.getDealtCards();
    }

    /**
     *
     * @return deck object
     */
    public Deck getDeckObject() {
        return deck;
    }

    /**
     *
     * @return player object
     */
    public Player getPlayerObject(){
        return player;
    }

    /**
     * Sorts the list of actors from highest to lowest priority
     *
     * @param actors ArrayList of actors
     * @param count  Round count
     */
    private void sort(ArrayList<IActor> actors, int count) {
        for (int i = 0; i < actors.size(); i++) {
            IActor temp = actors.get(i);
            int idx = i;
            for (int j = i + 1; j < actors.size(); j++) {
                if (actors.get(j).getCard(count).getPriority() > temp.getCard(count).getPriority()) {
                    temp = actors.get(j);
                    idx = j;
                }
            }
            actors.remove(idx);
            actors.add(i, temp);
        }

    }
}
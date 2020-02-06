package inf112.skeleton.app;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Board {

    // Default pixels per tile
    private static final float BOARD_SIZE_X = 300;
    private static final float BOARD_SIZE_Y = 300;
    private int boardWidth;
    private int boardHeight;

    private TiledMap board;

    Board() {
        //board = new TmxMapLoader().load("assets/Test Board.tmx");
        board = new TmxMapLoader().load("assets/Risky Exchange.tmx");

        MapProperties properties = board.getProperties();
        boardWidth = properties.get("width", Integer.class);
        boardHeight = properties.get("height", Integer.class);
    }

    /**
     * Returns the width of the board
     *
     * @return width
     */
    public float getBoardWidth() {
        return boardWidth * BOARD_SIZE_X;
    }

    /**
     * Returns the height of the board
     *
     * @return height
     */
    public float getBoardHeight() {
        return boardHeight * BOARD_SIZE_Y;
    }

    /**
     * Returns the board
     *
     * @return board
     */
    public TiledMap getBoard() {
        return board;
    }
}

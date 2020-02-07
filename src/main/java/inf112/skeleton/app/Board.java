package inf112.skeleton.app;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Board {

    // Default pixels per tile
    public static final float TILE_SIZE = 300;
    private int boardWidth;
    private int boardHeight;

    private TiledMap board;

    public Board() {
        //board = new TmxMapLoader().load("assets/Risky_Exchange.tmx");
        board = new TmxMapLoader().load("assets/Test_Board.tmx");

        MapProperties properties = board.getProperties();
        boardWidth = properties.get("width", Integer.class);
        boardHeight = properties.get("height", Integer.class);
    }

    public Board(int width, int height) {
        boardWidth = width;
        boardHeight = height;
    }

    /**
     * Returns the width of the board
     *
     * @return width
     */
    public int getBoardWidth() {
        return boardWidth;
    }

    /**
     * Returns the height of the board
     *
     * @return height
     */
    public int getBoardHeight() {
        return boardHeight;
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

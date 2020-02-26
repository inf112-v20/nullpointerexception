package inf112.app.board;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashMap;
import java.util.Map;

public class Board {
    // Default pixels per tile

    private int boardWidth;
    private int boardHeight;

    private TiledMap board;
    private Map<String, TiledMapTileLayer> boardLayers;

    public Board(String boardName) {
        board = new TmxMapLoader().load(boardName);
        //board = new TmxMapLoader().load("assets/Test_Board.tmx");
        boardLayers = new HashMap<>();

        // Stores all the layers in the input map/board
        for (int i = 0; i < board.getLayers().size(); i++) {
            boardLayers.put(board.getLayers().get(i).getName(), (TiledMapTileLayer) board.getLayers().get(i));
        }

        boardWidth = board.getProperties().get("width", Integer.class);
        boardHeight = board.getProperties().get("height", Integer.class);
    }

    public Board(int width, int height) {
        boardWidth = width;
        boardHeight = height;
    }

    public Map<String, TiledMapTileLayer> getBoardLayers() {
        return boardLayers;
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

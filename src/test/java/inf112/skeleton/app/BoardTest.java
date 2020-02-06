package inf112.skeleton.app;


import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;


public class BoardTest {

    private TiledMap board;

    @Before
    public void setUp() {
        board = new TmxMapLoader().load("assets/Test Board.tmx");
    }

    @Test
    public void checksIfTheMapHasALayer() {
        assertNotNull(board.getLayers().get("Flag"));
    }
}

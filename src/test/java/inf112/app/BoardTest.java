package inf112.app;

import inf112.app.board.Board;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BoardTest {

    private Board board;

    @Before
    public void setUp() {
        board = new Board(5, 6);
    }

    @Test
    public void checksBoardHeight() {
        assertEquals(board.getBoardHeight(), 6);
    }


    @Test
    public void checksBoardWidth() {
        assertEquals(board.getBoardWidth(), 5);
    }
}

package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import inf112.app.player.Direction;
import inf112.app.player.Player;
import inf112.app.player.Position;

public class Input extends InputAdapter {
    private Player player;
    private Game game;

    public Input(Player player, Game game) {
        Gdx.input.setInputProcessor(this);
        this.player = player;
        this.game = game;
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
        Direction dir = player.getDirection();
        Position pos = player.getPos();
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.RIGHT:
                if (dir != Direction.EAST)
                    player.setDirection(Direction.EAST);
                else
                    game.movePlayer(pos, dir);
                break;
            case com.badlogic.gdx.Input.Keys.LEFT:
                if (dir != Direction.WEST)
                    player.setDirection(Direction.WEST);
                else
                    game.movePlayer(pos, dir);
                break;
            case com.badlogic.gdx.Input.Keys.DOWN:
                if (dir != Direction.NORTH)
                    player.setDirection(Direction.NORTH);
                else
                    game.movePlayer(pos, dir);
                break;
            case com.badlogic.gdx.Input.Keys.UP:
                if (dir != Direction.SOUTH)
                    player.setDirection(Direction.SOUTH);
                else
                    game.movePlayer(pos, dir);
                break;
            case com.badlogic.gdx.Input.Keys.Q:
                game.checkCurrentTile(player);
                break;
            case com.badlogic.gdx.Input.Keys.SPACE:
                game.tryToMove();
                break;
            default:
        }
        player.updateState();
        return super.keyDown(keycode);
    }
}

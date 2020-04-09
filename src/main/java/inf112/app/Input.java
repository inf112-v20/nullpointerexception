package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import inf112.app.player.Direction;
import inf112.app.player.Player;

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
        switch (keycode) {
            case com.badlogic.gdx.Input.Keys.RIGHT:
                if (dir != Direction.EAST)
                    player.setDirection(Direction.EAST);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.LEFT:
                if (dir != Direction.WEST)
                    player.setDirection(Direction.WEST);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.UP:
                if (dir != Direction.NORTH)
                    player.setDirection(Direction.NORTH);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.DOWN:
                if (dir != Direction.SOUTH)
                    player.setDirection(Direction.SOUTH);
                else
                    game.moveActor(player, player.getDirection());
                break;
            case com.badlogic.gdx.Input.Keys.Q:
                game.checkCurrentTile(player);
                break;
            case com.badlogic.gdx.Input.Keys.P:
                player.printCards();
                break;
            case com.badlogic.gdx.Input.Keys.Z:
                game.moveActors();
            case com.badlogic.gdx.Input.Keys.X:
                game.moveActorsByCards();
            default:
        }
        return super.keyDown(keycode);
    }
}

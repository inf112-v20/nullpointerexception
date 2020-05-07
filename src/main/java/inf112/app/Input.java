package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import inf112.app.player.Player;

public class Input extends InputAdapter {
    private final Player player;
    private final Game game;

    private final int counter = 0;

    public Input(Player player, Game game) {
        Gdx.input.setInputProcessor(this);
        this.player = player;
        this.game = game;
    }

}

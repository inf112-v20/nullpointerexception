package inf112.app;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRunner extends Game {

    public static int SCREEN_WIDTH = 900;
    public static int SCREEN_HEIGHT = 900;
    public static SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this));
    }

}

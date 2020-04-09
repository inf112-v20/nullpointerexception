package inf112.app.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameRunner extends Game {

    public static int SCREEN_WIDTH = 1200;
    public static int SCREEN_HEIGHT = 900;
    public SpriteBatch batch;

    @Override
    public void create() {

        batch = new SpriteBatch();
        //this.setScreen(new inf112.app.Game());
        this.setScreen(new GameScreen());
        //this.setScreen(new MenuScreen(this));
    }

}

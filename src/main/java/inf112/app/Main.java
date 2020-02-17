package inf112.app;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RoboRally";
        cfg.width = GameRunner.SCREEN_WIDTH;
        cfg.height = GameRunner.SCREEN_HEIGHT;

        new LwjglApplication(new GameRunner(), cfg);
    }
}
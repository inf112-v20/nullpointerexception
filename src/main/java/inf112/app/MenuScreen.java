package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen extends ScreenAdapter {

    private GameRunner gameRunner;
    private Texture startButton;
    private Texture startButtonActive;



    public MenuScreen(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        startButton = new Texture("start_game.png");
        startButtonActive = new Texture("start_game_active.png");
    }



    /**
     * Rendering the start button and starting up the game if the button is clicked
     *
     * @param v
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        gameRunner.batch.begin();

        //X and Y pos where the image is being drawn
        int x = (GameRunner.SCREEN_WIDTH / 2) - (startButton.getWidth() / 2);
        int y = GameRunner.SCREEN_HEIGHT / 2 -startButton.getHeight()/2;

        //Current mouse position on Y-axis
        int mousePosition_Y = Gdx.input.getY();
        //Current mouse position on X-axis
        int mousePosition_X = Gdx.input.getX();

        //Scaling mouse position when the resolution changes
        int scaleX = (mousePosition_X * GameRunner.SCREEN_WIDTH) / Gdx.graphics.getWidth();
        int scaleY = (mousePosition_Y * GameRunner.SCREEN_HEIGHT) / Gdx.graphics.getHeight();

        //If mouse hovers over the button and its being clicked, start the game
        if (scaleX < x + startButton.getWidth() && scaleX > x
                && scaleY < y + startButton.getHeight() && scaleY > y) {
            gameRunner.batch.draw(startButtonActive, x, y);
            //If mouse is clicked - start the game
            if (Gdx.input.isTouched()) {
                gameRunner.setScreen(new Game());
                dispose();
            }
        } else {
            gameRunner.batch.draw(startButton, x, y);
        }
        gameRunner.batch.end();
    }

    @Override
    public void dispose() {
        gameRunner.batch.dispose();
        startButton.dispose();
        startButtonActive.dispose();
    }

}
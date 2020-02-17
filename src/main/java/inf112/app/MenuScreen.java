package inf112.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Texture;

public class MenuScreen implements Screen {

    private GameRunner gameRunner;
    private Texture startButton;
    private Texture startButtonActive;

    public MenuScreen(GameRunner gameRunner){
        this.gameRunner = gameRunner;
        startButton = new Texture("start_game.png");
        startButtonActive = new Texture("start_game_active.png");
    }
    @Override
    public void show() {

    }

    /**
     * Rendering the start button and starting up the game if the button is clicked
     * @param v
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        GameRunner.batch.begin();

        //X pos where the image is being drawn
        int x = GameRunner.SCREEN_WIDTH/2 - startButton.getWidth()/2;
        //Current mouse position on Y-axes
        int mousePosition_Y = GameRunner.SCREEN_HEIGHT - Gdx.input.getY();
        //Current mouse position on X-axes
        int mousePosition_X = Gdx.input.getX();

        //If mouse hovers over the button and its being clicked, start the game
        if(mousePosition_X < x + startButton.getWidth() && mousePosition_X > x
                && mousePosition_Y < GameRunner.SCREEN_HEIGHT/2 + startButton.getHeight() && mousePosition_Y > GameRunner.SCREEN_HEIGHT/2) {
            GameRunner.batch.draw(startButtonActive, x, GameRunner.SCREEN_WIDTH/2);
            //If mouse is clicked - start the game
            if(Gdx.input.isTouched()){
                gameRunner.setScreen(new Game());
            }
        }
        else {
            GameRunner.batch.draw(startButton, x, GameRunner.SCREEN_WIDTH/2);
        }
        GameRunner.batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        GameRunner.batch.dispose();
    }
}

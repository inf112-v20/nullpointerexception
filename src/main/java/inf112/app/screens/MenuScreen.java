package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;


public class MenuScreen extends ScreenAdapter {

    private GameRunner gameRunner;
    private Texture background;
    private Button startButton;
    private Button startButtonActive;
    private Button exitButton;
    private Button exitButtonActive;
    private OrthographicCamera camera;

    public MenuScreen(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameRunner.SCREEN_WIDTH, GameRunner.SCREEN_HEIGHT);
        background = new Texture("menu/roborally_background.jpg");
        startButton = new Button("menu/start_game.png");
        startButtonActive = new Button("menu/start_game_active.png");
        exitButton = new Button("menu/exit.png");
        exitButtonActive = new Button("menu/exit_active.png");
        //X and Y pos where the image is being drawn
        int xStartBtn = (GameRunner.SCREEN_WIDTH / 2) - (startButton.getButtonTexture().getWidth() / 2);
        int yStartBtn = (GameRunner.SCREEN_HEIGHT / 2) - (startButton.getButtonTexture().getHeight() / 2);
        int xExitBtn = (GameRunner.SCREEN_WIDTH / 2) - (exitButton.getButtonTexture().getWidth() / 2);
        int yExitBtn = (GameRunner.SCREEN_HEIGHT / 2 - (2 * startButton.getButtonTexture().getHeight())) - (exitButton.getButtonTexture().getHeight() / 2);
        startButton.setButtonX(xStartBtn);
        startButton.setButtonY(yStartBtn);
        exitButton.setButtonX(xExitBtn);
        exitButton.setButtonY(yExitBtn);
    }


    /**
     * Rendering the start button and starting up the game if the button is clicked
     *
     * @param v The time in seconds since the last render
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        GameRunner.batch.setProjectionMatrix(camera.combined);
        GameRunner.batch.begin();

        GameRunner.batch.draw(background, 0, 0, GameRunner.SCREEN_WIDTH, GameRunner.SCREEN_HEIGHT);

        //Current mouse position on Y-axis
        int mousePosition_Y = Gdx.input.getY();
        //Current mouse position on X-axis
        int mousePosition_X = Gdx.input.getX();

        /**
         * Vector point of a current mouse position
         * unprojecting the mouse position so that it matches the worlds coordinates.
         * (Default mouse position at Y-axis in Gdx.input is the opposite of our worlds Y-axis coords, thats why
         * we need to unproject it)
         */
        Vector3 input = new Vector3(mousePosition_X, mousePosition_Y, 0);
        camera.unproject(input);

        //If mouse hovers over the button and its being clicked, start the game
        if (buttonIsHovered(startButton, input)) {
            GameRunner.batch.draw(startButtonActive.getButtonTexture(), startButton.getButtonX(), startButton.getButtonY());
            GameRunner.batch.draw(exitButton.getButtonTexture(), exitButton.getButtonX(), exitButton.getButtonY());
            //If mouse is clicked - start the game
            if (Gdx.input.isTouched()) {
                dispose();
                gameRunner.setScreen(new GameScreen());
            }
        } else if (buttonIsHovered(exitButton, input)) {
            GameRunner.batch.draw(exitButtonActive.getButtonTexture(), exitButton.getButtonX(), exitButton.getButtonY());
            GameRunner.batch.draw(startButton.getButtonTexture(), startButton.getButtonX(), startButton.getButtonY());
            //If mouse is clicked - close the game
            if (Gdx.input.isTouched()) {
                dispose();
                Gdx.app.exit();
            }
        } else {
            GameRunner.batch.draw(startButton.getButtonTexture(), startButton.getButtonX(), startButton.getButtonY());
            GameRunner.batch.draw(exitButton.getButtonTexture(), exitButton.getButtonX(), exitButton.getButtonY());
        }
        GameRunner.batch.end();
    }

    /**
     * Checks if the button is hovered over by the mouse cursor
     *
     * @param button
     * @param input
     * @return true or false
     */
    private boolean buttonIsHovered(Button button, Vector3 input) {
        return input.x < button.getButtonX() + button.getButtonTexture().getWidth() && input.x > button.getButtonX()
                && input.y < button.getButtonY() + button.getButtonTexture().getHeight() && input.y > button.getButtonY();
    }


    @Override
    public void dispose() {
        background.dispose();
        startButton.getButtonTexture().dispose();
        startButtonActive.getButtonTexture().dispose();
        exitButton.getButtonTexture().dispose();
        exitButtonActive.getButtonTexture().dispose();
    }
}
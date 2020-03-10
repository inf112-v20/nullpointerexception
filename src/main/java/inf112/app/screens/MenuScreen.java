package inf112.app.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;


public class MenuScreen extends ScreenAdapter {

    private GameRunner gameRunner;
    private Texture startButton;
    private Texture startButtonActive;
    private Texture background;
    private Texture exitButton;
    private Texture exitButtonActive;
    private OrthographicCamera camera;
    //X and Y pos where the image is being drawn
    int xStartBtn;
    int yStartBtn;
    int xExitBtn;
    int yExitBtn;

    public MenuScreen(GameRunner gameRunner) {
        this.gameRunner = gameRunner;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, GameRunner.SCREEN_WIDTH,GameRunner.SCREEN_HEIGHT);
        startButton = new Texture("menu/start_game.png");
        startButtonActive = new Texture("menu/start_game_active.png");
        background = new Texture("menu/roborally_background.jpg");
        exitButton  = new Texture("menu/exit.png");
        exitButtonActive = new Texture("menu/exit_active.png");
        xStartBtn = (GameRunner.SCREEN_WIDTH / 2) - (startButton.getWidth() / 2);
        yStartBtn = (GameRunner.SCREEN_HEIGHT / 2) - (startButton.getHeight() / 2);
        xExitBtn = (GameRunner.SCREEN_WIDTH / 2) - (exitButton.getWidth() / 2);
        yExitBtn = (GameRunner.SCREEN_HEIGHT / 2 - (2*startButton.getHeight())) - (exitButton.getHeight() / 2);
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
        gameRunner.batch.setProjectionMatrix(camera.combined);
        gameRunner.batch.begin();

        gameRunner.batch.draw(background,0 ,0,GameRunner.SCREEN_WIDTH,GameRunner.SCREEN_HEIGHT);

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
        if (buttonIsHovered(startButton,input)) {
            gameRunner.batch.draw(startButtonActive, xStartBtn, yStartBtn);
            gameRunner.batch.draw(exitButton, xExitBtn, yExitBtn);
            //If mouse is clicked - start the game
            if (Gdx.input.isTouched()) {
                dispose();
                gameRunner.setScreen(new GameScreen());
            }
        }
        else if(buttonIsHovered(exitButton,input)){
            gameRunner.batch.draw(exitButtonActive, xExitBtn, yExitBtn);
            gameRunner.batch.draw(startButton, xStartBtn, yStartBtn);
            //If mouse is clicked - close the game
            if (Gdx.input.isTouched()) {
                dispose();
                System.exit(0);
            }
        }
        else {
            gameRunner.batch.draw(startButton, xStartBtn, yStartBtn);
            gameRunner.batch.draw(exitButton, xExitBtn, yExitBtn);
        }
        gameRunner.batch.end();
    }

    /**
     * Checks if the button is hovered over by the mouse cursor
     * @param button
     * @param input
     * @return true or false
     */
    private boolean buttonIsHovered(Texture button, Vector3 input){
        if(button == startButton) {
            if (input.x < xStartBtn + startButton.getWidth() && input.x > xStartBtn
                    && input.y < yStartBtn + startButton.getHeight() && input.y > yStartBtn) {
                return true;
            }
            return false;
        }
        if(button == exitButton){
            if(input.x < xExitBtn + exitButton.getWidth() && input.x > xExitBtn
                    && input.y < yExitBtn + exitButton.getHeight() && input.y > yExitBtn){
                return true;
            }
            return false;
        }
        return false;
    }


    @Override
    public void dispose() {
        background.dispose();
        startButton.dispose();
        startButtonActive.dispose();
        exitButton.dispose();
        exitButtonActive.dispose();
    }
}
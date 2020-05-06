package inf112.app.screens;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Button {
    /**
     * A button class which takes in a string path argument to create a Texture
     */
    private Texture picture;
    private int x;
    private int y;

    public Button(String path) {
        this.picture = new Texture(path);
    }

    /**
     * Setters and Getters for button coordinates
     * x and y are the coordinates where the spritebatch should start drawing the texture
     */
    public void setButtonCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getButtonX() {
        return x;
    }

    public int getButtonY() {
        return y;
    }

    public Texture getButtonTexture() {
        return picture;
    }

    /**
     * Checks if the button is hovered over by the mouse cursor
     * @param input  input
     * @return true or false
     */
    public boolean buttonIsHovered(Vector3 input, int width, int height) {
        return input.x < this.getButtonX() + width && input.x > this.getButtonX()
                && input.y < this.getButtonY() + height && input.y > this.getButtonY();
    }
}

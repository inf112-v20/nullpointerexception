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
    public boolean buttonIsHovered(Vector3 input) {
        return input.x < this.getButtonX() + this.getButtonTexture().getWidth() && input.x > this.getButtonX()
                && input.y < this.getButtonY() + this.getButtonTexture().getHeight() && input.y > this.getButtonY();
    }
    public boolean buttonIsHovered(int x, int y) {
        return x < this.getButtonX() + this.getButtonTexture().getWidth() && x > this.getButtonX()
                && y < this.getButtonY() + this.getButtonTexture().getHeight() && y > this.getButtonY();
    }
}

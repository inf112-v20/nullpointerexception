package inf112.app.screens;

import com.badlogic.gdx.graphics.Texture;

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
}

package inf112.app.screens;

import com.badlogic.gdx.graphics.Texture;

public class Button {

    private Texture picture;
    private int x;
    private int y;

    public Button(String path){
        this.picture = new Texture(path);
    }

    public void setButtonX(int x){
        this.x = x;
    }
    public void setButtonY(int y){
        this.y = y;
    }
    public int getButtonX(){
        return x;
    }
    public int getButtonY(){
        return y;
    }
    public Texture getButtonTexture(){
        return picture;
    }
}

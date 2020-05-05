package inf112.app.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.app.Card;

import java.util.ArrayList;


public class Actor extends Player {

    /**
     * Initializing default/dying/winning cells of a player.
     * Texture region splits the player texture into 3 different textures and puts them in a 2-dimensional array.
     * Initializing the inputProcessor for input-listening.
     *
     * @param spawn   spawn point of the player
     * @param texture texture of player
     */
    public Actor(Position spawn, TextureRegion texture) {
        super(spawn, texture);
    }


    @Override
    public void setHand() {
        int size = getDealtCards().size();
        for (int i = 0; i < size; i++) {
            hand.add(0, dealtCards.remove(0));
        }
    }

    @Override
    public ArrayList<Card> getHand() {
        return hand;
    }
}

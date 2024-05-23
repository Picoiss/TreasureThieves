package seng201.team35.services;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;

/**
 * A class which handles the loading and initialisation of Cart Sprites
 * @author msh254
 */
public class CartSprite {
    private Map<String, Image> spriteMap;
    private static final String SPRITE_PATH = "/images/Carts/";

    /**
     * Initialiser for CartSprite
     */
    public CartSprite() {
        spriteMap = new HashMap<>();
        initializeSprites();
    }

    /**A function which initialises the Sprites for each Cart type in the game.
     * Maps a cart to an Image via the spriteMap HashMap
     * @author msh254
     */
    private void initializeSprites() {
        String[] carts = {"Bronze", "Silver", "Gold", "Diamond", "Emerald", "Ruby"};
        for (String cart : carts) {
            String imagePath = SPRITE_PATH + cart + ".png";
            Image image;
            try {
                image = new Image(getClass().getResourceAsStream(imagePath));
            } catch (Exception e) {
                image = new Image(getClass().getResourceAsStream(SPRITE_PATH + "Bronze.png"));
            }
            spriteMap.put(cart, image);
        }
    }

    /**A function which returns a image depending on what direction is given
     * Returns a Cart facing 'left' if the direction is left (0)
     *
     * @author msh254
     * @param cartType cartType
     * @param direction direction (int)
     * @return Image SpriteFrame
     */
    public Image getSpriteFrame(String cartType, int direction) {
        Image spriteSheet = spriteMap.get(cartType);
        int frameWidth = (int) spriteSheet.getWidth() / 4;
        int frameHeight = (int) spriteSheet.getHeight();
        int x = direction * frameWidth;
        return new WritableImage(spriteSheet.getPixelReader(), x, 0, frameWidth, frameHeight);
    }
}

package seng201.team35.models;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;

public class CartSprite {
    private Map<String, Image> spriteMap;
    private static final String SPRITE_PATH = "/images/Carts/";

    public CartSprite() {
        spriteMap = new HashMap<>();
        initializeSprites();
    }

    private void initializeSprites() {
        String[] carts = {"Bronze Cart", "Silver Cart", "Gold Cart", "Diamond Cart", "Emerald Cart", "Ruby Cart"};
        for (String cart : carts) {
            String imagePath = SPRITE_PATH + cart + ".png";
            Image image;
            try {
                image = new Image(getClass().getResourceAsStream(imagePath));
            } catch (Exception e) {
                image = new Image(getClass().getResourceAsStream(SPRITE_PATH + "Bronze Cart.png"));
            }
            spriteMap.put(cart, image);
        }
    }

    public Image getSpriteFrame(String cartType, int direction) {
        Image spriteSheet = spriteMap.get(cartType);
        int frameWidth = (int) spriteSheet.getWidth() / 4;
        int frameHeight = (int) spriteSheet.getHeight();
        int x = direction * frameWidth;
        return new WritableImage(spriteSheet.getPixelReader(), x, 0, frameWidth, frameHeight);
    }
}

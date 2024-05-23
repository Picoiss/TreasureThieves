package seng201.team35.models;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Projectile {
    private static final String SPRITE_PATH = "/images/Projectiles/";
    private static Map<String, Image> projectileSprites = new HashMap<>();

    public static Image getProjectileSprite(String spriteName) {
        String imagePath = SPRITE_PATH + spriteName;
        return new Image(Projectile.class.getResourceAsStream(imagePath));
    }
}

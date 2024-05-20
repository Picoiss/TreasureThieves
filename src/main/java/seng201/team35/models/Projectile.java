package seng201.team35.models;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class Projectile {
    private static final String SPRITE_PATH = "/images/";
    private static final String DEFAULT_SPRITE = "Arrow Long.png";
    private static Map<String, Image> projectileSprites = new HashMap<>();

    static {
        initializeSprites();
    }

    private static void initializeSprites() {
        // Load the default sprite for projectiles
        String imagePath = SPRITE_PATH + DEFAULT_SPRITE;
        try {
            Image image = new Image(Projectile.class.getResourceAsStream(imagePath));
            projectileSprites.put(DEFAULT_SPRITE, image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Image getProjectileSprite(String spriteName) {
        return projectileSprites.getOrDefault(spriteName, projectileSprites.get(DEFAULT_SPRITE));
    }
}

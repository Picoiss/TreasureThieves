package seng201.team35.models;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/** A Class Projectile which handles how the GameController gets the spritepath of a certain projectile.
 *
 * @author msh254
 */
public class Projectile {
    private static final String SPRITE_PATH = "/images/Projectiles/";

    /**A function which returns the imagePath of a projectile
     *
     * @author msh254
     * @param spriteName
     * @return imagePath (String)
     */
    public static Image getProjectileSprite(String spriteName) {
        String imagePath = SPRITE_PATH + spriteName;
        return new Image(Projectile.class.getResourceAsStream(imagePath));
    }
}

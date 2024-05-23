package seng201.team35.services;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;

/** A Class which handles Sprite Loading and ImagePaths.
 * @author msh254 (11 -> see readME)
 */
public class SpriteSheet {
    private Map<String, Image> spriteMap;
    private static final String DEFAULT_SPRITE = "/images/Towers/Ruby Dragon.png";
    private static final String SPRITE_PATH = "/images/Towers/";

    private static final int FRAME_COUNT = 5;

    /**
     * an initialises for SpriteSheet
     */
    public SpriteSheet() {
        spriteMap = new HashMap<>();
        initializeSprites();
    }

    /**
     * A function which makes a list of all the towers in the game. Next, it iterates over the list to get the imagePath
     * of all of the towers.
     * Creates an image Image for every tower
     * This Image is placed in a HashMap spriteMap which maps a tower to an image
     *
     * @author msh254
     */
    private void initializeSprites() {
        String[] towers = {
                "Bronze Archer", "Bronze Dwarf", "Bronze Villager",
                "Silver Knight", "Silver Priest", "Silver Alchemist",
                "Gold Giant", "Gold Goblin", "Gold Pirate",
                "Diamond Mage", "Diamond Necromancer", "Diamond Minotaur",
                "Emerald Elf", "Emerald Phoenix", "Emerald Pegasus",
                "Ruby Dragon", "Ruby OrcMage", "Ruby Golem"
        };

        for (String tower : towers) {
            String imagePath = SPRITE_PATH + tower + ".png";
            Image image;
            try {
                image = new Image(getClass().getResourceAsStream(imagePath));
            } catch (Exception e) {
                image = new Image(getClass().getResourceAsStream(DEFAULT_SPRITE));
            }
            spriteMap.put(tower, image);
        }
    }

    /**
     * A function which gets a Sprite from a towerName (Image)
     * default to "ruby dragon"
     *
     * @author msh254
     * @param towerName
     * @return Image (for the specific tower)
     */
    public Image getSprite(String towerName) {
        return spriteMap.getOrDefault(towerName, spriteMap.get("Ruby Dragon"));
    }

    /** A function which gets the specific frame of the Sprite.
     * This is done by Splitting the png of the Image in 5 (or as specified by frameIndex)
     * frameIndex dictates which Image to be returned (which split of the png)
     *
     *
     * @author msh254 (12 -> see readME)
     * @param towerName
     * @param frameIndex
     * @return Image (based on frameIndex)
     */
    public Image getSpriteFrame(String towerName, int frameIndex) {
        Image spriteSheet = getSprite(towerName);
        int spriteSheetWidth = (int) spriteSheet.getWidth();
        int spriteSheetHeight = (int) spriteSheet.getHeight();
        int frameWidth = spriteSheetWidth / FRAME_COUNT;
        int frameHeight = spriteSheetHeight;
        int x = frameIndex * frameWidth;
        int y = 0;
        return new WritableImage(spriteSheet.getPixelReader(), x, y, frameWidth, frameHeight);
    }

    /**returns the Frame_Count for a sprite png (5)
     *
     * @author msh254
     * @return int FRAME_COUNT
     */
    public int getFrameCount() {
        return FRAME_COUNT;
    }
}

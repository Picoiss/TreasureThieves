package seng201.team35.services;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import java.util.HashMap;
import java.util.Map;

public class SpriteSheet {
    private Map<String, Image> spriteMap;
    private static final String DEFAULT_SPRITE = "/images/Towers/Ruby Dragon.png";
    private static final String SPRITE_PATH = "/images/Towers/";

    private static final int FRAME_COUNT = 5;

    public SpriteSheet() {
        spriteMap = new HashMap<>();
        initializeSprites();
    }

    private void initializeSprites() {
        String[] towers = {
                "Bronze Archer", "Bronze Dwarf", "Bronze Villager",
                "Silver Knight", "Silver Priest", "Silver Assassin",
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
                image = new Image(getClass().getResourceAsStream(DEFAULT_SPRITE)); // i defaulted the sprite to Ruby Dragon
                // was the first sprite i had edited..
            }
            spriteMap.put(tower, image);
            //make sure the grid we use does not contain a tower. (popualte the arraylist? no map thing idk what its called)
        }//hashmap..
    }

    public Image getSprite(String towerName) {
        return spriteMap.getOrDefault(towerName, spriteMap.get("Ruby Dragon"));
        //return the sprite for the towername, or the sporite for ruby dragon.
    }

    public Image getSpriteFrame(String towerName, int frameIndex) { // god this was awful to code.
        Image spriteSheet = getSprite(towerName); // set the image to the sprite image..
        int spriteSheetWidth = (int) spriteSheet.getWidth(); // width is the width of the image
        int spriteSheetHeight = (int) spriteSheet.getHeight(); // height is the height of the image.. yes.
        int frameWidth = spriteSheetWidth / FRAME_COUNT; // frame count is here. not (5) because i was trying things.
        // most likely a redundant class variable.
        int frameHeight = spriteSheetHeight;
        int x = frameIndex * frameWidth;
        int y = 0;
        return new WritableImage(spriteSheet.getPixelReader(), x, y, frameWidth, frameHeight); // yet to understand.
    }

    public int getFrameCount() {
        return FRAME_COUNT; // redundant function.. (dont remove though as there will be dependencies)
    }
}

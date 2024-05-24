package seng201.team35.unittests.services;

import javafx.scene.image.Image;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team35.models.Tower;
import seng201.team35.services.SpriteSheet;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpriteSheetTest {

    private Tower bronzeArcher;
    private Tower silverKnight;
    private Tower goldGiant;

    @BeforeEach
    public void setup() {
        bronzeArcher = new Tower("Bronze Archer", 5,100, "Bronze", 100);
        silverKnight = new Tower("Silver Knight",7,150, "Silver", 150);
        goldGiant = new Tower("Gold Giant", 6,200, "Gold", 200);
    }

    @Test
    public void getFrameCountTest() {
        SpriteSheet spriteSheet = new SpriteSheet();
        assertEquals(5, spriteSheet.getFrameCount());
    }

}

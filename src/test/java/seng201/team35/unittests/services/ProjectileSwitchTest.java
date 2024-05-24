package seng201.team35.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team35.services.ProjectileSwitch;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectileSwitchTest {

    private String bronzeArcher;
    private String silverKnight;
    private String emeraldPegasus;
    private String sapphireWarrior;

    @BeforeEach
    public void setup() {
        bronzeArcher = "Bronze Archer";
        silverKnight = "Silver Knight";
        emeraldPegasus = "Emerald Pegasus";
        sapphireWarrior = "Sapphire Warrior";
    }

    @Test
    public void getProjectileSpriteTest() {
        String projectile1 = ProjectileSwitch.getProjectileSprite(bronzeArcher);
        assertEquals("Arrow Long.png", projectile1);
        String projectile2 = ProjectileSwitch.getProjectileSprite(silverKnight);
        assertEquals("Sword.png", projectile2);
        String projectile3 = ProjectileSwitch.getProjectileSprite(emeraldPegasus);
        assertEquals("Fireball.png", projectile3);
        String projectile4 = ProjectileSwitch.getProjectileSprite(sapphireWarrior);
        assertEquals("Arrow Long.png", projectile4);
    }

}

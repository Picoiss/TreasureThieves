package seng201.team35.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team35.models.Upgrade;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UpgradeTest {
    private Upgrade bronzeUpgrade;
    private Upgrade silverUpgrade;
    private Upgrade goldUpgrade;

    @BeforeEach
    public void setUp() {
        bronzeUpgrade = new Upgrade(10, 10, "Bronze", 100, "Active");
        silverUpgrade = new Upgrade(10, 10, "Silver", 150, "Active");
        goldUpgrade = new Upgrade(10, 10, "Gold", 200, "Active");
    }

    @Test
    public void constructorGettersTest() {
        assertEquals(10, bronzeUpgrade.getBoostResourceAmount());
        assertEquals(10, bronzeUpgrade.getReduceReloadSpeed());
        assertEquals("Bronze", bronzeUpgrade.getResourceType());
        assertEquals(100, bronzeUpgrade.getCost());
        assertEquals("Active", bronzeUpgrade.getStatus());
    }

    @Test
    public void toggleStatusTest() {
        silverUpgrade.toggleStatus();
        assertEquals("Inactive", silverUpgrade.getStatus());
        silverUpgrade.toggleStatus();
        assertEquals("Active", silverUpgrade.getStatus());
    }

    @Test
    public void getUpgradeNamesTest() {
        List<Upgrade> upgrades = Arrays.asList(bronzeUpgrade, silverUpgrade, goldUpgrade);
        List<String> upgradeNames = Upgrade.getUpgradeNames(upgrades);
        assertEquals(Arrays.asList("Bronze", "Silver", "Gold"), upgradeNames);
    }
}

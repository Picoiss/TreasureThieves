package seng201.team35.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team35.models.Tower;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TowerTest {
    private Tower bronzeArcher;
    private Tower silverKnight;
    private Tower goldGiant;

    @BeforeEach
    public void setUp() {
        // Initialize a Tower object before each test
        bronzeArcher = new Tower("Bronze Archer", 100,100, "Bronze", 100);
        silverKnight = new Tower("Silver Knight",80,150, "Silver", 150);
        goldGiant = new Tower("Gold Giant", 70,200, "Gold", 200);
    }

    @Test
    public void testConstructorsGetters() {
        assertEquals("Bronze Archer", bronzeArcher.getName());
        assertEquals(100, bronzeArcher.getReloadSpeed());
        assertEquals(100, bronzeArcher.getMaxAmount());
        assertEquals("Bronze", bronzeArcher.getResourceType());
        assertEquals(100, bronzeArcher.getCost());
        assertEquals(1, bronzeArcher.getLevel());
    }

    @Test
    public void testIncreaseLevel() {
        bronzeArcher.increaseLevel();
        bronzeArcher.increaseLevel();
        assertEquals(3, bronzeArcher.getLevel());
    }

    @Test
    public void testIncreaseMaxAmount() {
        bronzeArcher.increaseMaxAmount(10);
        assertEquals(110, bronzeArcher.getMaxAmount());
    }

    @Test
    public void testGetTowerNames() {

        Collection<Tower> towers = Arrays.asList(bronzeArcher, silverKnight, goldGiant);

        List<String> names = Tower.getTowerNames(towers);
        assertEquals(Arrays.asList("Bronze Archer", "Silver Knight", "Gold Giant"), names);
    }
}

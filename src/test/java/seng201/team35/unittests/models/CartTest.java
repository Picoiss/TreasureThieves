package seng201.team35.unittests.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team35.models.Cart;

import static org.junit.jupiter.api.Assertions.*;

public class CartTest {
    private Cart bronzeCart;
    private Cart silverCart;

    @BeforeEach
    public void setUp() {
        bronzeCart = new Cart(100, "Bronze", 1);
        silverCart = new Cart(125, "Silver", 1.5);
    }

    @Test
    public void constructorGettersTest() {
        assertEquals(100, bronzeCart.getSize());
        assertEquals("Bronze", bronzeCart.getResourceType());
        assertEquals(1, bronzeCart.getSpeed());
        assertEquals(0, bronzeCart.getDirection());
        assertEquals(0, bronzeCart.getCurrentAmount());
        assertFalse(bronzeCart.isCartFilled());
    }

    @Test
    public void fillCartTest() {
        bronzeCart.fillCart(50);
        assertEquals(50, bronzeCart.getCurrentAmount());
        bronzeCart.fillCart(50);
        assertEquals(100, bronzeCart.getCurrentAmount());
    }

    @Test
    public void isCartFilledTest() {
        bronzeCart.fillCart(100);
        assertTrue(bronzeCart.isCartFilled());
        silverCart.fillCart(100);
        assertFalse(silverCart.isCartFilled());
    }

    @Test
    public void setDirectionTest() {
        bronzeCart.setDirection(2);
        assertEquals(2, bronzeCart.getDirection());
    }
}

package seng201.team35.models;

/**
 * Cart Class which allows for the creation of individual carts with specified
 * parameters. Can be called as many times in context of the rounds
 * @author msh254
 */
public class Cart {
    private final int size;
    private final String recourseType;
    private int speed;
    private int fillAmount;
    private int position;

    /**
     * Default Cart Constructor.
     * @param size
     * @param recourseType
     * @param speed
     */
    public Cart(int size, String recourseType, int speed) {
        this.recourseType = recourseType;
        this.speed = speed;
        this.size = size;
        this.position = position;
        fillAmount = 0;
    }

    /**
     * gives the current amount of 'recourse' in the Cart (e.g how fill
     * the cart is)
     * @return Current resource amount
     */
    public int getFillAmount() {
        return fillAmount;
    }

    /**
     * returns the speed of the cart
     * @return Speed of Cart
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * returns the size of the cart
     * @return Size of Cart
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the recourse type of the cart.
     * @return Recourse Type
     */
    public String getRecourseType() {
        return recourseType;
    }

    /**
     * A function which when called can increase the speed of the cart
     * this may be done as a certain part of the 'map' may cause the
     * cart to move faster.
     * @param increment value to change the speed of the cart by 'increment'
     */
    public void increaseSpeed(int increment) {
        speed += increment;
    }

    public void increasePosition() {
        position += speed;
    }

    public void fill(int amount) {
        fillAmount += amount;
    }

    public boolean isFill() {
        if (fillAmount >= size) {
            return true;
        }
        else {
            return false;
        }
    }
}

package seng201.team35.models;

public class Cart {
    private int size;
    private double speed;
    private String resourceType;
    private int x;
    private int y;
    private int direction;
    private int currentAmount;
    private boolean isFilled = false;

    public Cart(int size, String resourceType, double speed) {
        this.size = size;
        this.currentAmount = 0;
        this.resourceType = resourceType;
        this.speed = speed;
        this.x = -1;
        this.y = -1;
        this.direction = 0;
    }
    public void fillCart(int fillAmount) {
        currentAmount += fillAmount;
    }
    public boolean isCartFilled() {
        isFilled = currentAmount >= this.size;
        return isFilled;
    }

    public int getSize() {
        return size;
    }

    public String getResourceType() {
        return resourceType;
    }

    public double getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }
    public void setDirection(int direction) {
        this.direction = direction;
    }

}

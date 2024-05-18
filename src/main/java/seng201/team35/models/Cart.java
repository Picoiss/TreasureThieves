package seng201.team35.models;

public class Cart {
    private int size;
    private double speed;
    private String resourceType;

    public Cart(int size, String resourceType, double speed) {
        this.size = size;
        this.resourceType = resourceType;
        this.speed = speed;
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
}

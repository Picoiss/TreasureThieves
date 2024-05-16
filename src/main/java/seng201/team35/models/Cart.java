package seng201.team35.models;

import java.awt.Point;
import java.util.List;

public class Cart {
    private final int size;
    private final String resourceType;
    private int fillAmount;
    private int waypointIndex;
    private List<Point> waypoints;

    public Cart(int size, String resourceType, List<Point> waypoints) {
        this.size = size;
        this.resourceType = resourceType;
        this.waypoints = waypoints;
        this.waypointIndex = 0;
        this.fillAmount = 0;
    }

    public Point getPosition() {
        return waypoints.get(waypointIndex);
    }

    public boolean hasNextWaypoint() {
        return waypointIndex < waypoints.size() - 1;
    }

    public void nextWaypoint() {
        if (hasNextWaypoint()) {
            waypointIndex++;
        }
    }

    public void fill(int amount) {
        this.fillAmount += amount;
    }

    public boolean isFull() {
        return fillAmount >= size;
    }

    public int getFillAmount() {
        return fillAmount;
    }
}

package seng201.team35.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tower class to create unique towers to add to the player's inventory
 * Upgrades can change a Tower's parameters
 * @author nsr36
 */
public class Tower {
    private final String name;
    private final String resourceType;
    private final int cost;
    private int maxAmount;
    private int resourceAmount;
    private int reloadSpeed;
    private int level;

    /**
     * Tower Constructor
     */
    public Tower(String towerName, int towerCapacity, String towerType, int towerCost) {
        name = towerName;
        maxAmount = towerCapacity;
        resourceType = towerType;
        cost = towerCost;
        level = 1;
    }

    /**
     * Get the tower's name
     * @return name
     */
    public String getName() { return name; }

    /**
     * Get current tower resource amount
     * @return Current resource amount
     */
    public int getResourceAmount() { return resourceAmount; }

    /**
     * Get current tower reload speed
     * @return Current reload speed
     */
    public int getReloadSpeed() { return reloadSpeed; }

    /**
     * Get tower resource type
     * @return resource type
     */
    public String getResourceType() { return resourceType; }

    /**
     * Get current tower level
     * @return Current level
     */
    public int getLevel() { return level; }

    /**
     * Get current tower cost
     * @return Current cost
     */
    public int getCost() { return cost; }

    /**
     * Reduce the current tower resource amount
     * Call the reloadResourceAmount method if the current resource amount goes to 0 or below
     * @param deplete Value to reduce the resource amount by
     */
    public void depleteResourceAmount(int deplete) {
        if (deplete >= resourceAmount) {
            resourceAmount = 0;
            reloadResourceAmount();
        }
        else {
            resourceAmount -= deplete;
        }
    }

    /**
     * Wait for reload speed time
     * Then reset the current tower resource amount to the max amount
     */
    public void reloadResourceAmount() {
        try {
            Thread.sleep(1000L *reloadSpeed);
            resourceAmount = maxAmount;
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Increase the current tower max amount
     * @param increment Value to increase the max amount by
     */
    public void increaseMaxAmount(int increment) { maxAmount += increment; }

    /**
     * Increase the current tower level by 1
     */
    public void increaseLevel() { level += 1; }

    /**
     * Decrease the current tower reload speed
     * @param decrement Value to decrease the reload speed by
     */
    public void decreaseReloadSpeed(int decrement) {
        if (decrement > reloadSpeed) {
            //throw exception
        }
        else { reloadSpeed -= decrement; }
    }

    /**
     * Return the names of a tower collection as a string list
     * @param towers stream using a map function converted to a list
     */
    public static List<String> getTowerNames(Collection<Tower> towers) {
        return towers.stream().map(Tower::getName).collect(Collectors.toList());
    }
}
package seng201.team35.models;

/**
 * Tower class to
 * @author nsr36
 */
public class Tower {
    private final String resourceType;
    private final int cost;
    private int maxAmount;
    private int resourceAmount;
    private int reloadSpeed;
    private int level;

    /**
     * Tower Constructor
     */
    public Tower(int towerCapacity, String towerType, int towerCost) {
        maxAmount = towerCapacity;
        resourceType = towerType;
        cost = towerCost;
        level = 1;
    }

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
     * Get current tower resource type
     * @return Current resource type
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
}
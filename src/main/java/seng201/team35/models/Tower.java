package seng201.team35.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tower class to create unique towers to add to the player's inventory
 * Upgrades can change a Tower's parameters
 * @author nsr36, msh254
 */
public class Tower {
    private final String name;
    private final String resourceType;
    private final int cost;
    private int maxAmount;
    private int reloadSpeed;
    private int level;

    /**
     * Tower Constructor
     * @author msh254, nsr36
     * @param towerName name of tower
     * @param towerSpeed reload speed of tower
     * @param resourceAmount maximum resource amount of tower
     * @param towerType resource type of tower
     * @param towerCost cost to buy tower in shop
     */
    public Tower(String towerName, int towerSpeed, int resourceAmount, String towerType, int towerCost) {
        name = towerName;
        reloadSpeed = towerSpeed;
        maxAmount = resourceAmount;
        resourceType = towerType;
        cost = towerCost;
        level = 1;
    }

    /**
     * Get the tower's name
     * @author nsr36
     * @return name
     */
    public String getName() { return name; }

    /**
     * Get current tower resource amount
     * @author msh254
     * @return Current resource amount
     */
    public int getMaxAmount() {return maxAmount;}

    /**
     * Get current tower reload speed
     * @author nsr36
     * @return Current reload speed
     */
    public int getReloadSpeed() { return reloadSpeed; }

    /**
     * Get tower resource type
     * @author nsr36
     * @return resource type
     */
    public String getResourceType() { return resourceType; }

    /**
     * Get current tower level
     * @author nsr36
     * @return Current level
     */
    public int getLevel() { return level; }

    /**
     * Get current tower cost
     * @author nsr36
     * @return Current cost
     */
    public int getCost() { return cost; }

    /**
     * Increase the current tower max amount
     * @author nsr36
     * @param increment Value to increase the max amount by
     */
    public void increaseMaxAmount(int increment) { maxAmount += increment; }

    /**
     * Increase the current tower level by
     * @author nsr36
     */
    public void increaseLevel() { level += 1; }

    /**
     * Return the names of a tower collection as a string list
     * @author nsr36
     * @param towers stream using a map function converted to a list
     */
    public static List<String> getTowerNames(Collection<Tower> towers) {
        return towers.stream().map(Tower::getName).collect(Collectors.toList());
    }

}
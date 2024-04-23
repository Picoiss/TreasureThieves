package seng201.team35.models;

/**
 * Upgrade class to improve the parameters of a Tower object
 * @author nsr36
 */
public class Upgrade {
    private final int cost;
    private String resourceType;
    private int boostResourceAmount;
    private int reduceReloadSpeed;

    /**
     * Upgrade Constructor
     */
    public Upgrade(int amountBoost, int speedReduce, String upgradeType, int upgradeCost) {
        boostResourceAmount = amountBoost;
        reduceReloadSpeed = speedReduce;
        resourceType = upgradeType;
        cost = upgradeCost;
    }

    /**
     * Get current upgrade resource type
     * @return Resource type
     */
    public String getResourceType() { return resourceType; }

    /**
     * Get upgrade's resource amount boost value
     * @return boostResourceAmount
     */
    public int getBoostResourceAmount() { return boostResourceAmount; }

    /**
     * Get upgrade's reload speed reducer
     * @return reduceReloadSpeed
     */
    public int getReduceReloadSpeed() { return reduceReloadSpeed; }
}

package seng201.team35.models;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Upgrade class to improve the parameters of a Tower object
 * @author nsr36, msh254
 */
public class Upgrade {
    private final int cost;
    private String resourceType;
    private int boostResourceAmount;
    private int reduceReloadSpeed;
    private String status;

    /**
     * Upgrade Constructor
     * @author nsr36
     * @param amountBoost increase for amount filled
     * @param speedReduce reduction for speed of fill
     * @param upgradeType resource type of upgrade
     * @param upgradeCost cost to buy upgrade in shop
     * @param upgradeStatus whether the upgrade is active or not
     */
    public Upgrade(int amountBoost, int speedReduce, String upgradeType, int upgradeCost, String upgradeStatus) {
        boostResourceAmount = amountBoost;
        reduceReloadSpeed = speedReduce;
        resourceType = upgradeType;
        cost = upgradeCost;
        status = upgradeStatus;
    }

    /**
     * Get current upgrade resource type
     * @author nsr36
     * @return Resource type
     */
    public String getResourceType() { return resourceType; }

    /**
     * Get upgrade's resource amount boost value
     * @author nsr36
     * @return boostResourceAmount
     */
    public int getBoostResourceAmount() { return boostResourceAmount; }

    /**
     * Get upgrade's reload speed reducer
     * @author nsr36
     * @return reduceReloadSpeed
     */
    public int getReduceReloadSpeed() { return reduceReloadSpeed; }

    /**
     * Return the cost of an upgrade
     * @author msh254
     * @return cost
     */
    public int getCost() { return cost;}

    /**
     * Return the status of an upgrade
     * @author msh254
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status to false if true or true if false
     * @author msh254
     */
    public void toggleStatus() {
        if ("Active".equals(status)) {
            status = "Inactive";
        } else if ("Inactive".equals(status)) {
            status = "Active";
        }
    }

    /**
     * Return the names of an upgrade collection as a string list
     * @author nsr36
     * @return list of names
     */
    public static List<String> getUpgradeNames(Collection<Upgrade> upgrades) {
        return upgrades.stream().map(Upgrade::getResourceType).collect(Collectors.toList());
    }
}

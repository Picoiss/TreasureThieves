package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.util.*;

/**
 * Controller for the gameShop.fxml window
 * @author nsr36, msh254
 */

public class ShopController {
    @FXML
    private Label moneyLabel;
    @FXML
    private Label towerName;
    @FXML
    private Label speed;
    @FXML
    private Label level;
    @FXML
    private Label cost;
    @FXML
    private Label resourceAmount;
    @FXML
    private Label towerNamelabel;
    @FXML
    private Label speedLabel;
    @FXML
    private Label levelLabel;
    @FXML
    private Label costLabel;
    @FXML
    private Label resourceAmountLabel;
    @FXML
    private Label resourceType;
    @FXML
    private ComboBox shopTowersComboBox;
    @FXML
    private ComboBox shopUpgradesComboBox;
    @FXML
    private ComboBox mainTowersComboBox;
    @FXML
    private ComboBox reserveTowersComboBox;
    @FXML
    private ComboBox upgradesComboBox;
    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;
    @FXML
    private Button returnToMainMenuButton;
    @FXML
    private Label resourceTypeLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label livesLabel;
    @FXML
    private Label roundsLabel;
    @FXML
    private Label roundsLeftLabel;
    private Upgrade currentUpgrade;
    private Tower currentTower;
    private String currentCombobox;
    private GameManager gameManager;
    private List<Tower> shopTowerList = new ArrayList<>();

    public ShopController(GameManager x) {
        gameManager = x;
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        shopTowerList.addAll(getShopTowerSet(gameManager.getCurrentRound()));
        shopTowerList.sort(Comparator.comparing(Tower::getCost));
        shopTowersComboBox.getItems().setAll(Tower.getTowerNames(shopTowerList));
        shopUpgradesComboBox.getItems().setAll(Upgrade.getUpgradeNames(getShopUpgradeList(gameManager.getCurrentRound())));
        livesLabel.setText(String.valueOf(gameManager.getLives()));
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        roundsLabel.setText(String.valueOf(gameManager.getCurrentRound()));
        roundsLeftLabel.setText(String.valueOf(gameManager.getNumOfRounds() - gameManager.getCurrentRound()));
        updateComboBox();
    }

    @FXML
    private void goToMainMenu() {
        gameManager.closeShopScreen();
    }

    private void updateComboBox() {
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        mainTowersComboBox.setValue("");
        reserveTowersComboBox.setValue("");
        upgradesComboBox.setValue("");
        shopTowersComboBox.setValue("");
        shopUpgradesComboBox.setValue("");
        mainTowersComboBox.getItems().setAll(Tower.getTowerNames(gameManager.getMainTowerList()));
        reserveTowersComboBox.getItems().setAll(Tower.getTowerNames(gameManager.getReserveTowerList()));
        upgradesComboBox.getItems().setAll(Upgrade.getUpgradeNames(gameManager.getUpgradesList()));
        towerNamelabel.setText("");
        speedLabel.setText("");
        levelLabel.setText("");
        costLabel.setText("");
        resourceAmountLabel.setText("");
        resourceTypeLabel.setText("");
        currentCombobox = null;
    }

    private void clearComboBoxes() {
        if (!Objects.equals(currentCombobox, "ShopTower")) { shopTowersComboBox.setValue(""); }
        if (!Objects.equals(currentCombobox, "ShopUpgrade")) { shopUpgradesComboBox.setValue(""); }
        if (!Objects.equals(currentCombobox, "MainTower")) { mainTowersComboBox.setValue(""); }
        if (!Objects.equals(currentCombobox, "ReserveTower")) { reserveTowersComboBox.setValue(""); }
        if (!Objects.equals(currentCombobox, "MyUpgrade")) { upgradesComboBox.setValue(""); }
    }

    private void setDetailLabels(String towerNameLabelText, String speedLabelText, String levelLabelText,
                                 String costLabelText, String resourceAmountLabelText, String resourceTypeLabelText) {
        towerNamelabel.setText(towerNameLabelText);
        speedLabel.setText(speedLabelText);
        levelLabel.setText(levelLabelText);
        costLabel.setText(costLabelText);
        resourceAmountLabel.setText(resourceAmountLabelText);
        resourceTypeLabel.setText(resourceTypeLabelText);
    }

    private void displaySelectedTower() {
        towerName.setText("Tower name");
        speed.setText("Speed");
        level.setText("Level");
        resourceAmount.setText("Resource Amount");
        resourceType.setText("Resource Type");
        if (currentTower != null) {
            int cost;
            if (currentCombobox == "ShopTower")
                cost = currentTower.getCost();
            else {
                cost = (int) (0.9*currentTower.getCost());
            }
            setDetailLabels(currentTower.getName(), String.valueOf(currentTower.getReloadSpeed()), String.valueOf(currentTower.getLevel()),
                    String.valueOf(cost), String.valueOf(currentTower.getMaxAmount()), String.valueOf(currentTower.getResourceType()));
            clearComboBoxes();
            currentCombobox = null;
        }
    }

    private void displaySelectedUpgrade() {
        towerName.setText("Upgrade type");
        speed.setText("Resource Boost");
        level.setText("Reload Speed Boost");
        resourceAmount.setText("");
        resourceType.setText("");
        if (currentUpgrade != null) {
            int cost;
            if (currentCombobox == "ShopUpgrade")
                cost = currentUpgrade.getCost();
            else {
                cost = (int) (0.9*currentUpgrade.getCost());
            }
            setDetailLabels(currentUpgrade.getResourceType(), String.valueOf(currentUpgrade.getBoostResourceAmount()),
                    String.valueOf(currentUpgrade.getReduceReloadSpeed()), String.valueOf(cost), "", "");
            clearComboBoxes();
            currentCombobox = null;
        }
    }

    @FXML
    private void selectedShopTower() {
        if (currentCombobox == null) {
            currentCombobox = "ShopTower";
            currentTower = gameManager.getTowerClass(shopTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    @FXML
    private void selectedShopUpgrade() {
        if (currentCombobox == null) {
            currentCombobox = "ShopUpgrade";
            currentUpgrade = gameManager.getUpgradeClass(shopUpgradesComboBox.getValue().toString());
            displaySelectedUpgrade();
        }
    }

    @FXML
    private void selectedMainTower() {
        if (currentCombobox == null) {
            currentCombobox = "MainTower";
            currentTower = gameManager.getTowerClass(mainTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    @FXML
    private void selectedReserveTower() {
        if (currentCombobox == null) {
            currentCombobox = "ReserveTower";
            currentTower = gameManager.getTowerClass(reserveTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    @FXML
    private void selectedMyUpgrade() {
        if (currentCombobox == null) {
            currentCombobox = "MyUpgrade";
            currentUpgrade = gameManager.getUpgradeClass(upgradesComboBox.getValue().toString());
            displaySelectedUpgrade();
        }
    }

    private Set<Tower> getShopTowerSet(int currentRound) {
        Set<Tower> shopTowerSet = new HashSet<>();
        //shopTowerSet.addAll(gameManager.getDefaultTowers()); // this line is for testing purposes (to delete later)
        switch (currentRound) {
            case 1:
                shopTowerSet.add(gameManager.getTowerClass("Bronze Archer"));
                shopTowerSet.add(gameManager.getTowerClass("Bronze Dwarf"));
                shopTowerSet.add(gameManager.getTowerClass("Silver Knight"));
                shopTowerSet.add(gameManager.getTowerClass("Silver Priest"));
                break;
            case 2:
                shopTowerSet.add(gameManager.getTowerClass("Bronze Villager"));
                shopTowerSet.add(gameManager.getTowerClass("Silver Knight"));
                shopTowerSet.add(gameManager.getTowerClass("Silver Alchemist"));
                shopTowerSet.add(gameManager.getTowerClass("Gold Giant"));
                break;
            case 3:
                shopTowerSet.add(gameManager.getTowerClass("Silver Alchemist"));
                shopTowerSet.add(gameManager.getTowerClass("Gold Giant"));
                shopTowerSet.add(gameManager.getTowerClass("Gold Goblin"));
                break;
            case 4:
                shopTowerSet.add(gameManager.getTowerClass("Gold Pirate"));
                shopTowerSet.add(gameManager.getTowerClass("Silver Priest"));
                shopTowerSet.add(gameManager.getTowerClass("Bronze Villager"));
                break;
            case 5:
                shopTowerSet.add(gameManager.getTowerClass("Diamond Mage"));
                shopTowerSet.add(gameManager.getTowerClass("Silver Priest"));
                shopTowerSet.add(gameManager.getTowerClass("Bronze Villager"));
                shopTowerSet.add(gameManager.getTowerClass("Gold Pirate"));
                break;
            case 6:
                shopTowerSet.add(gameManager.getTowerClass("Diamond Necromancer"));
                shopTowerSet.add(gameManager.getTowerClass("Diamond Minotaur"));
                shopTowerSet.add(gameManager.getTowerClass("Gold Goblin"));
                break;
            case 7:
                shopTowerSet.addAll(getShopTowerSet(1));
                shopTowerSet.addAll(getShopTowerSet(2));
                shopTowerSet.addAll(getShopTowerSet(3));
                shopTowerSet.addAll(getShopTowerSet(4));
                shopTowerSet.addAll(getShopTowerSet(5));
                shopTowerSet.addAll(getShopTowerSet(6));
                break;
            case 8:
                shopTowerSet.add(gameManager.getTowerClass("Emerald Elf"));
                shopTowerSet.add(gameManager.getTowerClass("Emerald Phoenix"));
                shopTowerSet.addAll(getShopTowerSet(7));
                break;
            case 9:
                shopTowerSet.addAll(getShopTowerSet(8));
                shopTowerSet.add(gameManager.getTowerClass("Emerald Pegasus"));
                break;
            case 10:
                shopTowerSet.add(gameManager.getTowerClass("Ruby Dragon"));
                shopTowerSet.add(gameManager.getTowerClass("Ruby OrcMage"));
                shopTowerSet.add(gameManager.getTowerClass("Ruby Golem"));
                break;
            default:
                shopTowerSet.addAll(gameManager.getDefaultTowers());
        }
        return shopTowerSet;
    }

    private List<Upgrade> getShopUpgradeList(int currentRound) {
        List<Upgrade> shopUpgradeList = new ArrayList<>();
        switch (currentRound) {
            case 1:
                shopUpgradeList.add(gameManager.getUpgradeClass("Bronze"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Silver"));
                break;
            case 2, 3, 4:
                shopUpgradeList.add(gameManager.getUpgradeClass("Bronze"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Silver"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Gold"));
                break;
            case 5, 6, 7:
                shopUpgradeList.add(gameManager.getUpgradeClass("Bronze"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Silver"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Gold"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Diamond"));
                break;
            case 8, 9:
                shopUpgradeList.add(gameManager.getUpgradeClass("Bronze"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Silver"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Gold"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Diamond"));
                shopUpgradeList.add(gameManager.getUpgradeClass("Emerald"));
                break;
            default:
                shopUpgradeList.addAll(gameManager.getDefaultUpgrades());
        }
        return shopUpgradeList;
    }

    @FXML
    private void sell() {
        errorLabel.setText("");
        if (mainTowersComboBox.getValue() != "") {
            System.out.println("Main Tower has been Sold");
            gameManager.removeMainTower(currentTower);
            gameManager.changeMoneyAmount(currentTower.getCost());
            updateComboBox();
        }
        else if (reserveTowersComboBox.getValue() != "") {
            System.out.println("Reserve Tower has been Sold");
            gameManager.removeReserveTower(currentTower);
            gameManager.changeMoneyAmount(currentTower.getCost());
            updateComboBox();
        }
        else if (upgradesComboBox.getValue() != "") {
            System.out.println("Upgrade has been Sold");
            gameManager.removeUpgrade(currentUpgrade);
            gameManager.changeMoneyAmount(currentUpgrade.getCost());
            updateComboBox();
        }
        else {
            errorLabel.setText("No Tower or Upgrade is selected to sell");
        }
    }

    @FXML
    private void buy() {
        errorLabel.setText("");
        if (shopTowersComboBox.getValue() != "") {
            if (gameManager.getMoneyAmount() - currentTower.getCost() < 0) {
                errorLabel.setText("You do not have enough money to buy this");
            } else {
                if (gameManager.getMainTowerList().size() >= 5) {
                    if (gameManager.getReserveTowerList().size() >= 5) {
                        errorLabel.setText("Max 5 Reserve Towers (Tower cannot be bought)");
                    } else {
                        errorLabel.setText("Max 5 Main Towers (Tower has been placed in reserve)");
                        gameManager.addReserveTower(currentTower);
                        gameManager.changeMoneyAmount(-1 * currentTower.getCost());
                        updateComboBox();
                    }
                } else {
                    if (shopTowersComboBox.getValue() != "") {
                        System.out.println("currentTower has been Bought");
                        gameManager.addMainTower(currentTower);
                        gameManager.changeMoneyAmount(-1 * currentTower.getCost());
                        updateComboBox();
                    }
                }
            }
        }
        else if (shopUpgradesComboBox.getValue() != "") {
            if (gameManager.getMoneyAmount() - currentUpgrade.getCost() < 0) {
                errorLabel.setText("You do not have enough money to buy this");
            } else {
                System.out.println("currentUpgrade has been Bought");
                gameManager.addUpgrade(currentUpgrade);
                gameManager.changeMoneyAmount(-1 * currentUpgrade.getCost());
                updateComboBox();
            }
        }
        else {
            errorLabel.setText("No Tower or Upgrade is selected to buy");
        }
    }
}

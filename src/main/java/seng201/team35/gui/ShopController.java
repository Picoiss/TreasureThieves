package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private Boolean isOwnTower;
    private Boolean isUpgrade;
    private GameManager gameManager;
    private List<Tower> shopTowerList = new ArrayList<>();

    public ShopController(GameManager x) {
        gameManager = x;
    }

    /**
     * Initialize the window
     */
    public void initialize() {
        shopTowerList = getShopTowerList(gameManager.getCurrentRound());
        shopTowersComboBox.getItems().setAll(Tower.getTowerNames(shopTowerList));
        shopUpgradesComboBox.getItems().setAll(Upgrade.getUpgradeNames(gameManager.getDefaultUpgrades()));
        livesLabel.setText(String.valueOf(gameManager.getLives()));
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        roundsLabel.setText(String.valueOf(gameManager.getCurrentRound()));
        roundsLeftLabel.setText(String.valueOf(gameManager.getNumOfRounds() - gameManager.getCurrentRound()));
        updateComboBox();
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

    @FXML
    private void sell() {
        if (isOwnTower == true) {
            if (isUpgrade == false) {
                if (mainTowersComboBox.getValue() != "") {
                    if (reserveTowersComboBox.getValue() == "") {
                        System.out.println("currentTower has been Sold");
                        gameManager.removeMainTower(currentTower);
                        gameManager.changeMoneyAmount(currentTower.getCost());
                        updateComboBox();
                    }
                }
                if (reserveTowersComboBox.getValue() == "") {
                    if (mainTowersComboBox.getValue() != "") {
                        System.out.println("currentTower has been Sold");
                        gameManager.removeMainTower(currentTower);
                        gameManager.changeMoneyAmount(currentTower.getCost());
                        updateComboBox();
                    }
                }

            }
            if (isUpgrade == true) {
                if (upgradesComboBox.getValue() != "") {
                    System.out.println("currentUpgrade has been Sold");
                    gameManager.removeUpgrade(currentUpgrade);
                    gameManager.changeMoneyAmount(currentUpgrade.getCost());
                    updateComboBox();
                }
            }
        }
    }

    @FXML
    private void buy() {
        if (isOwnTower == false) {
            if (isUpgrade == false) {
                if (gameManager.getMainTowerList().size() >= 5) {
                    errorLabel.setText("Max 5 Main Towers (Tower has been placed in reserve)");
                    gameManager.addReserveTower(currentTower);
                    gameManager.changeMoneyAmount(-1*currentTower.getCost());
                    updateComboBox();
                }
                if (gameManager.getMainTowerList().size() < 5) {
                    if (shopTowersComboBox.getValue() != "") {
                        System.out.println("currentTower has been Bought");
                        gameManager.addMainTower(currentTower);
                        gameManager.changeMoneyAmount(-1 * currentTower.getCost());
                        updateComboBox();
                    }
                }
            }
            if (isUpgrade == true) {
                if (shopUpgradesComboBox.getValue() != "") {
                    System.out.println("currentUpgrade has been Bought");
                    gameManager.addUpgrade(currentUpgrade);
                    gameManager.changeMoneyAmount(-1 * currentUpgrade.getCost());
                    updateComboBox();
                }
            }
        }
    }

    @FXML
    private void goToMainMenu() {
        gameManager.closeShopScreen();
    }

    private void displaySelectedTower() {
        towerName.setText("Tower name");
        speed.setText("Speed");
        level.setText("Level");
        resourceAmount.setText("Resource Amount");
        resourceType.setText("Resource Type");
        if (currentTower != null) {
            towerNamelabel.setText(currentTower.getName());
            speedLabel.setText(String.valueOf(currentTower.getReloadSpeed()));
            levelLabel.setText(String.valueOf(currentTower.getLevel()));
            costLabel.setText(String.valueOf(currentTower.getCost()));
            resourceAmountLabel.setText(String.valueOf(currentTower.getMaxAmount()));
            resourceTypeLabel.setText(String.valueOf(currentTower.getResourceType()));
            clearComboBoxes();
            currentCombobox = null;
        }
    }

    private void displaySelectedUpgrade() {
        towerName.setText("Upgrade type");
        speed.setText("Resource Boost");
        level.setText("Reload Speed Boost");
        resourceAmount.setText("");
        resourceAmountLabel.setText("");
        resourceTypeLabel.setText("");
        resourceType.setText("");
        if (currentUpgrade != null) {
            towerNamelabel.setText(currentUpgrade.getResourceType());
            speedLabel.setText(String.valueOf(currentUpgrade.getBoostResourceAmount()));
            levelLabel.setText(String.valueOf(currentUpgrade.getReduceReloadSpeed()));
            costLabel.setText(String.valueOf(currentUpgrade.getCost()));
            clearComboBoxes();
            currentCombobox = null;
        }
    }

    @FXML
    private void selectedShopTower() {
        if (currentCombobox == null) {
            isUpgrade = false;
            isOwnTower = false;
            currentCombobox = "ShopTower";
            currentTower = gameManager.getTowerClass(shopTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    @FXML
    private void selectedShopUpgrade() {
        if (currentCombobox == null) {
            isUpgrade = true;
            isOwnTower = false;
            currentCombobox = "ShopUpgrade";
            currentUpgrade = gameManager.getUpgradeClass(shopUpgradesComboBox.getValue().toString());
            displaySelectedUpgrade();
        }
    }

    @FXML
    private void selectedMainTower() {
        if (currentCombobox == null) {
            isUpgrade = false;
            isOwnTower = true;
            currentCombobox = "MainTower";
            currentTower = gameManager.getTowerClass(mainTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    @FXML
    private void selectedReserveTower() {
        if (currentCombobox == null) {
            isUpgrade = false;
            isOwnTower = true;
            currentCombobox = "ReserveTower";
            currentTower = gameManager.getTowerClass(reserveTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    @FXML
    private void selectedMyUpgrade() {
        if (currentCombobox == null) {
            isUpgrade = true;
            isOwnTower = true;
            currentCombobox = "MyUpgrade";
            currentUpgrade = gameManager.getUpgradeClass(upgradesComboBox.getValue().toString());
            displaySelectedUpgrade();
        }
    }

    private List<Tower> getShopTowerList(int currentRound) {
        List<Tower> shopTowerList = new ArrayList<>();
        switch (currentRound) {
            case 1:
                shopTowerList.addAll(gameManager.getDefaultTowers());
                shopTowerList.add(gameManager.getTowerClass("Bronze Archer"));
                shopTowerList.add(gameManager.getTowerClass("Bronze Dwarf"));
                shopTowerList.add(gameManager.getTowerClass("Silver Knight"));
                shopTowerList.add(gameManager.getTowerClass("Silver Priest"));
                break;
            case 2:
                shopTowerList.add(gameManager.getTowerClass("Bronze Villager"));
                shopTowerList.add(gameManager.getTowerClass("Silver Knight"));
                shopTowerList.add(gameManager.getTowerClass("Silver Alchemist"));
                shopTowerList.add(gameManager.getTowerClass("Gold Giant"));
                break;
            case 3:
                shopTowerList.add(gameManager.getTowerClass("Silver Alchemist"));
                shopTowerList.add(gameManager.getTowerClass("Gold Giant"));
                shopTowerList.add(gameManager.getTowerClass("Gold Goblin"));
                break;
            case 4:
                shopTowerList.add(gameManager.getTowerClass("Gold Wolf"));
                shopTowerList.add(gameManager.getTowerClass("Silver Priest"));
                shopTowerList.add(gameManager.getTowerClass("Bronze Villager"));
                break;
            case 5:
                shopTowerList.add(gameManager.getTowerClass("Diamond Mage"));
                shopTowerList.add(gameManager.getTowerClass("Silver Priest"));
                shopTowerList.add(gameManager.getTowerClass("Bronze Villager"));
                shopTowerList.add(gameManager.getTowerClass("Gold Wolf"));
                break;
            case 6:
                shopTowerList.add(gameManager.getTowerClass("Diamond Necromancer"));
                shopTowerList.add(gameManager.getTowerClass("Diamond Minotaur"));
                shopTowerList.add(gameManager.getTowerClass("Gold Goblin"));
                break;
            case 7:
                shopTowerList.addAll(getShopTowerList(1)); // this may need to be altered in the future as there will be duplicates in the shop i believe...
                shopTowerList.addAll(getShopTowerList(2));
                shopTowerList.addAll(getShopTowerList(3));
                shopTowerList.addAll(getShopTowerList(4));
                shopTowerList.addAll(getShopTowerList(5));
                shopTowerList.addAll(getShopTowerList(6));
                break;
            case 8:
                shopTowerList.add(gameManager.getTowerClass("Emerald Elf"));
                shopTowerList.add(gameManager.getTowerClass("Emerald Pheonix"));
                shopTowerList.addAll(getShopTowerList(7));
                break;
            case 9:
                shopTowerList.addAll(getShopTowerList(8));
                shopTowerList.add(gameManager.getTowerClass("Emerald Pegasus"));
                break;
            case 10:
                shopTowerList.add(gameManager.getTowerClass("Ruby Dragon"));
                shopTowerList.add(gameManager.getTowerClass("Ruby OrcMage"));
                shopTowerList.add(gameManager.getTowerClass("Ruby Golem"));
                break;
            default:
                shopTowerList.addAll(gameManager.getDefaultTowers());
        }
        return shopTowerList;
    }
}

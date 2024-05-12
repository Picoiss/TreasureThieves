package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

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
    private Upgrade currentUpgrade;
    private GameManager gameManager;
    public ShopController(GameManager x) { gameManager = x; }
    private Tower currentTower;
    private Boolean isOwnTower;
    private Boolean isUpgrade;
    /**
     * Initialize the window
     */
    public void initialize() {
        shopTowersComboBox.getItems().setAll(Tower.getTowerNames(gameManager.getDefaultTowers()));
        shopUpgradesComboBox.getItems().setAll(Upgrade.getUpgradeNames(gameManager.getDefaultUpgrades()));
        updateComboBox();
    }
    private void updateComboBox() {
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        mainTowersComboBox.getItems().setAll(Tower.getTowerNames(gameManager.getMainTowerList()));
        reserveTowersComboBox.getItems().setAll(Tower.getTowerNames(gameManager.getReserveTowerList()));
        upgradesComboBox.getItems().setAll(Upgrade.getUpgradeNames(gameManager.getUpgradesList()));
    }
    @FXML
    private void sell() {
        if (isOwnTower == true) {
            if(isUpgrade == false) {
                System.out.println("currentTower has been Sold");
                gameManager.removeMainTower(currentTower);
                gameManager.changeMoneyAmount(currentTower.getCost());
                updateComboBox();
            }
            if(isUpgrade == true) {
                System.out.println("currentUpgrade has been Sold");
                gameManager.removeUpgrade(currentUpgrade);
                gameManager.changeMoneyAmount(currentUpgrade.getCost());
                updateComboBox();
            }
        }
    }
    @FXML
    private void buy() {
        if (isOwnTower == false) {
            if(isUpgrade == false) {
                System.out.println("currentTower has been Bought");
                gameManager.addMainTower(currentTower);
                gameManager.changeMoneyAmount(-1*currentTower.getCost());
                updateComboBox();
            }
            if(isUpgrade == true) {
                System.out.println("currentUpgrade has been Bought");
                gameManager.addUpgrade(currentUpgrade);
                gameManager.changeMoneyAmount(-1*currentUpgrade.getCost());
                updateComboBox();
            }
        }
    }
    @FXML
    private void goToMainMenu() {
        gameManager.closeShopScreen();
    }
    @FXML
    private void selectedShopTower() {
        isUpgrade = false;
        isOwnTower = false;
        towerName.setText("Tower name");
        speed.setText("Speed");
        level.setText("Level");
        resourceAmount.setText("Resource Amount");
        resourceType.setText("Resource Type");
        currentTower = gameManager.getTowerClass(shopTowersComboBox.getValue().toString());
        towerNamelabel.setText(currentTower.getName());
        speedLabel.setText(String.valueOf(currentTower.getReloadSpeed()));
        levelLabel.setText(String.valueOf(currentTower.getLevel()));
        costLabel.setText(String.valueOf(currentTower.getCost()));
        resourceAmountLabel.setText(String.valueOf(currentTower.getMaxAmount()));
        resourceTypeLabel.setText(String.valueOf(currentTower.getResourceType()));
    }
    @FXML
    private void selectedShopUpgrade() {
        isUpgrade = true;
        isOwnTower = false;
        towerName.setText("Upgrade type");
        speed.setText("Resource Boost");
        level.setText("Reload Speed Boost");
        resourceAmount.setText("");
        resourceAmountLabel.setText("");
        resourceTypeLabel.setText("");
        resourceType.setText("");
        currentUpgrade = gameManager.getUpgradeClass(shopUpgradesComboBox.getValue().toString());
        towerNamelabel.setText(currentUpgrade.getResourceType());
        speedLabel.setText(String.valueOf(currentUpgrade.getBoostResourceAmount()));
        levelLabel.setText(String.valueOf(currentUpgrade.getReduceReloadSpeed()));
        costLabel.setText(String.valueOf(currentUpgrade.getCost()));
    }
    @FXML
    private void selectedMainTower() {
        isUpgrade = false;
        isOwnTower = true;
        towerName.setText("Tower name");
        speed.setText("Speed");
        level.setText("Level");
        resourceAmount.setText("Resource Amount");
        resourceType.setText("Resource Type");
        currentTower = gameManager.getTowerClass(mainTowersComboBox.getValue().toString());
        towerNamelabel.setText(currentTower.getName());
        speedLabel.setText(String.valueOf(currentTower.getReloadSpeed()));
        levelLabel.setText(String.valueOf(currentTower.getLevel()));
        costLabel.setText(String.valueOf(currentTower.getCost()));
        resourceAmountLabel.setText(String.valueOf(currentTower.getMaxAmount()));
        resourceTypeLabel.setText(String.valueOf(currentTower.getResourceType()));
    }
    @FXML
    private void selectedReserveTower() {
        isUpgrade = false;
        isOwnTower = true;
        towerName.setText("Tower name");
        speed.setText("Speed");
        level.setText("Level");
        resourceAmount.setText("Resource Amount");
        resourceType.setText("Resource Type");
        currentTower = gameManager.getTowerClass(reserveTowersComboBox.getValue().toString());
        towerNamelabel.setText(currentTower.getName());
        speedLabel.setText(String.valueOf(currentTower.getReloadSpeed()));
        levelLabel.setText(String.valueOf(currentTower.getLevel()));
        costLabel.setText(String.valueOf(currentTower.getCost()));
        resourceAmountLabel.setText(String.valueOf(currentTower.getMaxAmount()));
        resourceTypeLabel.setText(String.valueOf(currentTower.getResourceType()));
    }
    @FXML
    private void selectedMyUpgrade() {
        isUpgrade = true;
        isOwnTower = true;
        towerName.setText("Upgrade type");
        speed.setText("Resource Boost");
        level.setText("Reload Speed Boost");
        resourceAmount.setText("");
        resourceAmountLabel.setText("");
        resourceTypeLabel.setText("");
        resourceType.setText("");
        currentUpgrade = gameManager.getUpgradeClass(upgradesComboBox.getValue().toString());
        towerNamelabel.setText(currentUpgrade.getResourceType());
        speedLabel.setText(String.valueOf(currentUpgrade.getBoostResourceAmount()));
        levelLabel.setText(String.valueOf(currentUpgrade.getReduceReloadSpeed()));
        costLabel.setText(String.valueOf(currentUpgrade.getCost()));
    }
}

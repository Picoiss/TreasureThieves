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
    private GameManager gameManager;
    public ShopController(GameManager x) { gameManager = x; }
    /**
     * Initialize the window
     */
    public void initialize() {
        shopTowersComboBox.getItems().addAll(Tower.getTowerNames(gameManager.getDefaultTowers()));
        shopUpgradesComboBox.getItems().addAll(Upgrade.getUpgradeNames(gameManager.getDefaultUpgrades()));
        mainTowersComboBox.getItems().addAll(Tower.getTowerNames(gameManager.getMainTowerList()));
        //reserveTowersComboBox.getItems().addAll(Tower.getTowerNames(gameManager.getReserveTowerList()));
        //upgradesComboBox.getItems().addAll(Upgrade.getUpgradeNames(gameManager.getUpgradesList()));
    }
    @FXML
    public void sell() {}
    @FXML
    public void buy() {}
    @FXML
    public void goToMainMenu() {
        gameManager.closeShopScreen();
    }
}

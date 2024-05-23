package seng201.team35.gui;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.util.Objects;

/**
 * Controller for the Inventory.fxml window
 * @author nsr36, msh254
 */
public class InventoryController {
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
    private ComboBox mainTowersComboBox;
    @FXML
    private ComboBox reserveTowersComboBox;
    @FXML
    private ComboBox upgradesComboBox;
    @FXML
    private Button returnToMainMenuButton;
    @FXML
    private Label resourceTypeLabel;
    @FXML
    private Button swapReserveButton;
    @FXML
    private Button swapMainButton;
    @FXML
    private Button toggleUpgradeButton;
    @FXML
    private Label errorLabel;
    @FXML
    private Label livesLabel;
    @FXML
    private Label roundsLabel;
    @FXML
    private Label roundsLeftLabel;
    private String currentCombobox;
    private GameManager gameManager;

    public InventoryController(GameManager x) {
        gameManager = x;
    }

    private Tower currentTower;
    private Upgrade currentUpgrade;
    private Boolean isMainTower;
    private Boolean isUpgrade;

    public void initialize() {
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        livesLabel.setText(String.valueOf(gameManager.getLives()));
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        roundsLabel.setText(String.valueOf(gameManager.getCurrentRound()));
        roundsLeftLabel.setText(String.valueOf(gameManager.getNumOfRounds() - gameManager.getCurrentRound()));
        updateComboBox();
    }
    @FXML
    private void goToMainMenu() {
        gameManager.closeInventoryScreen();
    }

    public void updateComboBox() {
        mainTowersComboBox.setValue("");
        reserveTowersComboBox.setValue("");
        upgradesComboBox.setValue("");
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
            setDetailLabels(currentTower.getName(), String.valueOf(currentTower.getReloadSpeed()), String.valueOf(currentTower.getLevel()),
                    String.valueOf(currentTower.getCost()), String.valueOf(currentTower.getMaxAmount()), String.valueOf(currentTower.getResourceType()));
            clearComboBoxes();
            currentCombobox = null;
        }
    }

    private void displaySelectedUpgrade() {
        towerName.setText("Upgrade type");
        speed.setText("Resource Boost");
        level.setText("Reload Speed Boost");
        resourceAmount.setText("Status");
        resourceType.setText("");
        if (currentUpgrade != null) {
            setDetailLabels(currentUpgrade.getResourceType(), String.valueOf(currentUpgrade.getBoostResourceAmount()),
                    String.valueOf(currentUpgrade.getReduceReloadSpeed()), String.valueOf(currentUpgrade.getCost()), currentUpgrade.getStatus(), "");
            clearComboBoxes();
            currentCombobox = null;
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
    @FXML
    private void swapReserve() {
        if(gameManager.getReserveTowerList().size() >= 5) {
            errorLabel.setText("You can only have 5 reserve towers!");
        }
        else {
            if (mainTowersComboBox.getValue() != "") {
                gameManager.removeMainTower(currentTower);
                gameManager.addReserveTower(currentTower);
                updateComboBox();
            }
        }
    }
    @FXML
    private void swapMain() {
        if(gameManager.getMainTowerList().size() >= 5) {
            errorLabel.setText("You can only have 5 main towers!");
        }
        else {
            if (reserveTowersComboBox.getValue() != "") {
                gameManager.removeReserveTower(currentTower);
                gameManager.addMainTower(currentTower);
                updateComboBox();
            }
        }
    }
    @FXML
    private void toggleUpgrade() {
        if (upgradesComboBox.getValue() != "") {
            currentUpgrade.toggleStatus();
            updateComboBox();
        }
    }
}


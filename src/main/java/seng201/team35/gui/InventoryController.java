package seng201.team35.gui;
import javafx.fxml.FXML;
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
    private Label resourceTypeLabel;
    @FXML
    private Label errorLabel;
    @FXML
    private Label livesLabel;
    @FXML
    private Label roundsLabel;
    @FXML
    private Label roundsLeftLabel;
    private String currentCombobox;
    private Tower currentTower;
    private Upgrade currentUpgrade;
    private GameManager gameManager;

    /**
     * InventoryController Constructor
     * Pass in the gameManager
     * @author msh254
     * @param x GameManager instance
     */
    public InventoryController(GameManager x) {
        gameManager = x;
    }

    /**
     * Initialize the window
     * Set labels to display and reset combo boxes
     * @author msh254
     */
    public void initialize() {
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        livesLabel.setText(String.valueOf(gameManager.getLives()));
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        roundsLabel.setText(String.valueOf(gameManager.getCurrentRound()));
        roundsLeftLabel.setText(String.valueOf(gameManager.getNumOfRounds() - gameManager.getCurrentRound()));
        updateComboBox();
    }

    /**
     * Transition to main menu screen if main menu button is clicked
     * @author msh254
     */
    @FXML
    private void goToMainMenu() {
        gameManager.closeInventoryScreen();
    }

    /**
     * Reset all combo boxes and clear labels whenever a tower is swapped or upgrade is toggled
     * @author msh254, nsr36
     */
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

    /**
     * Clear all combo boxes expect the one which has been selected
     * @author nsr36
     */
    private void clearComboBoxes() {
        if (!Objects.equals(currentCombobox, "MainTower")) { mainTowersComboBox.setValue(""); }
        if (!Objects.equals(currentCombobox, "ReserveTower")) { reserveTowersComboBox.setValue(""); }
        if (!Objects.equals(currentCombobox, "MyUpgrade")) { upgradesComboBox.setValue(""); }
    }

    /**
     * Set all the detail labels
     * @author nsr36
     * @param towerNameLabelText name of tower or resource type of upgrade
     * @param speedLabelText speed of tower or resource amount boost of upgrade
     * @param levelLabelText level of tower or reload speed reduction of upgrade
     * @param costLabelText cost of tower or upgrade
     * @param resourceAmountLabelText resource amount of tower
     * @param resourceTypeLabelText resource type of tower
     */
    private void setDetailLabels(String towerNameLabelText, String speedLabelText, String levelLabelText,
                                 String costLabelText, String resourceAmountLabelText, String resourceTypeLabelText) {
        towerNamelabel.setText(towerNameLabelText);
        speedLabel.setText(speedLabelText);
        levelLabel.setText(levelLabelText);
        costLabel.setText(costLabelText);
        resourceAmountLabel.setText(resourceAmountLabelText);
        resourceTypeLabel.setText(resourceTypeLabelText);
    }

    /**
     * Make sure all labels are associated to tower-specific details
     * @author msh254, nsr36
     */
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

    /**
     * Make sure all labels are associated to upgrade-specific details
     * @author nsr36
     */
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

    /**
     * Display the main tower's details when its combo box is selected
     * @author nsr36
     */
    @FXML
    private void selectedMainTower() {
        if (currentCombobox == null) {
            currentCombobox = "MainTower";
            currentTower = gameManager.getTowerClass(mainTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    /**
     * Display the reserve tower's details when its combo box is selected
     * @author nsr36
     */
    @FXML
    private void selectedReserveTower() {
        if (currentCombobox == null) {
            currentCombobox = "ReserveTower";
            currentTower = gameManager.getTowerClass(reserveTowersComboBox.getValue().toString());
            displaySelectedTower();
        }
    }

    /**
     * Display the owned upgrade's details when its combo box is selected
     * @author nsr36
     */
    @FXML
    private void selectedMyUpgrade() {
        if (currentCombobox == null) {
            currentCombobox = "MyUpgrade";
            currentUpgrade = gameManager.getUpgradeClass(upgradesComboBox.getValue().toString());
            displaySelectedUpgrade();
        }
    }

    /**
     * Swap a tower from main to reserve if there is not already 5 towers in reserve
     * @author msh254, nsr36
     */
    @FXML
    private void swapReserve() {
        if(gameManager.getReserveTowerList().size() >= 5) {
            errorLabel.setText("You can only have 5 reserve towers!");
        }
        else {
            if (mainTowersComboBox.getValue() != "") {
                errorLabel.setText("");
                gameManager.removeMainTower(currentTower);
                gameManager.addReserveTower(currentTower);
                updateComboBox();
            }
        }
    }

    /**
     * Swap a tower from reserve to main if there is not already 5 towers in main
     * @author msh254, nsr36
     */
    @FXML
    private void swapMain() {
        if(gameManager.getMainTowerList().size() >= 5) {
            errorLabel.setText("You can only have 5 main towers!");
        }
        else {
            if (reserveTowersComboBox.getValue() != "") {
                errorLabel.setText("");
                gameManager.removeReserveTower(currentTower);
                gameManager.addMainTower(currentTower);
                updateComboBox();
            }
        }
    }

    /**
     * Change the upgrades status to false if true or true if false
     * @author msh254, nsr36
     */
    @FXML
    private void toggleUpgrade() {
        if (upgradesComboBox.getValue() != "") {
            currentUpgrade.toggleStatus();
            updateComboBox();
        }
    }
}


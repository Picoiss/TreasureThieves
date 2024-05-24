package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team35.GameManager;

/**
 * Controller for the winMenu.fxml window
 * @author nsr36
 */

public class WinMenuController {
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label numRoundsLabel;
    @FXML
    private Label currentRoundLabel;
    @FXML
    private Label totalMoneyLabel;
    @FXML
    private Label totalCartsDestroyedLabel;
    private GameManager gameManager;

    /**
     * WinMenuController Constructor
     * Pass in the gameManager
     * @author nsr36
     * @param x GameManager instance
     */
    public WinMenuController(GameManager x) { gameManager = x; }

    /**
     * Initialize the window and all the labels to display
     * @author nsr36
     */
    public void initialize() {
        playerNameLabel.setText(gameManager.getPlayerName());
        numRoundsLabel.setText(String.valueOf(gameManager.getNumOfRounds()));
        currentRoundLabel.setText(String.valueOf(gameManager.getCurrentRound()));
        totalMoneyLabel.setText(String.valueOf(gameManager.getTotalMoney()));
        totalCartsDestroyedLabel.setText(String.valueOf(gameManager.getTotalCartsDestroyed()));
    }

}

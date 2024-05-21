package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import seng201.team35.GameManager;

public class FailMenuController {
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
    public FailMenuController(GameManager x) { gameManager = x; }
    public void initialize() {
        playerNameLabel.setText(gameManager.getPlayerName());
        numRoundsLabel.setText(String.valueOf(gameManager.getNumOfRounds()));
        currentRoundLabel.setText(String.valueOf(gameManager.getNumOfRounds()));
        totalMoneyLabel.setText(String.valueOf(gameManager.getTotalMoney()));
        totalCartsDestroyedLabel.setText(String.valueOf(gameManager.getTotalCartsDestroyed()));
    }
}

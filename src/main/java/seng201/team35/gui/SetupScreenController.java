package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import seng201.team35.GameManager;
import seng201.team35.services.CounterService;

import java.awt.*;

/**
 * Controller for the gameSetup.fxml window
 * @author nsr36, msh254
 */
public class SetupScreenController {
    @FXML
    private TextField playerNameTextField;
    @FXML
    private Button continueButton;
    @FXML
    private Slider numRoundsSlider;
    @FXML
    private ComboBox difficultyComboBox;
    private String playerName;
    private int numRounds;
    private String gameDifficulty;
    private GameManager gameManager;
    public SetupScreenController(GameManager x) { gameManager = x; }
    /**
     * Initialize the window
     */

    @FXML
    public void getName() {
        playerName = playerNameTextField.getText();
    }
    public void getRounds() {
        numRounds = (int) numRoundsSlider.getValue();

    }
    public void getDifficulty() {
        gameDifficulty = (String) difficultyComboBox.getValue();
    }
    @FXML
    public void gameSetupComplete() {
        getName();
        getRounds();
        getDifficulty();
        System.out.println(playerName);
        System.out.println(numRounds);
        System.out.println(gameDifficulty);
        //is this where we switch screens? IN FUTURE add a call to the next window (Main Menu)
        gameManager.setPlayerName(playerName);
        gameManager.setNumOfRounds(numRounds);
        gameManager.setGameDifficulty(gameDifficulty);
        //Need to be able to select towers in setup
        //gameManager.setTowerList();
        gameManager.closeSetupScreen();
    }
}

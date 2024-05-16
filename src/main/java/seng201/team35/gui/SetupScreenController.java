package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import seng201.team35.GameManager;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @FXML
    private Label warningLabel;
    private String playerName;
    private int numRounds; // maxrounds
    private String gameDifficulty;
    private GameManager gameManager;

    public SetupScreenController(GameManager x) { gameManager = x; }
    /**
     * Initialize the window
     */

    public void initialize() {
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
    }
    @FXML
    public void getName() {
        playerName = playerNameTextField.getText();
    }
    public void getRounds() {
        numRounds = (int) numRoundsSlider.getValue();

    }
    @FXML
    public void getDifficulty() {
        gameDifficulty = (String) difficultyComboBox.getValue();
    }
    @FXML
    public void gameSetupComplete() {
        if (gameDifficulty == null) {
            warningLabel.setText("Please Select a Difficulty");
        }
        else {
            getName();
            playerName = playerName.trim();
            if (playerName.isBlank()) {
                warningLabel.setText("Please Enter a Name");
            }
            else if (playerName.length() < 3) {
                warningLabel.setText("Name must have at least 3 characters");
            }
            else if (playerName.length() > 15) {
                warningLabel.setText("Name must have 15 characters maximum");
            }
            else {
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(playerName);
                if (m.find()) {
                    warningLabel.setText("Name must not contain special characters");
                }
                else {
                    getRounds();
                    System.out.println(playerName);
                    System.out.println(numRounds);
                    System.out.println(gameDifficulty);
                    //is this where we switch screens? IN FUTURE add a call to the next window (Main Menu)
                    gameManager.setPlayerName(playerName);
                    gameManager.setNumOfRounds(numRounds);
                    gameManager.setGameDifficulty(gameDifficulty);
                    //Need to be able to select towers in setup
                    //gameManager.setTowerList();
                    if (gameDifficulty == "Easy") {
                        gameManager.setLives(15);
                        gameManager.changeMoneyAmount(200000);
                        System.out.println("Money set to easy");
                    }
                    if (gameDifficulty == "Medium") {
                        gameManager.setLives(10);
                        gameManager.changeMoneyAmount(150000);
                        System.out.println("Money set to medium");
                    }
                    if (gameDifficulty == "Hard") {
                        gameManager.setLives(5);
                        gameManager.changeMoneyAmount(100000);
                        System.out.println("Money set to hard");
                    }
                    gameManager.closeSetupScreen();
                }
            }
        }
    }
}

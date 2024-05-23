package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
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
    private Slider numRoundsSlider;
    @FXML
    private ComboBox difficultyComboBox;
    @FXML
    private ComboBox startingTower1Combo;
    @FXML
    private ComboBox startingTower2Combo;
    @FXML
    private ComboBox startingTower3Combo;
    @FXML
    private Label warningLabel;
    private String playerName;
    private int numRounds; // maximum rounds
    private String gameDifficulty;
    private static final int EASY_STARTING_LIVES = 10;
    private static final int MEDIUM_STARTING_LIVES = 5;
    private static final int HARD_STARTING_LIVES = 3;
    private static final int EASY_STARTING_MONEY = 250;
    private static final int MEDIUM_STARTING_MONEY = 150;
    private static final int HARD_STARTING_MONEY = 0;
    private GameManager gameManager;

    public SetupScreenController(GameManager x) { gameManager = x; }
    /**
     * Initialize the window
     */

    public void initialize() {
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        startingTower1Combo.getItems().addAll("Bronze Archer", "Bronze Dwarf", "Bronze Villager");
        startingTower2Combo.getItems().addAll("Bronze Archer", "Bronze Dwarf", "Bronze Villager");
        startingTower3Combo.getItems().addAll("Bronze Archer", "Bronze Dwarf", "Bronze Villager");
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
        getName();
        playerName = playerName.trim();
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(playerName);
        if (playerName.isBlank()) {
            warningLabel.setText("Please Enter a Name");
        }
        else if (playerName.length() < 3) {
            warningLabel.setText("Name must have at least 3 characters");
        }
        else if (playerName.length() > 15) {
            warningLabel.setText("Name must have 15 characters maximum");
        }
        else if (m.find()) {
            warningLabel.setText("Name must not contain special characters");
        }
        else if (gameDifficulty == null) {
            warningLabel.setText("Please Select a Difficulty");
        }
        else if (startingTower1Combo.getValue() == null || startingTower2Combo.getValue() == null || startingTower3Combo.getValue() == null) {
            warningLabel.setText("Please Select 3 Starting Towers");
        }
        else {
            getRounds();
            System.out.println(playerName);
            System.out.println(numRounds);
            System.out.println(gameDifficulty);
            gameManager.setPlayerName(playerName);
            gameManager.setNumOfRounds(numRounds);
            gameManager.setGameDifficulty(gameDifficulty);
            List<Tower> startingTowers = new ArrayList<>();
            startingTowers.add(gameManager.getTowerClass((String) startingTower1Combo.getValue()));
            startingTowers.add(gameManager.getTowerClass((String) startingTower2Combo.getValue()));
            startingTowers.add(gameManager.getTowerClass((String) startingTower3Combo.getValue()));
            gameManager.setMainTowerList(startingTowers);
            if (gameDifficulty == "Easy") {
                gameManager.setLives(EASY_STARTING_LIVES);
                gameManager.changeMoneyAmount(EASY_STARTING_MONEY);
                System.out.println("Money set to easy");
            }
            if (gameDifficulty == "Medium") {
                gameManager.setLives(MEDIUM_STARTING_LIVES);
                gameManager.changeMoneyAmount(MEDIUM_STARTING_MONEY);
                System.out.println("Money set to medium");
            }
            if (gameDifficulty == "Hard") {
                gameManager.setLives(HARD_STARTING_LIVES);
                gameManager.changeMoneyAmount(HARD_STARTING_MONEY);
                System.out.println("Money set to hard");
            }
            gameManager.closeSetupScreen();
        }
    }
}

package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final int EASYSTARTINGLIVES = 10;
    private final int MEDIUMSTARTINGLIVES = 5;
    private final int HARDSTARTINGLIVES = 3;
    private final int EASYSTARTINGMONEY = 200;
    private final int MEDIUMSTARTINGMONEY = 100;
    private final int HARDSTARTINGMONEY = 0;
    private GameManager gameManager;

    /**
     * SetupScreenController Constructor
     * Pass in the gameManager
     * @author nsr36
     * @param x GameManager instance
     */
    public SetupScreenController(GameManager x) { gameManager = x; }

    /**
     * Initialize the window
     * @author msh254, nsr36
     */
    public void initialize() {
        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        startingTower1Combo.getItems().addAll("Bronze Archer", "Bronze Dwarf", "Bronze Villager");
        startingTower2Combo.getItems().addAll("Bronze Archer", "Bronze Dwarf", "Bronze Villager");
        startingTower3Combo.getItems().addAll("Bronze Archer", "Bronze Dwarf", "Bronze Villager");
    }

    /**
     * Store the player's name they entered in the corresponding text field
     * @author msh254
     */
    @FXML
    public void getName() {
        playerName = playerNameTextField.getText();
    }

    /**
     * Store the number of rounds selected using the corresponding slider
     * @author msh254
     */
    public void getRounds() {
        numRounds = (int) numRoundsSlider.getValue();
    }

    /**
     * Store the difficulty selected using the corresponding combobox
     * @author msh254
     */
    @FXML
    public void getDifficulty() {
        gameDifficulty = (String) difficultyComboBox.getValue();
    }

    /**
     * When the continue button is pressed, check all the inputs
     * are valid before storing all the information in the
     * gameManager variables and progressing to the main menu screen.
     * Regex was implemented using
     * <a href="https://www.geeksforgeeks.org/java-program-to-check-whether-the-string-consists-of-special-characters/">this website</a>
     * @author msh254, nsr36
     */
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
            if (Objects.equals(gameDifficulty, "Easy")) {
                gameManager.setLives(EASYSTARTINGLIVES);
                gameManager.changeMoneyAmount(EASYSTARTINGMONEY);
                System.out.println("Money set to easy");
            }
            if (Objects.equals(gameDifficulty, "Medium")) {
                gameManager.setLives(MEDIUMSTARTINGLIVES);
                gameManager.changeMoneyAmount(MEDIUMSTARTINGMONEY);
                System.out.println("Money set to medium");
            }
            if (Objects.equals(gameDifficulty, "Hard")) {
                gameManager.setLives(HARDSTARTINGLIVES);
                gameManager.changeMoneyAmount(HARDSTARTINGMONEY);
                System.out.println("Money set to hard");
            }
            gameManager.closeSetupScreen();
        }
    }
}

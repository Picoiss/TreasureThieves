package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import seng201.team35.GameManager;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Controller for the Main Menu.fxml window
 * @author nsr36, msh254
 */
public class MainMenuController {
    @FXML
    private TextArea modifierText1;
    @FXML
    private TextArea modifierText2;
    @FXML
    private TextArea modifierText3;
    @FXML
    private Label warningLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label playerNameLabel;
    @FXML
    private Label livesLabel;
    @FXML
    private Label roundsLabel;
    @FXML
    private Label roundsLeftLabel;
    private final String[] MODIFIERS = {"Cart Speed Increase 10%", "Cart Speed Increase 5%", "Cart Speed Decrease 5%", "Cart Speed Decrease 10%",
            "Cart Number Decrease by 2", "Cart Number Decrease by 1", "Cart Number Increase by 1", "Cart Number Increase by 2", "Cart Fill Amount Decrease 10%",
            "Cart Fill Amount Increase 5%", "Cart Fill Amount Increase 10%", "Cart Fill Amount Decrease 5%"};
    private final List<String> MODIFIERSLIST = Arrays.asList(MODIFIERS);
    private Boolean modifierSelected = false;
    private String modifierName;
    private GameManager gameManager;
    public MainMenuController(GameManager x) { gameManager = x; }
    public void initialize() {
        Random rng = new Random();
        int randomModifier1 = rng.nextInt(0,4);
        int randomModifier2 = rng.nextInt(4,8);
        int randomModifier3 = rng.nextInt(8,12);
        modifierText1.setText(MODIFIERSLIST.get(randomModifier1));
        modifierText2.setText(MODIFIERSLIST.get(randomModifier2));
        modifierText3.setText(MODIFIERSLIST.get(randomModifier3));
        playerNameLabel.setText(gameManager.getPlayerName());
        livesLabel.setText(String.valueOf(gameManager.getLives()));
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        roundsLabel.setText(String.valueOf(gameManager.getCurrentRound()));
        roundsLeftLabel.setText(String.valueOf(gameManager.getNumOfRounds() - gameManager.getCurrentRound()));
    }
    @FXML
    public void shopClicked() {
        gameManager.mainMenuToShopScreen();
    }
    @FXML
    public void inventoryClicked() {
        gameManager.mainMenuToInventoryScreen();
    }
    @FXML
    public void nextRoundClicked() {
        System.out.println("NextRoundClicked");
        if (modifierSelected) {
            if (gameManager.getMainTowerList().size() >= 3) {
                System.out.println("ModifierBeenSelected");
                gameManager.setModifier(modifierName);
                gameManager.mainMenuToGameScreen();
            }
            else {
                warningLabel.setText("Please have at least 3 main towers");
            }
        }
        else {
            System.out.println("ModifierNotSelected");
            System.out.println(MODIFIERSLIST);
            warningLabel.setText("Please select a Modifier");
        }
    }
    @FXML
    public void modifier1Clicked() {
        modifierSelected = true;
        modifierName = modifierText1.getText();
        System.out.println(modifierName);
        System.out.println("Modifier 1 has been selected");

    }
    @FXML
    public void modifier2Clicked() {
        modifierSelected = true;
        modifierName = modifierText2.getText();
        System.out.println(modifierName);
        System.out.println("Modifier 2 has been selected");
    }
    @FXML
    public void modifier3Clicked() {
        modifierSelected = true;
        modifierName = modifierText3.getText();
        System.out.println(modifierName);
        System.out.println("Modifier 3 has been selected");
    }
}

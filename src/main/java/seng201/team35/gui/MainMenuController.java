package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import seng201.team35.GameManager;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
    private String modifierName;
    private GameManager gameManager;
    public MainMenuController(GameManager x) { gameManager = x; }
    public void initialize() {
        if (gameManager.getModifiersInitialised() == false) {
            System.out.println("Modifiers False");
            Random rng = new Random();
            int randomModifier1 = rng.nextInt(0,4);
            int randomModifier2 = rng.nextInt(4,8);
            int randomModifier3 = rng.nextInt(8,12);
            String modifier1 = MODIFIERSLIST.get(randomModifier1);
            String modifier2 = MODIFIERSLIST.get(randomModifier2);
            String modifier3 = MODIFIERSLIST.get(randomModifier3);
            gameManager.setGlobalModifier1(modifier1);
            gameManager.setGlobalModifier2(modifier2);
            gameManager.setGlobalModifier3(modifier3);
            modifierText1.setText(MODIFIERSLIST.get(randomModifier1));
            modifierText2.setText(MODIFIERSLIST.get(randomModifier2));
            modifierText3.setText(MODIFIERSLIST.get(randomModifier3));
            modifierText1.setStyle("-fx-text-fill: black;");
            modifierText2.setStyle("-fx-text-fill: black;");
            modifierText3.setStyle("-fx-text-fill: black;");
            gameManager.setModifiersInitialisedTrue();
            gameManager.setModifier("If you see this... uh");
        }
        else {
            System.out.println("Modifiers True");
            modifierText1.setText(gameManager.getGlobalModifier1());
            modifierText2.setText(gameManager.getGlobalModifier2());
            modifierText3.setText(gameManager.getGlobalModifier3());
            System.out.println(modifierText1.getText() + ", " + gameManager.getModifier());
            if (Objects.equals(modifierText1.getText(), gameManager.getModifier())) {
                modifierText1.setStyle("-fx-text-fill: green;");
            }
            if (Objects.equals(modifierText2.getText(), gameManager.getModifier())) {
                modifierText2.setStyle("-fx-text-fill: green;");
            }
            if (Objects.equals(modifierText3.getText(), gameManager.getModifier())) {
                modifierText3.setStyle("-fx-text-fill: green;");
            }
        }
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
        if ((gameManager.getModifierSelected())) {
            if (gameManager.getMainTowerList().size() >= 3) {
                System.out.println("ModifierBeenSelected");
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

        gameManager.setModifierSelectedTrue();
        modifierName = modifierText1.getText();
        gameManager.setModifier(modifierName);
        System.out.println(modifierName);
        modifierText1.setStyle("-fx-text-fill: green;");
        modifierText2.setStyle("-fx-text-fill: black;");
        modifierText3.setStyle("-fx-text-fill: black;");
        System.out.println("Modifier 1 has been selected");

    }
    @FXML
    public void modifier2Clicked() {

        gameManager.setModifierSelectedTrue();
        modifierName = modifierText2.getText();
        gameManager.setModifier(modifierName);
        System.out.println(modifierName);
        modifierText2.setStyle("-fx-text-fill: green;");
        modifierText1.setStyle("-fx-text-fill: black;");
        modifierText3.setStyle("-fx-text-fill: black;");
        System.out.println("Modifier 2 has been selected");
    }
    @FXML
    public void modifier3Clicked() {
        gameManager.setModifierSelectedTrue();
        modifierName = modifierText3.getText();
        gameManager.setModifier(modifierName);
        modifierText3.setStyle("-fx-text-fill: green;");
        modifierText1.setStyle("-fx-text-fill: black;");
        modifierText2.setStyle("-fx-text-fill: black;");
        System.out.println(modifierName);
        System.out.println("Modifier 3 has been selected");
    }
}

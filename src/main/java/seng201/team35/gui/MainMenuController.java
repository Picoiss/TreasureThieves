package seng201.team35.gui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import seng201.team35.GameManager;
import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.util.*;

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
    @FXML
    private Label levelUpLabel;
    private final String[] MODIFIERS = {"Cart Speed Increase 10%", "Cart Speed Increase 5%", "Cart Speed Decrease 5%", "Cart Speed Decrease 10%",
            "Cart Number Decrease by 2", "Cart Number Decrease by 1", "Cart Number Increase by 1", "Cart Number Increase by 2", "Cart Fill Amount Decrease 10%",
            "Cart Fill Amount Increase 5%", "Cart Fill Amount Increase 10%", "Cart Fill Amount Decrease 5%"};
    private final List<String> MODIFIERSLIST = Arrays.asList(MODIFIERS);
    private String modifierName;
    private GameManager gameManager;

    /**
     * MainMenuController Constructor
     * Pass in the gameManager
     * @author msh254
     * @param x GameManager instance
     */
    public MainMenuController(GameManager x) { gameManager = x; }

    /**
     * Initialize the window
     * Make sure modifiers are reset after playing a round
     * Otherwise make sure modifiers are unchanged
     * Set all the labels to display and check if towers need to be leveled up
     * @author msh254, nsr36
     */
    public void initialize() {
        playerNameLabel.setText(gameManager.getPlayerName());
        livesLabel.setText(String.valueOf(gameManager.getLives()));
        moneyLabel.setText(String.valueOf(gameManager.getMoneyAmount()));
        roundsLabel.setText(String.valueOf(gameManager.getCurrentRound()));
        roundsLeftLabel.setText(String.valueOf(gameManager.getNumOfRounds() - gameManager.getCurrentRound()));
        if (!gameManager.getModifiersInitialised()) {
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
            if (gameManager.getCurrentRound() >= 2) {
                checkBrokenTowers();
            }
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
            checkTowerLevelUps();
        }
    }

    /**
     * Transition to shop screen if shop button is clicked
     * @author msh254, nsr36
     */
    @FXML
    public void shopClicked() {
        gameManager.mainMenuToShopScreen();
    }

    /**
     * Transition to inventory screen if inventory button is clicked
     * @author msh254, nsr36
     */
    @FXML
    public void inventoryClicked() {
        gameManager.mainMenuToInventoryScreen();
    }

    /**
     * If next round button is clicked, check if a modifier has been
     * selected and player has at least 3 main towers, then transition to game screen
     * @author msh254, nsr36
     */
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

    /**
     * Set the selected modifier to modifier1
     * Set modifier1 text to green and others to black when its button is clicked
     * @author msh254, nsr36
     */
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

    /**
     * Set the selected modifier to modifier2
     * Set modifier2 text to green and others to black when its button is clicked
     * @author msh254, nsr36
     */
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

    /**
     * Set the selected modifier to modifier3
     * Set modifier3 text to green and others to black when its button is clicked
     * @author msh254, nsr36
     */
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

    /**
     * Check if player owns three or more active upgrades of the same type
     * If true, level up all owned towers of the same type and remove a maximum of three upgrades of that type
     * Display a message using the levelUpLabel
     * @author nsr36
     */
    public void checkTowerLevelUps() {
        Map<String, Integer> numOfUpgrades = new HashMap<>();
        String[] keys = {"Bronze", "Silver", "Gold", "Diamond", "Emerald", "Ruby"};
        for (String key : keys) {
            numOfUpgrades.put(key, 0);
        }
        for (int i = 0; i < gameManager.getUpgradesList().size(); i++) {
            if (Objects.equals(gameManager.getUpgradesList().get(i).getStatus(), "Active")) {
                numOfUpgrades.replace(gameManager.getUpgradesList().get(i).getResourceType(), numOfUpgrades.get(gameManager.getUpgradesList().get(i).getResourceType()) + 1);
            }
        }
        for (Map.Entry<String, Integer> type : numOfUpgrades.entrySet()) {
            if (type.getValue() >= 3) {
                levelUpLabel.setText("All your " + type.getKey() + " towers have leveled up!");
                levelUpLabel.setOpacity(1);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(3), new KeyValue(levelUpLabel.opacityProperty(), 0.7)),
                        new KeyFrame(Duration.seconds(4), new KeyValue(levelUpLabel.opacityProperty(), 0))
                );
                timeline.setOnFinished(event -> levelUpLabel.setText(""));
                timeline.play();
                for (int i = 0; i < gameManager.getMainTowerList().size(); i++) {
                    if (Objects.equals(type.getKey(), gameManager.getMainTowerList().get(i).getResourceType())) {
                        gameManager.getMainTowerList().get(i).increaseLevel();
                        gameManager.getMainTowerList().get(i).increaseMaxAmount(gameManager.getMainTowerList().get(i).getMaxAmount()/10);
                    }
                }
                for (int i = 0; i < gameManager.getReserveTowerList().size(); i++) {
                    if (Objects.equals(type.getKey(), gameManager.getReserveTowerList().get(i).getResourceType())) {
                        gameManager.getReserveTowerList().get(i).increaseLevel();
                        gameManager.getReserveTowerList().get(i).increaseMaxAmount(gameManager.getReserveTowerList().get(i).getMaxAmount() / 10);
                    }
                }
                int i = 0;
                int removed = 0;
                while (i < gameManager.getUpgradesList().size() && removed <= 3) {
                    if (Objects.equals(type.getKey(), gameManager.getUpgradesList().get(i).getResourceType())) {
                        gameManager.removeUpgrade(gameManager.getUpgradesList().get(i));
                        removed++;
                    }
                    else {
                        i++;
                    }
                }
            }
        }
    }

    /**
     * Randomly generates broken towers or reload speed boosts
     * If tower broken, it is removed from the player's inventory (towers used in previous round have higher chance of breaking)
     * If a tower is selected for a boost, its reload speed is increased by 1 or if it is already maxed at 6, its resource amount is increased by 10
     * Display a message using the levelUpLabel
     * @author nsr36
     */
    public void checkBrokenTowers() {
        if (!gameManager.getTowersUsedInPreviousRound().isEmpty()) {
            Random rng = new Random();
            Tower randomTower = null;
            int randomBreak = rng.nextInt(11);
            if (randomBreak == 7) {
                int randIndex = rng.nextInt(gameManager.getMainTowerList().size());
                for (int i = 0; i < gameManager.getMainTowerList().size(); i++) {
                    if (randIndex == i) {
                        if (gameManager.getMainTowerList().get(i).getReloadSpeed() < 6) {
                            gameManager.getMainTowerList().get(i).increaseReloadSpeed();
                        }
                        else {
                            gameManager.getMainTowerList().get(i).increaseMaxAmount(10);
                        }
                        break;
                    }
                }
                randomTower = gameManager.getMainTowerList().get(randIndex);
            }
            else if (randomBreak == 8) {
                randomTower = gameManager.getReserveTowerList().get(rng.nextInt(gameManager.getReserveTowerList().size()));
                for (Tower tower : gameManager.getReserveTowerList()) {
                    if (tower == randomTower) {
                        gameManager.removeReserveTower(tower);
                        break;
                    }
                }
            }
            else if (randomBreak == 9) {
                randomTower = gameManager.getMainTowerList().get(rng.nextInt(gameManager.getMainTowerList().size()));
            }
            else if (randomBreak == 10) {
                randomTower = gameManager.getMainTowerList().get(rng.nextInt(gameManager.getTowersUsedInPreviousRound().size()));
            }
            else {
                randomTower = null;
            }
            if (randomTower != null) {
                if (randomBreak == 7) {
                    levelUpLabel.setText("Your " + randomTower.getName() + "'s reload speed was boosted!");
                }
                else {
                    levelUpLabel.setText("Your " + randomTower.getName() + " has broken down (removed from inventory)");
                }
                levelUpLabel.setOpacity(1);
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(3), new KeyValue(levelUpLabel.opacityProperty(), 0.7)),
                        new KeyFrame(Duration.seconds(4), new KeyValue(levelUpLabel.opacityProperty(), 0))
                );
                timeline.setOnFinished(event -> levelUpLabel.setText(""));
                timeline.play();
                if (randomBreak >= 9) {
                    for (Tower tower : gameManager.getMainTowerList()) {
                        if (tower == randomTower) {
                            gameManager.removeMainTower(tower);
                            break;
                        }
                    }
                }
            }
        }
    }
}

package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seng201.team35.GameManager;

import java.io.IOException;

public class FXWrapper {
    @FXML
    private Pane pane;

    private Stage stage;


    public void init(Stage stage) {
        this.stage = stage;
        new GameManager(this::launchSetupScreen,
                this::launchMainMenuScreen,
                this::launchShopScreen,
                this::launchInventoryScreen,
                this::launchGameScreen,
                this::launchFailMenuScreen,
                this::launchWinMenuScreen,
                this::clearPane);
    }

    public void launchSetupScreen(GameManager gameManager) {
        try {
            FXMLLoader setupLoader = new FXMLLoader(getClass().getResource("/fxml/gameSetup.fxml"));
            // provide a custom Controller with parameters
            setupLoader.setControllerFactory(param -> new SetupScreenController(gameManager));
            Parent setupParent  = setupLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Game Setup");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearPane() {
        pane.getChildren().removeAll(pane.getChildren());
    }

    public void launchMainMenuScreen(GameManager gameManager) {
        try {
            FXMLLoader mainMenuScreenLoader = new FXMLLoader(getClass().getResource("/fxml/Main Menu.fxml"));
            mainMenuScreenLoader.setControllerFactory(param -> new MainMenuController(gameManager));
            Parent setupParent  = mainMenuScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Game Main Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchShopScreen(GameManager gameManager) {
        try {
            FXMLLoader shopScreenLoader = new FXMLLoader(getClass().getResource("/fxml/gameShop.fxml"));
            shopScreenLoader.setControllerFactory(param -> new ShopController(gameManager));
            Parent setupParent  = shopScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Game Shop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchInventoryScreen(GameManager gameManager) {
        try {
            FXMLLoader inventoryScreenLoader = new FXMLLoader(getClass().getResource("/fxml/gameInventory.fxml"));
            inventoryScreenLoader.setControllerFactory(param -> new InventoryController(gameManager));
            Parent setupParent  = inventoryScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Game Inventory");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchGameScreen(GameManager gameManager) {
        try {
            FXMLLoader gameScreenLoader = new FXMLLoader(getClass().getResource("/fxml/________.fxml"));
            gameScreenLoader.setControllerFactory(param -> new GameController(gameManager));
            Parent setupParent  = gameScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("Gameplay");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchFailMenuScreen(GameManager gameManager) {
        try {
            FXMLLoader failScreenLoader = new FXMLLoader(getClass().getResource("/fxml/failMenu.fxml"));
            failScreenLoader.setControllerFactory(param -> new FailMenuController(gameManager));
            Parent setupParent  = failScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("You Lost!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void launchWinMenuScreen(GameManager gameManager) {
        try {
            FXMLLoader winScreenLoader = new FXMLLoader(getClass().getResource("/fxml/winMenu.fxml"));
            winScreenLoader.setControllerFactory(param -> new WinMenuController(gameManager));
            Parent setupParent  = winScreenLoader.load();
            pane.getChildren().add(setupParent);
            stage.setTitle("You Won!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


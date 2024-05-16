package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.animation.AnimationTimer;
import seng201.team35.GameManager;
import seng201.team35.models.Cart;
import seng201.team35.models.Track;

import java.awt.Point;

public class GameController {
    @FXML
    private GridPane gameGrid;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button startGameButton;

    private Cart cart;
    private Track track;
    private boolean gameRunning = false;
    private long lastUpdate = 0;
    private final long updateInterval = 500_000_000;
    private GameManager gameManager;
    private Circle gameToken;

    public GameController(GameManager x) {
        gameManager = x;
    }

    @FXML
    public void initialize() {
        track = new Track();
        cart = new Cart(15, "Gold", track.getWaypoints());
        Point startPosition = cart.getPosition();
        createGameToken(startPosition);
        setupGameLoop();
    }

    private void createGameToken(Point startPosition) {
        gameToken = new Circle(10);
        gameToken.setFill(Color.GOLD);
        gameToken.setLayoutX(startPosition.x);
        gameToken.setLayoutY(startPosition.y);
        gamePane.getChildren().add(gameToken);
    }

    private void setupGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameRunning && now - lastUpdate >= updateInterval) {
                    updateGame();
                    //check finish conditions
                    // check if cart fill
                    //check if tower empty

                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();
    }

    @FXML
    private void gameStarted() {
        gameRunning = true;
        lastUpdate = System.nanoTime();
    }

    private void updateGame() {
        cart.move();
        Point currentPosition = cart.getPosition();
        gameToken.setLayoutX(currentPosition.x);
        gameToken.setLayoutY(currentPosition.y); //maybe has to be inside the cart function
    }

    @FXML
    private void stopGame() {
        gameRunning = false;
    }
}

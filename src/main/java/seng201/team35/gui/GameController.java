package seng201.team35.gui;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.util.Duration;
import seng201.team35.GameManager;
import seng201.team35.models.Cart;
import seng201.team35.models.Track;
import seng201.team35.models.Path;
import seng201.team35.models.Tower;
import seng201.team35.models.SpriteSheet;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class GameController {
    @FXML
    private GridPane gameGrid;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private Button startGameButton;
    @FXML
    private ComboBox<String> towerSelectionComboBox;

    private Cart cart;
    private Track track;
    private boolean gameRunning = false;
    private long lastUpdate = 0;
    private final long updateInterval = 500_000_000;
    private GameManager gameManager;
    private Circle gameToken;
    private Image grassImage;
    private int[][] currentIndexGraph;
    private String currentTower;
    private Map<Point, ImageView> towerPositions;
    private SpriteSheet spriteSheet;

    public GameController(GameManager x) {
        gameManager = x;
        towerPositions = new HashMap<>();
        spriteSheet = new SpriteSheet();
    }

    @FXML
    public void initialize() {
        track = new Track();
        cart = new Cart(15, "Gold", track.getWaypoints()); // no idea why this is still here but..
        grassImage = new Image(getClass().getResourceAsStream("/images/Grass.png")); // this is the grass field
        Point startPosition = cart.getPosition();
        createGameToken(startPosition);
        setupGameLoop();
        loadGroundAssets();
        drawPathForRound(1); // change 1 to roundNum... eventually !!

        // put our towers into the combo Boxes
        towerSelectionComboBox.getItems().addAll(Tower.getTowerNames(gameManager.getMainTowerList()));

        //Event Hander for clicking (in the gameGrid gridpane)
        gameGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleGridClick);

        updateComboBox();
    }

    private void loadGroundAssets() { // this is for loading the grass field
        int rows = 20;
        int columns = 18;

        for (int row = 0; row < rows; row++) { // this iterates through the whole gridpane..
            for (int col = 0; col < columns; col++) {
                addTileToGrid(grassImage, 17, 0, 15, 16, col, row); // Using the grass part
            }
        }
    }

    private void drawPathForRound(int roundNumber) {
        Path path = Path.getPathForRound(roundNumber); // get the path from the Path class
        currentIndexGraph = path.getIndexGraph();
        for (int row = 0; row < currentIndexGraph.length; row++) {
            for (int col = 0; col < currentIndexGraph[row].length; col++) {
                if (currentIndexGraph[row][col] == 1) { // iterates through whole gridpane through the arraylist.. no what forgot what its called. thing in path class.
                    addTileToGrid(grassImage, 49, 0, 15, 16, col, row); // Using the path part ( will change to water.. maybe? have no cart sprites)
                }
            }
        }
    }

    private void addTileToGrid(Image image, int x, int y, int width, int height, int colIndex, int rowIndex) {
        ImageView tile = new ImageView(image);
        tile.setViewport(new Rectangle2D(x, y, width, height));
        tile.setFitWidth(50); //resize to fit the grid
        tile.setFitHeight(50);
        tile.setSmooth(false); // Disable image smoothing NOTE: do this for sprites.. too.
        gameGrid.add(tile, colIndex, rowIndex);
    }

    @FXML
    private void handleGridClick(MouseEvent event) {
        // Check if a tower is selected
        if (currentTower == null || currentTower.isEmpty()) {
            return;
        }

        // Calculate the clicked cell
        Point2D localPoint = gameGrid.sceneToLocal(event.getSceneX(), event.getSceneY()); // yet to understand this part..
        int colIndex = (int) (localPoint.getX() / (gameGrid.getWidth() / gameGrid.getColumnCount()));
        int rowIndex = (int) (localPoint.getY() / (gameGrid.getHeight() / gameGrid.getRowCount()));

        // this checks if the click is within the gridpane.
        if (colIndex >= 0 && colIndex < 18 && rowIndex >= 0 && rowIndex < 20) {
            // check if the place we want to put tower is not ap ath ( extend if we add trees or rocks or whatever.. )
            if (currentIndexGraph[rowIndex][colIndex] == 0) {
                addTower(colIndex, rowIndex);
                updateComboBox();
            }
        }
    }

    private void addTower(int colIndex, int rowIndex) {
        ImageView towerSprite = new ImageView();
        towerSprite.setFitWidth(50); //adjust again
        towerSprite.setFitHeight(50);
        towerPositions.put(new Point(colIndex, rowIndex), towerSprite);
        gameGrid.add(towerSprite, colIndex, rowIndex);// note somewhere here disable smoothing.
        animateTower(towerSprite, currentTower);
        currentTower = null; // Clear the current tower after placing it (note : can we use ""? )
    }

    private void animateTower(ImageView towerSprite, String towerName) {
        final int frameCount = spriteSheet.getFrameCount();
        AnimationTimer animationTimer = new AnimationTimer() {
            private int frameIndex = 0;
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= updateInterval / frameCount) {
                    Image frame = spriteSheet.getSpriteFrame(towerName, frameIndex);
                    towerSprite.setImage(frame);
                    frameIndex = (frameIndex + 1) % frameCount;
                    lastUpdate = now;
                }
            }
        };
        animationTimer.start();
    }

    private void createGameToken(Point startPosition) { // this... is for adding the carts..
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
        if (cart.hasNextWaypoint()) {
            cart.nextWaypoint();
            moveCartSmoothly();
        }
    }

    private void moveCartSmoothly() { // again.. this is for the carts using TranslateTransition.
        Point currentPosition = cart.getPosition();
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), gameToken);
        transition.setToX(currentPosition.x);
        transition.setToY(currentPosition.y);
        transition.play();
    }

    @FXML
    private void stopGame() {
        gameRunning = false;
    }

    @FXML
    private void towerSelected() {
        currentTower = towerSelectionComboBox.getValue();
    }

    private void updateComboBox() {
        towerSelectionComboBox.setValue("");
        towerSelectionComboBox.getItems().setAll(Tower.getTowerNames(gameManager.getMainTowerList()));
    }
}

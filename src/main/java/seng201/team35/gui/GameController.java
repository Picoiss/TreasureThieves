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
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import seng201.team35.GameManager;
import seng201.team35.models.Cart;
import seng201.team35.models.Path;
import seng201.team35.models.Tower;
import seng201.team35.models.SpriteSheet;
import seng201.team35.models.CartRound;
import seng201.team35.models.CartRound.CartSpawn;
import seng201.team35.models.CartPath;
import seng201.team35.models.CartDirectionMap;
import seng201.team35.models.CartSprite;

import java.awt.Point;
import java.util.HashMap;
import java.util.List;
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
    private long startTime;
    private boolean gameRunning = false;
    private long lastUpdate = 0;
    private final long updateInterval = 500_000_000;
    private GameManager gameManager;
    private Circle gameToken;
    private Image grassImage;
    private Image texturedGrassImage;
    private int[][] currentIndexGraph;
    private String currentTower;
    private Map<Point, ImageView> towerPositions;
    private SpriteSheet spriteSheet;
    private CartSprite cartSprite;
    private List<CartSpawn> carts;
    private int currentCartIndex = 0;
    private CartPath cartPath;
    private CartDirectionMap cartDirectionMap;
    private Map<Cart, ImageView> cartTokens = new HashMap<>();
    private Map<Cart, Integer> cartSteps = new HashMap<>();
    private boolean isMoving = false;

    public GameController(GameManager x) {
        gameManager = x;
        towerPositions = new HashMap<>();
        spriteSheet = new SpriteSheet();
        cartSprite = new CartSprite();
    }

    @FXML
    public void initialize() {
        grassImage = new Image(getClass().getResourceAsStream("/images/Grass.png"));
        texturedGrassImage = new Image(getClass().getResourceAsStream("/images/TexturedGrass.png"));
        setupGameLoop();
        loadGroundAssets(); // load the ground (path and grass etc).
        drawPathForRound(1); // Example for round 1

        // Populate the ComboBox with tower names from the GameManager
        towerSelectionComboBox.getItems().addAll(Tower.getTowerNames(gameManager.getMainTowerList()));

        // Add click handler to the grid maybe we can jst add a fxml code instead?
        gameGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleGridClick);

        updateComboBox();
    }

    private void loadGroundAssets() {
        int rows = 20;
        int columns = 18;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                addTileToGrid(texturedGrassImage, 15, 0, 32, 32, col, row); // Using the textured grass part for more detail
            }
        }
    }

    private void drawPathForRound(int roundNumber) {
        Path path = Path.getPathForRound(roundNumber);
        currentIndexGraph = path.getIndexGraph(); // Store the current index graph
        for (int row = 0; row < currentIndexGraph.length; row++) {
            for (int col = 0; col < currentIndexGraph[row].length; col++) {
                if (currentIndexGraph[row][col] == 1) {
                    addTileToGrid(grassImage, 0, 0, 15, 16, col, row); // Using the canal part (boats)
                }
            }
        }
    }

    private void addTileToGrid(Image image, int x, int y, int width, int height, int colIndex, int rowIndex) {
        ImageView tile = new ImageView(image);
        tile.setViewport(new Rectangle2D(x, y, width, height));
        tile.setFitWidth(50); // Set the width to 50 pixels
        tile.setFitHeight(50); // Set the height to 50 pixels
        tile.setSmooth(false); // Disable image smoothing (need to do with sprites)
        gameGrid.add(tile, colIndex, rowIndex);
    }

    @FXML
    private void handleGridClick(MouseEvent event) {
        // Check if a tower is selected
        if (currentTower == null || currentTower.isEmpty()) {
            return;
        }

        // Calculate the clicked cell
        Point2D localPoint = gameGrid.sceneToLocal(event.getSceneX(), event.getSceneY());
        // this one gets the point clicked then divides by gameGrid width (1000) and the column count to get the specific
        //column
        int colIndex = (int) (localPoint.getX() / (gameGrid.getWidth() / gameGrid.getColumnCount()));
        //same logic with the row index
        int rowIndex = (int) (localPoint.getY() / (gameGrid.getHeight() / gameGrid.getRowCount()));

        // Ensure the click is within the grid bounds
        //check if the clicked grid is indeed within boundaries
        if (colIndex >= 0 && colIndex < 18 && rowIndex >= 0 && rowIndex < 20) {
            // Check if the selected cell is not a path
            if (currentIndexGraph[rowIndex][colIndex] == 0) {
                addTower(colIndex, rowIndex);
                updateComboBox();
            }
        }
    }

    private void addTower(int colIndex, int rowIndex) {
        ImageView towerSprite = new ImageView();
        towerSprite.setFitWidth(50); // Adjust as needed
        towerSprite.setFitHeight(50); // Adjust as needed
        towerSprite.setSmooth(false);
        towerPositions.put(new Point(colIndex, rowIndex), towerSprite);
        gameGrid.add(towerSprite, colIndex, rowIndex);
        animateTower(towerSprite, currentTower);
        currentTower = null; // Clear the current tower after placing it
    }

    private void animateTower(ImageView towerSprite, String towerName) {
        // ImageView object displays the towerSprite.
        // a towername
        // these are the parameters for animateTower
        final int frameCount = spriteSheet.getFrameCount();
        // total number of frames for the given tower. gotten from spriteSheet.

        AnimationTimer animationTimer = new AnimationTimer() {
            // Animation timer created to handle the animation
            //similar to the one calling the update, this one allows an update every handle
            private int frameIndex = 0;
            private long lastUpdate = 0;
            // stores the current frame.

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= updateInterval / frameCount) {
                    // if the time between now and last update is greater than the specified update interval (for 1 frame)
                    Image frame = spriteSheet.getSpriteFrame(towerName, frameIndex);
                    // gets the frame from the spriteSheet (stored as frame (Image object))
                    towerSprite.setImage(frame);
                    //set the image of towerSprite
                    // updates the ImageView of the towerSprite to BE the frame
                    frameIndex = (frameIndex + 1) % frameCount;
                    // update the frame index (modular arithmetic to keep it within frameCount always)
                    lastUpdate = now;
                    // last update = now.
                }
            }
        };
        animationTimer.start();
    }
    /**
    private void createGameToken() {
        gameToken = new Circle(10);
        gameToken.setFill(Color.GOLD);
        gamePane.getChildren().add(gameToken);
    }
     */

    private void setupGameLoop() {
        startTime = System.nanoTime();
        AnimationTimer gameLoop = new AnimationTimer() {
            // AnimationTime created, which calls handle() method every frame
            // name = gameLoop.
            @Override
            public void handle(long now) {
                // now parameter is the current time in nanoSeconds
                if (gameRunning) { // check to see if the game is running
                    if (now - lastUpdate >= updateInterval) { // is the time between (now - lastupdate) (time between NOW and
                        //the last tick) greater than the updateInterval (which we specified)?
                        updateGame(); // if so, call updateGame()
                        lastUpdate = now; // reset lastupdate to now.
                    }
                    if (currentCartIndex < carts.size() && now - startTime >= carts.get(currentCartIndex).spawnTime) {
                        // check if currentCartIndex is less than carts.size
                        // and if the time between now and last-update and if now - lastUpdate is greater than carts.get(spawntime..)
                        // wait i think there is an error here. (FIXED)
                        spawnCart(carts.get(currentCartIndex).cart);
                        //spawn the cart
                        currentCartIndex++;
                    }
                }
            }
        };
        gameLoop.start();
    }

    @FXML
    private void gameStarted() {
        // this is called upon the button being pressed
        carts = CartRound.getCartsForRound(1); // the int will need to be changed to a var roundNum eventually
        cartPath = CartPath.getCartPathForRound(1);
        cartDirectionMap = CartDirectionMap.getDirectionMapForRound(1);
        currentCartIndex = 0;
        gameRunning = true; // game is now running
        lastUpdate = System.nanoTime(); // set lastUpdate to now
        System.out.println("Game started, carts to spawn: " + carts.size());
        // little debugging
    }

    private void spawnCart(Cart cart) {
        ImageView cartToken = new ImageView();
        // create s anew ImageView called cartToken
        cartToken.setFitWidth(50); // set the width
        cartToken.setFitHeight(50); // and the height
        cartToken.setImage(cartSprite.getSpriteFrame(cart.getResourceType(), 0)); // Initial direction 0 probably wrong but ok
        int[][] pathGraph = cartPath.getIndexGraph(); // get the path for the Cart
        Point startPosition = getCartPosition(pathGraph, 1); // Initialize at position 1
        if (startPosition != null) { // if the startPosition exists;
            double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
            double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
            double startX = startPosition.x * cellWidth + cellWidth / 2 - cartToken.getFitWidth() / 2;
            double startY = startPosition.y * cellHeight + cellHeight / 2 - cartToken.getFitHeight() / 2;
            //calculate cell Sizing and also the start co - ordinates
            cartToken.setLayoutX(startX);
            cartToken.setLayoutY(startY);
            //initialise the cart
            gamePane.getChildren().add(cartToken);
            //add the cart to the gamePane
            cartTokens.put(cart, cartToken);
            cartSteps.put(cart, 1);
            // stores information about the Carts in two dictionaries, cartTokens, and cartSteps
            // cartTokens is a dict which maps the cart to the cartToken ( which was created from the cart )
            // cartSteps matches the cart to the .. ''current step''?
            System.out.println("Spawned cart at: " + startX + ", " + startY);
        } else {
            System.out.println("Start position not found in pathGraph.");
        }
        // debugging
    }

    private Point getCartPosition(int[][] pathGraph, int step) {
        //essentially, this function iterates through pathGraph indexMap and finds matches the step to
        // a  grid in the pathGraph.
        for (int i = 0; i < pathGraph.length; i++) {
            for (int j = 0; j < pathGraph[i].length; j++) {
                if (pathGraph[i][j] == step) {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    private void updateCartSprite(Cart cart, ImageView cartToken, int direction) {
        //updates direction, req, direct as a parameter though.
        cartToken.setImage(cartSprite.getSpriteFrame(cart.getResourceType(), direction));
    }

    private void moveCarts() {
        // ill make comments later.
        if (isMoving) return;
        isMoving = true;

        for (Cart cart : cartTokens.keySet()) {
            ImageView cartToken = cartTokens.get(cart);
            int currentStep = cartSteps.get(cart);
            Point nextPosition = getCartPosition(cartPath.getIndexGraph(), currentStep + 1);

            if (nextPosition != null) {
                double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
                double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
                double targetX = nextPosition.x * cellWidth + cellWidth / 2 - cartToken.getFitWidth() / 2;
                double targetY = nextPosition.y * cellHeight + cellHeight / 2 - cartToken.getFitHeight() / 2;

                TranslateTransition transition = new TranslateTransition(Duration.seconds(cart.getSpeed()), cartToken);
                transition.setToX(targetX - cartToken.getLayoutX());
                transition.setToY(targetY - cartToken.getLayoutY());

                int direction = cartDirectionMap.getDirectionGraph()[nextPosition.y][nextPosition.x];
                updateCartSprite(cart, cartToken, direction);

                transition.setOnFinished(event -> {
                    cartSteps.put(cart, currentStep + 1);
                    isMoving = false;
                });
                transition.play();
                System.out.println("Moving cart from step " + currentStep + " to step " + (currentStep + 1));
            } else {
                System.out.println("Cart reached the end of the path or next position is invalid. Current step: " + currentStep);
                isMoving = false;
            }
        }
    }

    private void updateGame() {
        moveCarts();
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

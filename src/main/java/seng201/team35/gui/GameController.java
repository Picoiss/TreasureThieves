package seng201.team35.gui;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import seng201.team35.GameManager;
import seng201.team35.models.Cart;
import seng201.team35.services.*;
import seng201.team35.models.Tower;
import seng201.team35.services.CartRound.CartSpawn;
import seng201.team35.models.Projectile;

import java.awt.Point;
import java.util.*;

//questions for tutorial
//Can we include comments in Final Code
//How do we cite sources such as youtube Videos, Github (other games' source code), et c.
//Are we allowed this much user interactivity in the game?
//Does our game context still fit the requirements of the game in terms of the guidelines shown in the project specs?
//We lack services, will this impact our Codes design score?


import static seng201.team35.services.ProjectileSwitch.getProjectileSprite;

public class GameController {
    @FXML
    private GridPane gameGrid;
    @FXML
    private AnchorPane gamePane;
    @FXML
    private ComboBox<String> towerSelectionComboBox;
    @FXML
    private Label warningLabel;
    @FXML
    private Label winOrLoseLabel;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Label livesLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label roundLabel;
    @FXML
    private Label cartsLeftLabel;
    @FXML
    private Label moneyEarnedLabel;

    private int cartsLeft;
    private long startTime;
    private boolean gameRunning = false;
    private long lastUpdate = 0;
    private final long updateInterval = 500_000_000;
    private GameManager gameManager;
    private Image grassImage;
    private Image texturedGrassImage;
    private int[][] currentIndexGraph;
    private String currentTower;
    private Map<Point, ImageView> towerPositions;
    private SpriteSheet spriteSheet;
    private CartSprite cartSprite;
    private List<CartSpawn> carts;
    private List<CartSpawn> initialCarts;
    private Map<Cart, Rectangle> cartHealthBars = new HashMap<>();
    private int currentCartIndex = 0;
    private CartPath cartPath;
    private CartDirectionMap cartDirectionMap;
    private BuildingAndNatureMap buildingAndNatureMap;
    private Map<ImageView, Tower> towerImageViewToTower = new HashMap<>();
    private Map<Cart, ImageView> cartTokens = new HashMap<>();
    private Map<Cart, Integer> cartSteps = new HashMap<>();
    private Map<Point, Boolean> isGridShooting = new HashMap<>();
    private boolean gameStartState = false;
    private String currentModifier;
    private double difficultyScaling;
    private double speedIncrease = 1;
    private int cartNumIncrease = 0;
    private int cartNumDecrease= 0;
    private double cartFillIncrease = 1;
    private int moneyEarned = 0;
    Map<String, Integer> cartRewards = new HashMap<>();

    public GameController(GameManager x) {
        gameManager = x;
        towerPositions = new HashMap<>();
        spriteSheet = new SpriteSheet();
        cartSprite = new CartSprite();
        // initalise all of these GameController stuff..
    }

    @FXML
    public void initialize() {
        grassImage = new Image(getClass().getResourceAsStream("/images/Grass.png"));
        texturedGrassImage = new Image(getClass().getResourceAsStream("/images/TexturedGrass.png"));
        // load images of grass, and textured grass (loading grassImage may be redundant)
        setupGameLoop();
        mainMenuButton.setVisible(false);
        // set the mainMenu button to be not visisble.
        loadGroundAssets(); // load the ground (path and grass etc).
        drawPathForRound(gameManager.getCurrentRound()); // Example for round 1
        buildingAndNatureMap = BuildingAndNatureMap.getBuildingAndNatureMapForRound(gameManager.getCurrentRound());
        checkModifiers();
        loadBuildingAssets();
        getDifficultyScaling();
        // Populate the ComboBox with tower names from the GameManager
        towerSelectionComboBox.getItems().addAll(Tower.getTowerNames(gameManager.getMainTowerList()));

        // Add click handler to the grid maybe we can jst add a fxml code instead?
        gameGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleGridClick);

        initialCarts = CartRound.getCartsForRound(gameManager.getCurrentRound(), cartNumDecrease, cartNumIncrease);
        cartsLeft = initialCarts.size();
        livesLabel.setText("Lives: " + gameManager.getLives());
        moneyLabel.setText("Money: " + gameManager.getMoneyAmount());
        roundLabel.setText("Round: " + gameManager.getCurrentRound());
        cartsLeftLabel.setText("Carts Left: " + cartsLeft);
        cartRewards.put("Bronze", 300);
        cartRewards.put("Silver", 400);
        cartRewards.put("Gold", 550);
        cartRewards.put("Diamond", 800);
        cartRewards.put("Emerald", 950);
        cartRewards.put("Ruby", 1200);

        updateComboBox();
    }
    private void getDifficultyScaling() {
        String difficulty =gameManager.getGameDifficulty();
        switch(difficulty) {
            // get a difficulty scaling per difficulty (this affects how much damage a tower will do)
            case "Easy":
                difficultyScaling = 1.75;
                break;
            case "Medium":
                difficultyScaling = 1.5;
                break;
            case "Hard":
                difficultyScaling = 1;
                break;
        }
    }
    private void loadGroundAssets() {
        // load the ground (just grass)
        int rows = 20;
        int columns = 18;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                addTileToGrid(texturedGrassImage, 15, 0, 32, 32, col, row); // Using the textured grass part for more detail
            }
        }
    }
    private void checkUpgrades() {
        // THIS needs to be implemented
        gameManager.getUpgradesList();
    }
    public void loadBuildingAssets() {
        // Get the BuildingAndNatureGraph from the current BuildingAndNatureMap
        int[][] buildingAndNatureGraph = buildingAndNatureMap.getBuildingAndNatureGraph();
        // Retrieve the Path graph for the current round
        Path path = Path.getPathForRound(gameManager.getCurrentRound());
        int[][] pathGraph = path.getIndexGraph();

        // Iterating over each cell in the buildingAndNatureGraph
        for (int row = 0; row < buildingAndNatureGraph.length; row++) {
            for (int col = 0; col < buildingAndNatureGraph[row].length; col++) {
                // Check if the current cell is a path tile; skip if it is
                if (pathGraph[row][col] != 0) {
                    continue;
                }

                // Depending on the value at buildingAndNatureGraph[row][col], load the corresponding image
                ImageView imageView = new ImageView();
                imageView.setFitWidth(26);
                imageView.setFitHeight(32);
                imageView.setPreserveRatio(false);

                switch (buildingAndNatureGraph[row][col]) {
                    case 1:
                        Random rng = new Random();
                        int treeIndex = rng.nextInt(1,4);
                        imageView.setImage(new Image("/images/Buildings/Tree" + treeIndex + ".png"));
                        break;
                    case 2:
                        imageView.setImage(new Image("/images/Buildings/Rocks.png"));
                        break;
                    case 3:
                        imageView.setImage(new Image("/images/Buildings/Houses.png"));
                        break;
                    case 4:
                        imageView.setImage(new Image("/images/Buildings/Chapels.png"));
                        break;
                    case 5:
                        imageView.setImage(new Image("/images/Buildings/Taverns.png"));
                        break;
                    case 6:
                        imageView.setImage(new Image("/images/Buildings/Well.png"));
                        break;
                    default:
                        // No image for this cell
                        continue;
                }
                gameGrid.add(imageView, col, row); // Add to the GridPane at specified column and row
                GridPane.setHalignment(imageView, HPos.CENTER); // Center the image in its grid cell
                GridPane.setValignment(imageView, VPos.CENTER);
            }
        }
    }
    private void checkModifiers() {
        currentModifier = gameManager.getModifier();  // Get the current modifier from the GameManager
        switch (currentModifier) {
            case "Cart Speed Increase 10%":
                speedIncrease = 1.1;
                break;
            case "Cart Speed Increase 5%":
                speedIncrease = 1.05;
                break;
            case "Cart Speed Decrease 5%":
                speedIncrease = 0.95;
                break;
            case "Cart Speed Decrease 10%":
                speedIncrease = 0.9;
                break;
            case "Cart Number Decrease by 2":
                cartNumDecrease = 2;
                break;
            case "Cart Number Decrease by 1":
                cartNumDecrease = 1;
                break;
            case "Cart Number Increase by 1":
                cartNumIncrease = 1;
                break;
            case "Cart Number Increase by 2":
                cartNumIncrease = 2;
                break;
            case "Cart Fill Amount Decrease 10%":
                cartFillIncrease -= 0.1;
                break;
            case "Cart Fill Amount Increase 5%":
                cartFillIncrease += 0.05;
                break;
            case "Cart Fill Amount Increase 10%":
                cartFillIncrease += 0.1;
                break;
            case "Cart Fill Amount Decrease 5%":
                cartFillIncrease -= 0.05;
                break;
            default:
                System.out.println("No modifier or unknown modifier");
                break;
            // note, most of these have to be implemented still.
        }
    }
    private void drawPathForRound(int roundNumber) {
        // draw the path for the round
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
        // adds a tile to the grid
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
    private void setWarning(String warningText) {
        // sets a warning if a tower is already at the same location
        // or if you tried to click start game again
        warningLabel.setText(warningText);
        warningLabel.setOpacity(0.7);
        warningLabel.toFront();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), new KeyValue(warningLabel.opacityProperty(), 0.7)),
                new KeyFrame(Duration.seconds(4), new KeyValue(warningLabel.opacityProperty(), 0))
        );
        timeline.setOnFinished(event -> warningLabel.setText(""));
        timeline.play();
    }
    private void addTower(int colIndex, int rowIndex) {
        Point newTowerPosition = new Point(colIndex, rowIndex);
        if (towerPositions.containsKey(newTowerPosition)) {
            System.out.println("A tower already exists at this position!");
            setWarning("A tower already exists at this location");
            // sets a warning if the tower is already at the locatiomn
            return; // Exit the method if a tower already exists at this position
        }
        if (buildingAndNatureMap.getBuildingAndNatureGraph()[rowIndex][colIndex] != 0) {
            setWarning("Can't place a tower on an object");
            return; // Exit the method if there's an object at this location
        }
        Tower tower = retrieveSelectedTower();
        //gets the tower from the hashMap retrieveSelectedTower -> which holds tower information per grid basis.
        // Place the tower in GameManager
        gameManager.placeTowerAt(newTowerPosition, tower);

        ImageView towerSprite = new ImageView();
        towerImageViewToTower.put(towerSprite, tower);
        // create an image of the towerSprite.
        towerSprite.setFitWidth(35); // Adjust as needed
        towerSprite.setFitHeight(35); // Adjust as needed
        towerSprite.setPreserveRatio(true);
        towerSprite.setSmooth(true);
        towerPositions.put(newTowerPosition, towerSprite);
        gameGrid.add(towerSprite, colIndex, rowIndex);
        isGridShooting.put(new Point(colIndex,rowIndex), false);
        animateTower(towerSprite, tower.getName());
        towerSelectionComboBox.getItems().remove(tower.getName());
        currentTower = null; // set currentTower -> null afterplacing.
    }
    private Tower retrieveSelectedTower() {
        //uh.. actually what?
        String towerName = towerSelectionComboBox.getValue();
        return gameManager.getTowerByName(towerName);
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
            private long lastUpdatesprite = 0;
            // stores the current frame.

            @Override
            public void handle(long now) {
                if (now - lastUpdatesprite >= updateInterval / frameCount) {
                    // if the time between now and last update is greater than the specified update interval (for 1 frame)
                    Image frame = spriteSheet.getSpriteFrame(towerName, frameIndex);
                    // gets the frame from the spriteSheet (stored as frame (Image object))
                    towerSprite.setImage(frame);
                    towerSprite.setPreserveRatio(true);
                    towerSprite.setSmooth(true);
                    //set the image of towerSprite
                    // updates the ImageView of the towerSprite to BE the frame
                    frameIndex = (frameIndex + 1) % frameCount;
                    // update the frame index (modular arithmetic to keep it within frameCount always)
                    lastUpdatesprite = now;
                    // last update = now.
                }
            }
        };
        animationTimer.start();
    }

    private void setupGameLoop() {
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
                        if (now - startTime >= carts.get(currentCartIndex).spawnTime) {
                            //System.out.println((now - startTime) + "," + carts.get(currentCartIndex).spawnTime);
                        }
                        // check if currentCartIndex is less than carts.size
                        // and if the time between now and last-update and if now - lastUpdate is greater than carts.get(spawnTime..)
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
        if (!gameStartState) {
            gameStartState = true;
            // this is called upon the button being pressed
            carts = initialCarts;
            cartPath = CartPath.getCartPathForRound(gameManager.getCurrentRound());
            cartDirectionMap = CartDirectionMap.getDirectionMapForRound(gameManager.getCurrentRound());
            currentCartIndex = 0;
            startTime = System.nanoTime(); // Set start time to current time
            gameRunning = true; // game is now running
            lastUpdate = System.nanoTime(); // set lastUpdate to now
            System.out.println("Game started, carts to spawn: " + carts.size());
            // little debugging
        }
        else {
            setWarning("You already Started the Game");
        }
    }
    @FXML
    private void mainMenu() {
        // this is the LAST THING that is done on the game scene
        // therefore all clearing and resetting occurs here.
        //side note -> if you want to make a diff. func which goes from game to fail
        // you will need to use the same methods of clearing before...
        // acutally no. then the games over.
        //nevermind.
        // Clear all tiles and elements from the game grid
        gameManager.setModifiersSelectedFalse();
        gameGrid.getChildren().clear();
        gamePane.getChildren().clear();
        // Optionally, clear specific game-related collections if not already done
        cartTokens.clear();
        towerPositions.clear();
        cartHealthBars.clear();
        // Reset game state variables
        gameRunning = false;
        gameStartState = false;
        currentCartIndex = 0;
        carts.clear();
        gameManager.setModifiersInitialisedFalse();
        gameManager.changeMoneyAmount(moneyEarned);
        gameManager.incrementTotalMoney(moneyEarned);

        // Transition to main menu or change the round
        if (winOrLoseLabel.getTextFill() == Color.GREEN) {
            if (gameManager.getCurrentRound() == gameManager.getNumOfRounds()) {
                gameManager.gameToWinMenuScreen(); // If final round was won, transition to win menu
            }
            else {
                gameManager.changeCurrentRound();
                gameManager.gameToMainMenuScreen(); // If non-final round was won, change round and go to main menu
            }
        }
        else if (winOrLoseLabel.getTextFill() == Color.ORANGE) {
            gameManager.gameToMainMenuScreen(); // If round failed, remain on same round and go to main menu
        }

        else {
            gameManager.gameToFailMenuScreen(); // If all lives lost, transition to fail menu
        }
    }

    private int getInitialCartDirection(Point startPosition) {
        if (startPosition != null) {
            return cartDirectionMap.getDirectionGraph()[startPosition.y][startPosition.x];
        }
        return 0;
    }


    private void spawnCart(Cart cart) {
        ImageView cartToken = new ImageView();
        // create s anew ImageView called cartToken
        cartToken.setFitWidth(50); // set the width
        cartToken.setFitHeight(50); // and the height
        cartToken.setImage(cartSprite.getSpriteFrame(cart.getResourceType(), 1)); // Initial direction 0 probably wrong but ok
        int[][] pathGraph = cartPath.getIndexGraph(); // get the path for the Cart
        Point startPosition = getCartPosition(pathGraph, 1, -1, -1); // Initialize at position 1
        System.out.println(startPosition);
        cart.setDirection(getInitialCartDirection(startPosition));
        if (startPosition != null) { // if the startPosition exists;
            cart.setX(startPosition.x);
            cart.setY(startPosition.y);
            double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
            double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
            double startX = startPosition.x * cellWidth + cellWidth / 2;
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
            Rectangle healthBar = new Rectangle(50, 5);  // Health bar width matches the cart width
            healthBar.setLayoutX(startX);
            healthBar.setLayoutY(startY + 55);  // Offset slightly below the cart
            healthBar.setFill(Color.GREEN);  // Green health bar
            gamePane.getChildren().add(healthBar);
            cartHealthBars.put(cart, healthBar);
            updateHealthBar(cart);
            System.out.println("Spawned cart at: " + startX + ", " + startY);
        } else {
            System.out.println("Start position not found in pathGraph.");
        }
        // debugging
    }

    private void updateHealthBar(Cart cart) {
        Rectangle healthBar = cartHealthBars.get(cart);
        // obtains a rectangle healthBar from the map cartHEALTHBARS.
        if (healthBar != null) {
            double fillPercentage = (double) (cart.getSize() - cart.getCurrentAmount()) / cart.getSize();
            healthBar.setWidth(50 * fillPercentage);
            if (fillPercentage < 0.3) {
                healthBar.setFill(Color.RED);  // Red when health is low
            } else if (fillPercentage < 0.6) {
                healthBar.setFill(Color.YELLOW);  // Yellow when health is medium
            } else {
                healthBar.setFill(Color.GREEN);  // Green when health is high
            }
        }
    }

    private void rotateTowerTowardsTarget(ImageView tower, Point2D target) {
        // this function calculates the rotation of the Tower towards the (nearest?) Cart.
        Point2D towerPosition = new Point2D(tower.getLayoutX() + tower.getFitWidth() / 2, tower.getLayoutY() + tower.getFitHeight() / 2);
        // this calculates the Towers' Position using a Point2D Variable (a x and a y co-ordinate)
        double angle = Math.atan2(target.getY() - towerPosition.getY(), target.getX() - towerPosition.getX()) * 180 / Math.PI;
        // Then calculates an ANGLE for the tower to the target by taking the difference in distance from the tower and the target,
        // and applies trig rules to find the angle
        // using the atan2 function via Math, (which takes two co-ordinates and changes the rect. co-ordinates to polar co-ordinates in terms of r and theta)
        // however, as this function is in radians, will need to convert to degrees in order to use setRotate
        // (which is a javafx NODE function.
        // However, as all the images are upright (facing up), we will need to correct the angle by adding 90degrees to our angle calculation. ( this is assuming
        // that 0 degrees is facing RIGHT.
        tower.setRotate(angle + 90); // Rotate the tower to face the target
    }

    private void checkTowersTargeting() {
        double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
        double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
        // calculates the Width and Height, of each gameGrid cell
        // in theory, this can both be set to GLOBAL variables of GAME_HEIGHT
        // and GAME_WIDTH as both are set to 50 px, but keeping this function here
        // allows for future modular/dynamic game grids. (gridpanes)

        for (Map.Entry<Point, ImageView> entry : towerPositions.entrySet()) {
            // note, towerPositions is a hashMap containing the towers' (Position, Sprite)
            Point towerGridPos = entry.getKey();
            ImageView towerSprite = entry.getValue();
            // as follows.

            // Calculate the center of the tower's grid cell in pixel coordinates
            double towerCenterX = (towerGridPos.x + 0.5) * cellWidth;
            //System.out.println("towerCenterx = " + towerCenterX);

            // this calculates the tower Center (X co-ordinate). This is done by taking the gridposition of the tower
            // adding 0.5 (to get the center), then multiplying by the cellWidth (which could have been set at 50, but oh well)
            double towerCenterY = (towerGridPos.y + 0.5) * cellHeight;
            //System.out.println("towerCentery = " + towerCenterY);
            //same for the y co-ordinate of the tower.
            Map<Cart, Double> cartsInRange = new HashMap<>();

            for (Map.Entry<Cart, ImageView> cartEntry : cartTokens.entrySet()) {

                //iterates over the carts which are in the game.
                // for (position of cart, cartSprite) in the cartTokens hashMap;
                Cart cart = cartEntry.getKey();
                ImageView cartToken = cartEntry.getValue();

                // Get the bounds of the cart token in the parent's coordinate system
                Bounds cartBounds = cartToken.getBoundsInParent();
                // using the .getBoundsInParent() is very important, as otherwise, the cart's position is calculated
                // incorrectly. (for some reason the gridpane becomes like -850, 850)..
                // Calculate the center of the cart's image using bounds
                double cartCenterX = cartBounds.getMinX() + cartBounds.getWidth() / 2;
                double cartCenterY = cartBounds.getMinY() + cartBounds.getHeight() / 2;
                // unsure if the cartBounds.getMinX() is even required... but..
                // calculates the center of the Cart by getting the Min of the boundaries of the cart (e.g most left point of cart)
                // + the width of the Cart divided by 2, in order to get the middle of the Cart (done with both X and Y)
                // Calculate the distance in pixel coordinates
                double distance = Math.sqrt(Math.pow(towerCenterX - cartCenterX, 2) + Math.pow(towerCenterY - cartCenterY, 2));
                // pythag.
                //System.out.println(distance);
                if (distance <= 2 * Math.min(cellWidth, cellHeight)) {
                    cartsInRange.put(cart, distance);
                }
            }
            if (cartsInRange.size() != 0) {
                Cart closestCart = null;
                Double closestDistance = 99999999.9;
                for (Map.Entry<Cart, Double> cartInfo : cartsInRange.entrySet()) {
                    Cart cart = cartInfo.getKey();
                    if (towerImageViewToTower.get(towerSprite).getResourceType() == cart.getResourceType()) {
                        closestCart = cart;
                    }
                }
                if (closestCart == null) {
                    for (Map.Entry<Cart, Double> cartInfo : cartsInRange.entrySet()) {
                        Cart cart = cartInfo.getKey();
                        Double distance = cartInfo.getValue();
                        if (distance < closestDistance) {
                            closestDistance = distance;
                            closestCart = cart;
                        }
                    }
                }
                ImageView cartImage = cartTokens.get(closestCart);
                Bounds cartBounds = cartImage.getBoundsInParent();
                double cartCenterX = cartBounds.getMinX() + cartBounds.getWidth() / 2;
                double cartCenterY = cartBounds.getMinY() + cartBounds.getHeight() / 2;
                rotateTowerTowardsTarget(towerSprite, new Point2D(cartCenterX, cartCenterY));
                System.out.println(closestCart);
                // rotates the tower towards the cart
                shootProjectile(towerGridPos, closestCart);
                // shoots a projectile at the cart.
            }
        }
    }

    private void shootProjectile(Point towerGridPos, Cart targetCart) {

        ImageView towerSprite = towerPositions.get(towerGridPos);
        if (towerSprite == null) return;

        Tower shootingTower = gameManager.getTowerAt(towerGridPos);

        if (!isGridShooting.get(towerGridPos)){
            double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
            double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
            Point2D towerCenter = new Point2D((towerGridPos.x + 0.5) * cellWidth, (towerGridPos.y + 0.5) * cellHeight);

            ImageView cartToken = cartTokens.get(targetCart);
            if (cartToken == null) {
                isGridShooting.replace(towerGridPos, false);
                return;
            }

            Bounds cartBounds = cartToken.getBoundsInParent();
            Point2D cartCenter = new Point2D(cartBounds.getMinX() + cartBounds.getWidth() / 2, cartBounds.getMinY() + cartBounds.getHeight() / 2);

            // Calculate the distance in tiles
            double distance = towerCenter.distance(cartCenter) / cellWidth;

            double targetX = cartCenter.getX();
            double targetY = cartCenter.getY();

            if (distance > 0.8 && distance <= 1.25) {
                // Shoot between current and next position
                Point nextPosition = getCartPosition(cartPath.getIndexGraph(), cartSteps.get(targetCart) + 1, targetCart.getX(), targetCart.getY());
                if (nextPosition != null) {
                    double nextPosX = nextPosition.x * cellWidth + cellWidth / 2;
                    double nextPosY = nextPosition.y * cellHeight + cellHeight / 2;
                    targetX += (nextPosX - targetX) / 2;
                    targetY += (nextPosY - targetY) / 2;
                }
            } else if (distance > 1.25 && distance <= 2) {
                // Shoot netween next position and current, but more so to the next position
                Point nextPosition = getCartPosition(cartPath.getIndexGraph(), cartSteps.get(targetCart) + 1, targetCart.getX(), targetCart.getY());
                if (nextPosition != null) {
                    double nextPosX = nextPosition.x * cellWidth + cellWidth / 2;
                    double nextPosY = nextPosition.y * cellHeight + cellHeight / 2;
                    targetX += (nextPosX - targetX) / 1.6;
                    targetY += (nextPosY - targetY) / 1.6;
                }
            }

            //System.out.println("Shooting projectile from: (" + towerCenter.getX() + ", " + towerCenter.getY() + ") to: (" + targetX + ", " + targetY + ")");
            launchProjectile(towerCenter.getX(), towerCenter.getY(), targetX, targetY, targetCart, shootingTower, towerGridPos);
        }
    }



    private void launchProjectile(double startX, double startY, double targetX, double targetY, Cart targetCart, Tower shootingTower, Point towerGridPos) {
        ImageView projectile = new ImageView(Projectile.getProjectileSprite(getProjectileSprite(shootingTower.getName())));
        // will need to add a function where the Projectile sprite depends on the tower. Luckily, not very hard to implement as the logic is already
        // here.
        projectile.setFitWidth(10);
        projectile.setFitHeight(18);
        projectile.setLayoutX(startX);
        projectile.setLayoutY(startY);
        gamePane.getChildren().add(projectile);
        double angle = Math.atan2(targetY - startY, targetX - startX) * 180 / Math.PI;
        projectile.setRotate(angle - 90);  // Adjust by -90 degrees because the projectile points down by default
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), projectile);
        shootingTower.setLastShotTime(System.nanoTime());
        isGridShooting.replace(towerGridPos, true);
        transition.setByX(targetX - startX);
        transition.setByY(targetY - startY);
        transition.setOnFinished(event -> {
            checkProjectileCollision(projectile, targetCart, shootingTower, towerGridPos);
            // when the transition
            gamePane.getChildren().remove(projectile);
        });
        transition.play();
    }

    private void checkProjectileCollision(ImageView projectile, Cart targetCart, Tower shootingTower, Point towerGridPos) {
        isGridShooting.replace(towerGridPos, false);
        ImageView cartToken = cartTokens.get(targetCart);
        if (cartToken == null) return;

        Bounds projectileBounds = projectile.getBoundsInParent();
        Bounds cartBounds = cartToken.getBoundsInParent();

        if (projectileBounds.intersects(cartBounds)) {
            // Check if the resource types match
            System.out.println(targetCart.getResourceType() + ", " + shootingTower.getResourceType());
            boolean isResourceMatch = targetCart.getResourceType().equals(shootingTower.getResourceType());

            // Calculate damage based on resource type match
            int damage;
            if (isResourceMatch) {
                damage = (int) (difficultyScaling * cartFillIncrease * shootingTower.getMaxAmount() / 10); // Full damage calculation
                System.out.println("Damage Full");
            } else {
                damage = (int) (difficultyScaling * cartFillIncrease * shootingTower.getMaxAmount() / 25); // Reduced damage (40% of full damage)
                System.out.println("Damage 40%");
            }

            targetCart.fillCart(damage); // Fill the cart with the calculated damage value
            if (targetCart.isCartFilled()) { // Check if the cart is now full
                System.out.println("Cart destroyed");
                gamePane.getChildren().remove(cartToken); // Remove the cart token from the game pane
                cartTokens.remove(targetCart); // Remove the cart from active carts
                cartsLeft -= 1; // Update remaining carts to fill
                moneyEarned += cartRewards.get(targetCart.getResourceType());
                gameManager.incrementTotalCartsDestroyed();
                Rectangle healthBar = cartHealthBars.get(targetCart);
                if (healthBar != null) {
                    gamePane.getChildren().remove(healthBar); // Remove health bar associated with the cart
                }
            } else {
                System.out.println("Projectile hit the cart! Damage: " + damage);
                updateHealthBar(targetCart); // Update health bar based on current health
            }
        }
    }

    private Point getCartPosition(int[][] pathGraph, int step, int prevX, int prevY) {
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
        Iterator<Map.Entry<Cart, ImageView>> iterator = cartTokens.entrySet().iterator();
        while (iterator.hasNext()) {
            // while there are entries in iterator -> cartTokens.entrySet()
            // while there are carts
            Map.Entry<Cart, ImageView> entry = iterator.next();
            // gets a Cart;
            Cart cart = entry.getKey();
            // cart info gets cart from entry.getKey()
            Rectangle healthBar = cartHealthBars.get(cart);
            ImageView cartToken = entry.getValue();
            Integer currentStep = cartSteps.get(cart); // Get the current step

            if (currentStep == null) {
                System.out.println("Error: No tracking info for cart. Removing cart.");
                gamePane.getChildren().remove(cartToken);
                iterator.remove();
                if (healthBar != null) {
                    gamePane.getChildren().remove(healthBar);
                }
                continue;  // Skip further processing for this cart
            }

            Point nextPosition = getCartPosition(cartPath.getIndexGraph(), currentStep + 1, cart.getX(), cart.getY());

            if (nextPosition != null) {
                cart.setX(nextPosition.x);
                cart.setY(nextPosition.y);
                double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
                double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
                double targetX = nextPosition.x * cellWidth + cellWidth / 2 - cartToken.getFitWidth() / 8;
                double targetY = nextPosition.y * cellHeight + cellHeight / 2 - cartToken.getFitHeight() / 2;
                TranslateTransition transition = new TranslateTransition(Duration.seconds(cart.getSpeed()*speedIncrease), cartToken);
                transition.setToX(targetX - cartToken.getLayoutX());
                transition.setToY(targetY - cartToken.getLayoutY());
                //direction 5 signifies where two paths merge on the map

                cart.setDirection(cartDirectionMap.getDirectionGraph()[nextPosition.y][nextPosition.x]);
                updateCartSprite(cart, cartToken, cart.getDirection());



                transition.setOnFinished(event -> {
                    cartSteps.put(cart, currentStep + 1);
                });
                transition.play();
                if (healthBar != null) {
                    TranslateTransition healthBarTransition = new TranslateTransition(Duration.seconds(cart.getSpeed()), healthBar);
                    healthBarTransition.setToX(targetX - healthBar.getLayoutX());
                    healthBarTransition.setToY(targetY - healthBar.getLayoutY() + 55);

                    healthBarTransition.play();
                }
            } else {
                System.out.println("Cart reached the end of the path or next position is invalid. Current step: " + currentStep);
                if (healthBar != null) {
                    gamePane.getChildren().remove(healthBar);
                }
                gamePane.getChildren().remove(cartToken);
                iterator.remove();
                cartSteps.remove(cart);
                gameManager.changeLives(1);
                gameRunning = false;
                winOrLoseLabel.toFront();
                mainMenuButton.toFront();
                winOrLoseLabel.setText("You failed Round " + gameManager.getCurrentRound() + "!");
                winOrLoseLabel.setTextFill(Color.ORANGE);  // Set text color to orange
                mainMenuButton.setVisible(true);  // Show the main menu button
                if (initialCarts.size() - cartsLeft != 0) {
                    moneyEarned = (moneyEarned/(initialCarts.size() - cartsLeft))/2; // You get half the normal reward for failing a round
                }
                moneyEarnedLabel.setText("You earned $" + moneyEarned);
                updateLabels();
            }
        }
    }


    private void updateGame() {
        updateLabels();
        moveCarts();
        checkTowersTargeting();
        checkWinOrLose();
    }

    // Update the lives, money, and carts left labels
    private void updateLabels() {
        livesLabel.setText("Lives: " + gameManager.getLives());
        moneyLabel.setText("Money: " + gameManager.getMoneyAmount());
        cartsLeftLabel.setText("Carts Left: " + cartsLeft);
    }

    private void checkWinOrLose() {
        boolean allCartsSpawned = currentCartIndex >= carts.size();  // Check if all carts have been spawned
        if (allCartsSpawned && cartTokens.isEmpty()) {  // No active carts left
            gameRunning = false;
            winOrLoseLabel.toFront();
            mainMenuButton.toFront();
            winOrLoseLabel.setText("You cleared Round " + gameManager.getCurrentRound() + "!");
            winOrLoseLabel.setTextFill(Color.GREEN);  // Set text color to green
            mainMenuButton.setVisible(true);  // Show the main menu button
            if (initialCarts.size() - cartsLeft != 0) {
                moneyEarned = moneyEarned/(initialCarts.size() - cartsLeft);
            }
            moneyEarnedLabel.setText("You earned $" + moneyEarned);
            updateLabels();
        }

        // Check lose condition
        if (gameManager.getLives() <= 0) {  // This will be implemented based on your game health logic
            gameRunning = false;
            winOrLoseLabel.toFront();
            mainMenuButton.toFront();
            winOrLoseLabel.setText("You lost all your lives!");
            winOrLoseLabel.setTextFill(Color.RED);  // Set text color to red
            mainMenuButton.setVisible(true);  // Show the main menu button
            if (initialCarts.size() - cartsLeft != 0) {
                moneyEarned = moneyEarned/(initialCarts.size() - cartsLeft);
            }
            moneyEarnedLabel.setText("You earned $" + moneyEarned);
            updateLabels();
        }
    }

    @FXML
    private void towerSelected() {
        currentTower = towerSelectionComboBox.getValue();
    }

    private void updateComboBox() {
        towerSelectionComboBox.setValue("");
    }
}

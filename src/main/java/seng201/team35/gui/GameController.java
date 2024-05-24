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
import seng201.team35.models.Upgrade;
import seng201.team35.services.*;
import seng201.team35.models.Tower;
import seng201.team35.services.CartRound.CartSpawn;
import seng201.team35.models.Projectile;

import java.awt.Point;
import java.util.*;

import static seng201.team35.services.ProjectileSwitch.getProjectileSprite;

/**Manages the Logic of the 'game'. This class loads, updates and renders certain game aspects.
 *
 * @author msh254, nsr36
 */
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
    private long lastUpdate1 = 0;
    private int time = 0;
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
    private List<Tower> towersUsed = new ArrayList<>();
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
    private double damageIncreaseBonus = 1;
    private double damageIncreaseBronze = 1;
    private double damageIncreaseSilver = 1;
    private double damageIncreaseGold = 1;
    private double damageIncreaseDiamond = 1;
    private double damageIncreaseEmerald = 1;
    private double damageIncreaseRuby = 1;
    private double randomEventMultiplier = 1;
    Map<String, Integer> cartRewards = new HashMap<>();

    /**  ?
     *
     * @param x
     */
    public GameController(GameManager x) {
        gameManager = x;
        towerPositions = new HashMap<>();
        spriteSheet = new SpriteSheet();
        cartSprite = new CartSprite();
        // initalise all of these GameController stuff..
    }

    /**
     * Initialises the GameController.
     * Specifically, sets up the GameLoop, loads Ground Assets, Sets Labels, and
     * gets certain parameters such as difficulty, and maps which are used further in the GameController class.
     * @author msh254, nsr36
     */
    @FXML
    public void initialize() {
        grassImage = new Image(getClass().getResourceAsStream(("/images/Grass.png")));
        texturedGrassImage = new Image(getClass().getResourceAsStream("/images/TexturedGrass.png"));
        setupGameLoop();
        mainMenuButton.setVisible(false);
        loadGroundAssets();
        drawPathForRound(gameManager.getCurrentRound());
        buildingAndNatureMap = BuildingAndNatureMap.getBuildingAndNatureMapForRound(gameManager.getCurrentRound());
        checkModifiers();
        loadBuildingAssets();
        getDifficultyScaling();
        checkUpgrades();
        setRandomEvent();
        towerSelectionComboBox.getItems().addAll(Tower.getTowerNames(gameManager.getMainTowerList()));
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

    private void setRandomEvent() {
        Random rng = new Random();
        int randomIndex = rng.nextInt(0,10);
        switch(randomIndex) {
            case 1:
                randomEventMultiplier = 1.2;
                setWarning("Random Event!, 20% Damage Boost");
                break;
            case 5:
                randomEventMultiplier = 0.8;
                setWarning("Random Event!, 20% Damage Decrease");
                break;
        }
    }


    /**Gets the difficulty scaling which is applied to certain logics such as projectile collision calculations, damage
     * calculations and money calculations.
     * @author msh254
     */
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

    /**Loads the base 'grass' assets over the entire gridPane (every tile in gridPane), further assets which are loaded
     * are simply put over these grass images.
     * @author msh254 (image credits see readME)
     */
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
        for (Upgrade upgrade: gameManager.getUpgradesList()) {
            if (upgrade.getStatus() == "Active") {
                switch (upgrade.getResourceType()) {
                    case "Bronze":
                        damageIncreaseBronze += 0.1;
                        break;
                    case "Silver":
                        damageIncreaseSilver += 0.1;
                        break;
                    case "Gold":
                        damageIncreaseGold  += 0.1;
                        break;
                    case "Diamond":
                        damageIncreaseDiamond += 0.1;
                        break;
                    case "Emerald":
                        damageIncreaseEmerald += 0.1;
                        break;
                    case "Ruby":
                        damageIncreaseRuby += 0.1;
                        break;
                }
            }
        }

    }

    /**Loads the trees, rocks and buildings for the game. Puts certain images at certain locations, specified
     * by the 'buildingAndNatureGraph' which dictates which asset (if any) should be loaded at any given position
     * in the gridPane
     * @author msh254 , ' Bro Code ' (youtube -> see readMe (1)) (image credits see readME)
     */
    public void loadBuildingAssets() {
        int[][] buildingAndNatureGraph = buildingAndNatureMap.getBuildingAndNatureGraph();
        Path path = Path.getPathForRound(gameManager.getCurrentRound());
        int[][] pathGraph = path.getIndexGraph();
        for (int row = 0; row < buildingAndNatureGraph.length; row++) {
            for (int col = 0; col < buildingAndNatureGraph[row].length; col++) {
                if (pathGraph[row][col] != 0) {
                    continue;
                }
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
                        continue;
                }
                gameGrid.add(imageView, col, row);
                GridPane.setHalignment(imageView, HPos.CENTER);
                GridPane.setValignment(imageView, VPos.CENTER);
            }
        }
    }

    /**A function which is called at the beginning of the round which determines what modifiers are active on the
     * certain round. Once obtained, the modifers will alter certain aspects of the round
     *
     * @author msh254
     */
    private void checkModifiers() {
        currentModifier = gameManager.getModifier();
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

    /**A function which draws the 'path' (canal) for the certain round. This is done by obtaining which grids in the grid
     * Pane have 'paths' on them via the Path class.
     * The function iterates over every grid in the gridpane, and if the indexGraph in Path.java contains a '1' at that location.
     * a path is generated there
     * @author msh254
     * @param roundNumber
     */
    private void drawPathForRound(int roundNumber) {
        Path path = Path.getPathForRound(roundNumber);
        currentIndexGraph = path.getIndexGraph();
        for (int row = 0; row < currentIndexGraph.length; row++) {
            for (int col = 0; col < currentIndexGraph[row].length; col++) {
                if (currentIndexGraph[row][col] == 1) {
                    addTileToGrid(grassImage, 0, 0, 15, 16, col, row);
                }
            }
        }
    }

    /**Adds a tile to the Grid, specified by the 'image' (imageview), size (width and height of image),
     * location(x,y ) and the gridPane location (colIndex, rowIndex).
     * The function then creates a ImageView object at the location specified, fitting the whole of the
     * tile (50x50 px)
     *
     * @author msh254
     * @param image
     * @param x
     * @param y
     * @param width
     * @param height
     * @param colIndex
     * @param rowIndex
     */
    private void addTileToGrid(Image image, int x, int y, int width, int height, int colIndex, int rowIndex) {
        ImageView tile = new ImageView(image);
        tile.setViewport(new Rectangle2D(x, y, width, height));
        tile.setFitWidth(50);
        tile.setFitHeight(50);
        tile.setSmooth(false);
        gameGrid.add(tile, colIndex, rowIndex);
    }

    /**Function which handles mouse inputs (mouse clicks).
     * Specifically, dictates where the tower is to be placed on the gridPane.
     * Click (x,y) co-ordinates and transformed into grids in the GridPane.
     *
     * @author msh254
     * @param event
     */
    @FXML
    private void handleGridClick(MouseEvent event) {
        if (currentTower == null || currentTower.isEmpty()) {
            return;
        }
        Point2D localPoint = gameGrid.sceneToLocal(event.getSceneX(), event.getSceneY());
        int colIndex = (int) (localPoint.getX() / (gameGrid.getWidth() / gameGrid.getColumnCount()));
        int rowIndex = (int) (localPoint.getY() / (gameGrid.getHeight() / gameGrid.getRowCount()));
        if (colIndex >= 0 && colIndex < 18 && rowIndex >= 0 && rowIndex < 20) {
            if (currentIndexGraph[rowIndex][colIndex] == 0) {
                addTower(colIndex, rowIndex);
                updateComboBox();
            }
        }
    }

    /**Gives a warning message to the player if an execption is called.
     * Sets a warningLabel Label to turn into the warningText parameter.
     * Fades after 3 seconds
     * @author msh254
     * @param warningText
     */
    private void setWarning(String warningText) {
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

    /**Adds a tower to the game based on it's row and column index.
     *
     * @author msh254,nsr36
     * @param colIndex
     * @param rowIndex
     */
    private void addTower(int colIndex, int rowIndex) {
        Point newTowerPosition = new Point(colIndex, rowIndex);
        if (towerPositions.containsKey(newTowerPosition)) {
            System.out.println("A tower already exists at this position!");
            setWarning("A tower already exists at this location");
            return;
        }
        if (buildingAndNatureMap.getBuildingAndNatureGraph()[rowIndex][colIndex] != 0) {
            setWarning("Can't place a tower on an object");
            return;
        }
        Tower tower = retrieveSelectedTower();
        gameManager.placeTowerAt(newTowerPosition, tower);
        towersUsed.add(tower);
        ImageView towerSprite = new ImageView();
        towerImageViewToTower.put(towerSprite, tower);
        towerSprite.setFitWidth(35);
        towerSprite.setFitHeight(35);
        towerSprite.setPreserveRatio(true);
        towerSprite.setSmooth(true);
        towerPositions.put(newTowerPosition, towerSprite);
        gameGrid.add(towerSprite, colIndex, rowIndex);
        isGridShooting.put(new Point(colIndex,rowIndex), false);
        animateTower(towerSprite, tower.getName());
        towerSelectionComboBox.getItems().remove(tower.getName());
        currentTower = null;
    }

    /**retrieves the currently selected tower in the comboBox.
     * As the comboBox can only store Strings, this String is converted
     * to a Tower using the gameManager.getTowerClass(towerName) function
     *
     * @author msh254
     * @return Tower
     */
    private Tower retrieveSelectedTower() {
        String towerName = towerSelectionComboBox.getValue();
        return gameManager.getTowerClass(towerName);
    }

    /** Animates the towerSprite.
     * creates an animationTimer instance of AnimationTimer.
     * overrides the handle for animationTimer to parse specific information / functions.
     *
     *
     * @author msh254, ' Soumyashree Sahoo ' (youtube -> see readME (2))
     * @param towerSprite
     * @param towerName
     */
    private void animateTower(ImageView towerSprite, String towerName) {
        final int frameCount = spriteSheet.getFrameCount();
        AnimationTimer animationTimer = new AnimationTimer() {
            private int frameIndex = 0;
            private long lastUpdatesprite = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdatesprite >= updateInterval / frameCount) {
                    Image frame = spriteSheet.getSpriteFrame(towerName, frameIndex);
                    towerSprite.setImage(frame);
                    towerSprite.setPreserveRatio(true);
                    towerSprite.setSmooth(true);
                    frameIndex = (frameIndex + 1) % frameCount;
                    lastUpdatesprite = now;
                }
            }
        };
        animationTimer.start();
    }

    /**Similar to animateTower(), this function sets the GameLoop up, using the AnimationTimer function.
     * checks if the difference between now and lastUpdate is >= our set updateInterval, this decides how often the game
     * updated via updateGame()
     *
     * @author msh254, ' Soumyashree Sahoo ' (youtube -> see readME (3))
     */
    private void setupGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (gameRunning) {
                    if (now - lastUpdate >= updateInterval) {
                        updateGame();
                        lastUpdate = now;
                    }

                    if (now - lastUpdate1 >= 100_000_000) {
                        checkTowersTargeting();
                        time += 1;
                        lastUpdate1 = now;
                    }

                    if (currentCartIndex < carts.size() && now - startTime >= carts.get(currentCartIndex).spawnTime) {
                        if (now - startTime >= carts.get(currentCartIndex).spawnTime) {
                        }
                        spawnCart(carts.get(currentCartIndex).cart);
                        currentCartIndex++;
                    }
                }
            }
        };
        gameLoop.start();
    }

    /** Function when 'Start' Button is clicked.
     * If so, the cart path and directionMap is loaded.
     * startTime is initialised and cart Index is set to 0
     *
     * @author msh254, nsr36
     */
    @FXML
    private void gameStarted() {
        if (!gameStartState) {
            gameStartState = true;
            carts = initialCarts;
            cartPath = CartPath.getCartPathForRound(gameManager.getCurrentRound());
            cartDirectionMap = CartDirectionMap.getDirectionMapForRound(gameManager.getCurrentRound());
            currentCartIndex = 0;
            startTime = System.nanoTime();
            gameRunning = true;
            lastUpdate = System.nanoTime();
        }
        else {
            setWarning("You already Started the Game");
        }
    }

    /**This occurs when the main menu button is pressed, transitioning to the main menu screen
     * As this is the last 'update' for the round, most of the clearing, resetting and updating is done here.
     * Additionally, a Label is set depending on wether you won or lost the round.
     *
     * @author msh254, nsr36
     */
    @FXML
    private void mainMenu() {
        gameManager.setModifiersSelectedFalse();
        gameGrid.getChildren().clear();
        gamePane.getChildren().clear();
        cartTokens.clear();
        towerPositions.clear();
        cartHealthBars.clear();
        gameRunning = false;
        gameStartState = false;
        currentCartIndex = 0;
        carts.clear();
        gameManager.setModifiersInitialisedFalse();
        gameManager.changeMoneyAmount(moneyEarned);
        gameManager.incrementTotalMoney(moneyEarned);
        gameManager.setTowersUsedInPreviousRound(towersUsed);
        if (winOrLoseLabel.getTextFill() == Color.GREEN) {
            if (gameManager.getCurrentRound() == gameManager.getNumOfRounds()) {
                gameManager.gameToWinMenuScreen();
            }
            else {
                gameManager.changeCurrentRound();
                gameManager.gameToMainMenuScreen();
            }
        }
        else if (winOrLoseLabel.getTextFill() == Color.ORANGE) {
            gameManager.gameToMainMenuScreen(); // If round failed, remain on same round and go to main menu
        }

        else {
            gameManager.gameToFailMenuScreen();
        }
    }

    /**Gets the initial cart direction from the indexGraph cartDirectionMap.
     *
     * @author msh254, nsr36
     * @param startPosition
     * @return Integer (start 'direction') -> 0 = left, 1 = up, 2 = down, 3 = right
     */
    private int getInitialCartDirection(Point startPosition) {
        if (startPosition != null) {
            return cartDirectionMap.getDirectionGraph()[startPosition.y][startPosition.x];
        }
        return 0;
    }

    /** Spawns a Cart into the Gridpane. Fits the Cart to the gridLength and Width (50x50px)
     *  Sets the ImageView of the Cart onto the GridPane. Additionally, also creates a HealthBar to go underneath
     *  the Cart.
     *
     * @author msh254, nsr36, 'Bro Code , Random Code' (youtube -> see readME (4))
     * @param cart
     */
    private void spawnCart(Cart cart) {
        ImageView cartToken = new ImageView();
        cartToken.setFitWidth(50);
        cartToken.setFitHeight(50);
        cartToken.setImage(cartSprite.getSpriteFrame(cart.getResourceType(), 1));
        int[][] pathGraph = cartPath.getIndexGraph();
        Point startPosition = getCartPosition(pathGraph, 1);
        System.out.println(startPosition);
        cart.setDirection(getInitialCartDirection(startPosition));
        if (startPosition != null) {
            double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
            double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
            double startX = startPosition.x * cellWidth + cellWidth / 2;
            double startY = startPosition.y * cellHeight + cellHeight / 2 - cartToken.getFitHeight() / 2;
            cartToken.setLayoutX(startX);
            cartToken.setLayoutY(startY);
            gamePane.getChildren().add(cartToken);
            cartTokens.put(cart, cartToken);
            cartSteps.put(cart, 1);
            Rectangle healthBar = new Rectangle(50, 5);
            healthBar.setLayoutX(startX);
            healthBar.setLayoutY(startY + 55); // this is the offset for the healthbar
            healthBar.setFill(Color.GREEN);
            gamePane.getChildren().add(healthBar);
            cartHealthBars.put(cart, healthBar);
            updateHealthBar(cart);
            System.out.println("Spawned cart at: " + startX + ", " + startY);
        } else {
            System.out.println("Start position not found in pathGraph.");
        }
    }

    /** A function which updates the Health Bar of a Cart depending on the damage inflicted
     * (how fill the cart is)
     *
     * @author msh254
     * @param cart
     */
    private void updateHealthBar(Cart cart) {
        Rectangle healthBar = cartHealthBars.get(cart);
        if (healthBar != null) {
            double fillPercentage = (double) (cart.getSize() - cart.getCurrentAmount()) / cart.getSize();
            healthBar.setWidth(50 * fillPercentage);
            if (fillPercentage < 0.3) {
                healthBar.setFill(Color.RED);
            } else if (fillPercentage < 0.6) {
                healthBar.setFill(Color.YELLOW);
            } else {
                healthBar.setFill(Color.GREEN);
            }
        }
    }

    /** Rotates a tower towards the 'target' (Cart). Takes a tower and target, the tower x and y co-ordinates are
     * obtained via the tower.getLayoutX() and tower.getLayoutY() function, with offsetting to obtain the center
     * of the tower (hence to tower.getFitWidth() / 2) ...
     * Once the co-ordinate of the tower is obtained, an angle is calculated between the tower and the target.
     * As all the Towers are facing UP in the 'Tower'.png, an offset of 90 degrees is necessary to orientate the tower.
     *
     * @author msh254 (5 -> see readME)
     *
     * @param tower
     * @param target
     */
    private void rotateTowerTowardsTarget(ImageView tower, Point2D target) {
        Point2D towerPosition = new Point2D(tower.getLayoutX() + tower.getFitWidth() / 2, tower.getLayoutY() + tower.getFitHeight() / 2);
        double angle = Math.atan2(target.getY() - towerPosition.getY(), target.getX() - towerPosition.getX()) * 180 / Math.PI;
        tower.setRotate(angle + 90);
    }


    /** A function which checks the targeting of Towers. Loops through every Tower and then through every Cart.
     * For every tower, a co-ordinate is obtained, and for every cart (for the Tower), if the Cart is in range of
     * the tower, it is added to the cartsInRange HashMap.
     *
     * Furthermore, once all the carts in distance of the Tower have been calculated, the carts are then sorted into
     * distance (From tower), and wether the carts share the same resourceType as the Tower.
     * This is done to specify targeting for the Tower.
     *
     * @author msh254, nsr36 (6 -> see readME)
     */
    private void checkTowersTargeting() {
        double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
        double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
        for (Map.Entry<Point, ImageView> entry : towerPositions.entrySet()) {
            Point towerGridPos = entry.getKey();
            ImageView towerSprite = entry.getValue();
            if (Math.floorMod(time, 11-towerImageViewToTower.get(towerSprite).getReloadSpeed()) == 0) {
                double towerCenterX = (towerGridPos.x + 0.5) * cellWidth;
                double towerCenterY = (towerGridPos.y + 0.5) * cellHeight;
                Map<Cart, Double> cartsInRange = new HashMap<>();
                for (Map.Entry<Cart, ImageView> cartEntry : cartTokens.entrySet()) {
                    Cart cart = cartEntry.getKey();
                    ImageView cartToken = cartEntry.getValue();
                    Bounds cartBounds = cartToken.getBoundsInParent();
                    double cartCenterX = cartBounds.getMinX() + cartBounds.getWidth() / 2;
                    double cartCenterY = cartBounds.getMinY() + cartBounds.getHeight() / 2;
                    double distance = Math.sqrt(Math.pow(towerCenterX - cartCenterX, 2) + Math.pow(towerCenterY - cartCenterY, 2));
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
                    shootProjectile(towerGridPos, closestCart);
                }
            }
        }
    }

    /** The first function out of 3 which handles projectile calculation.
     *  This first step 'shoots' a projectile (calculates the start and finish position for a projectile)
     *  Depending on the distance from the tower to the cart, certain aspects of the finish position
     *  of the projectile are altered.
     *  e.g If the cart is 0.5 > distance > 1.25 of the Tower, then there is a slight offset to compensate
     *  for the natural movement of the cart.
     *
     * @author msh254, nsr36
     *
     * @param towerGridPos
     * @param targetCart
     */
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
            double distance = towerCenter.distance(cartCenter) / cellWidth;
            double targetX = cartCenter.getX();
            double targetY = cartCenter.getY();
            if (distance > 0.8 && distance <= 1.25) {
                Point nextPosition = getCartPosition(cartPath.getIndexGraph(), cartSteps.get(targetCart) + 1);
                if (nextPosition != null) {
                    double nextPosX = nextPosition.x * cellWidth + cellWidth / 2;
                    double nextPosY = nextPosition.y * cellHeight + cellHeight / 2;
                    targetX += (nextPosX - targetX) / 2;
                    targetY += (nextPosY - targetY) / 2;
                }
            } else if (distance > 1.25 && distance <= 2) {
                Point nextPosition = getCartPosition(cartPath.getIndexGraph(), cartSteps.get(targetCart) + 1);
                if (nextPosition != null) {
                    double nextPosX = nextPosition.x * cellWidth + cellWidth / 2;
                    double nextPosY = nextPosition.y * cellHeight + cellHeight / 2;
                    targetX += (nextPosX - targetX) / 1.6;
                    targetY += (nextPosY - targetY) / 1.6;
                }
            }
            launchProjectile(towerCenter.getX(), towerCenter.getY(), targetX, targetY, targetCart, shootingTower, towerGridPos);
        }
    }


    /**The Second Function in projectile calculations.
     * This function handles the animation of the projectile.
     * Sets the projectile width and height at (10x18), and also sets the projectile angle (so the projectile is directed
     * towards the Cart). This is done similarly for the angle calculation of towers (taking the angle from the projectile start
     * position to the projectile finish position)
     *
     * @author msh254, 'Random Code , Genuine Coder, Bro Code' (youtube -> see readME) (7)
     *
     * @param startX
     * @param startY
     * @param targetX
     * @param targetY
     * @param targetCart
     * @param shootingTower
     * @param towerGridPos
     */
    private void launchProjectile(double startX, double startY, double targetX, double targetY, Cart targetCart, Tower shootingTower, Point towerGridPos) {
        ImageView projectile = new ImageView(Projectile.getProjectileSprite(getProjectileSprite(shootingTower.getName())));
        projectile.setFitWidth(10);
        projectile.setFitHeight(18);
        projectile.setLayoutX(startX);
        projectile.setLayoutY(startY);
        gamePane.getChildren().add(projectile);
        double angle = Math.atan2(targetY - startY, targetX - startX) * 180 / Math.PI;
        projectile.setRotate(angle - 90);
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.4), projectile);
        isGridShooting.replace(towerGridPos, true);
        transition.setByX(targetX - startX);
        transition.setByY(targetY - startY);
        transition.setOnFinished(event -> {
            checkProjectileCollision(projectile, targetCart, shootingTower, towerGridPos);
            gamePane.getChildren().remove(projectile);
        });
        transition.play();
    }

    /**The last function in projectile calculation.
     * This function handles the collision checking for projectiles. Also handles damage calculations for the Cart
     * (including Cart sinking scenario)
     * Checks for resource type match in order to calculate damage % for the projectile.
     *
     * @author msh254, nsr36
     *
     * @param projectile
     * @param targetCart
     * @param shootingTower
     * @param towerGridPos
     */
    private void checkProjectileCollision(ImageView projectile, Cart targetCart, Tower shootingTower, Point towerGridPos) {
        isGridShooting.replace(towerGridPos, false);
        ImageView cartToken = cartTokens.get(targetCart);
        if (cartToken == null) return;
        Bounds projectileBounds = projectile.getBoundsInParent();
        Bounds cartBounds = cartToken.getBoundsInParent();

        if (projectileBounds.intersects(cartBounds)) {
            switch (shootingTower.getResourceType()) {
                case "Bronze":
                    damageIncreaseBonus = damageIncreaseBronze;
                    System.out.println("Bonus Damage Bronze " + damageIncreaseBonus);
                    break;
                case "Silver":
                    damageIncreaseBonus = damageIncreaseSilver;
                    System.out.println("Bonus Damage Silver "+ damageIncreaseBonus);
                    break;
                case "Gold":
                    damageIncreaseBonus = damageIncreaseGold;
                    System.out.println("Bonus Damage Gold "+ damageIncreaseBonus);
                    break;
                case "Diamond":
                    damageIncreaseBonus = damageIncreaseDiamond;
                    System.out.println("Bonus Damage Diamond " + damageIncreaseBonus);
                    break;
                case "Emerald":
                    damageIncreaseBonus = damageIncreaseEmerald;
                    System.out.println("Bonus Damage Emerald");
                    break;
                case "Ruby":
                    damageIncreaseBonus = damageIncreaseRuby;
                    System.out.println("Bonus Damage Ruby");
                    break;
                }
            }
            boolean isResourceMatch = targetCart.getResourceType().equals(shootingTower.getResourceType());
            int damage;
            if (isResourceMatch) {
                damage = (int) (difficultyScaling * randomEventMultiplier*damageIncreaseBonus * cartFillIncrease * shootingTower.getMaxAmount() / 10); // Full damage calculation
                System.out.println("Damage Full");
            } else {
                damage = (int) (difficultyScaling * cartFillIncrease * randomEventMultiplier * damageIncreaseBonus * shootingTower.getMaxAmount() / 25); // Reduced damage (40% of full damage)
                System.out.println("Damage 40%");
            }
            targetCart.fillCart(damage);
            if (targetCart.isCartFilled()) {
                System.out.println("Cart destroyed");
                gamePane.getChildren().remove(cartToken);
                cartTokens.remove(targetCart);
                cartsLeft -= 1;
                moneyEarned += cartRewards.get(targetCart.getResourceType());
                gameManager.incrementTotalCartsDestroyed();
                Rectangle healthBar = cartHealthBars.get(targetCart);
                if (healthBar != null) {
                    gamePane.getChildren().remove(healthBar);
                }
            } else {
                System.out.println("Projectile hit the cart! Damage: " + damage);
                updateHealthBar(targetCart); // Update health bar based on current health
            }
        }


    /**A function which iterates through pathGraph given the previous step.
     * iterates through the whole of pathGraph until it finds the index after the prev step.
     *
     * @author msh254
     * @param pathGraph
     * @param step
     * @return Point (of next step)
     */
    private Point getCartPosition(int[][] pathGraph, int step) {
        for (int i = 0; i < pathGraph.length; i++) {
            for (int j = 0; j < pathGraph[i].length; j++) {
                if (pathGraph[i][j] == step) {
                    return new Point(j, i);
                }
            }
        }
        return null;
    }

    /**Updates the direction of the Cart Sprite (what direction the ImageView is facing)
     * depending on the direction parsed through.
     *
     * @author msh254
     * @param cart
     * @param cartToken
     * @param direction
     */
    private void updateCartSprite(Cart cart, ImageView cartToken, int direction) {
        cartToken.setImage(cartSprite.getSpriteFrame(cart.getResourceType(), direction));
    }

    /** A function which controls how the carts are moved through the gridPane. Checks for the next
     * index for the cart (through CartPath indexGraph), and moves the cart accordingly.
     * Iterates through all the Carts in the game. If a cart reaches the end of the Path, a 'life' is lost.
     *
     * @author msh254, nsr36, ' Bro Code, Genuine Coder, Random Code ' (youtube -> see readME) (8)
     *
     */
    private void moveCarts() {
        Iterator<Map.Entry<Cart, ImageView>> iterator = cartTokens.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Cart, ImageView> entry = iterator.next();
            Cart cart = entry.getKey();
            Rectangle healthBar = cartHealthBars.get(cart);
            ImageView cartToken = entry.getValue();
            Integer currentStep = cartSteps.get(cart);
            if (currentStep == null) {
                System.out.println("Error: No tracking info for cart. Removing cart.");
                gamePane.getChildren().remove(cartToken);
                iterator.remove();
                if (healthBar != null) {
                    gamePane.getChildren().remove(healthBar);
                }
                continue;
            }
            Point nextPosition = getCartPosition(cartPath.getIndexGraph(), currentStep + 1);
            if (nextPosition != null) {
                double cellWidth = gameGrid.getWidth() / gameGrid.getColumnCount();
                double cellHeight = gameGrid.getHeight() / gameGrid.getRowCount();
                double targetX = nextPosition.x * cellWidth + cellWidth / 2 - cartToken.getFitWidth() / 8;
                double targetY = nextPosition.y * cellHeight + cellHeight / 2 - cartToken.getFitHeight() / 2;
                TranslateTransition transition = new TranslateTransition(Duration.seconds(cart.getSpeed()*speedIncrease), cartToken);
                transition.setToX(targetX - cartToken.getLayoutX());
                transition.setToY(targetY - cartToken.getLayoutY());
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

    /** A function which updates the game. This is called every game Tick, specified by the Interval updateInterval
     * updatesLabels, moveCarts, checkTowerTargeting, checkWinOrLose
     *
     * @author msh254, nsr36
     */
    private void updateGame() {
        updateLabels();
        moveCarts();
        checkWinOrLose();
    }

    /**Updates the labels in the game (necessary for displaying the lives lost in the round)
     *
     * @author nsr36
     */
    private void updateLabels() {
        livesLabel.setText("Lives: " + gameManager.getLives());
        moneyLabel.setText("Money: " + gameManager.getMoneyAmount());
        cartsLeftLabel.setText("Carts Left: " + cartsLeft);
    }

    /**Check wether you have won or lost the game.
     * The game is 'Won' when all carts have been sunk
     * The game is 'Lost' if any cart reaches the end.
     *
     * @author msh254, nsr36
     */
    private void checkWinOrLose() {
        boolean allCartsSpawned = currentCartIndex >= carts.size();
        if (allCartsSpawned && cartsLeft == 0) {
            gameRunning = false;
            winOrLoseLabel.toFront();
            mainMenuButton.toFront();
            winOrLoseLabel.setText("You cleared Round " + gameManager.getCurrentRound() + "!");
            winOrLoseLabel.setTextFill(Color.GREEN);
            mainMenuButton.setVisible(true);
            if (initialCarts.size() - cartsLeft != 0) {
                moneyEarned = moneyEarned/(initialCarts.size() - cartsLeft);
            }
            moneyEarnedLabel.setText("You earned $" + moneyEarned);
            updateLabels();
        }
        if (gameManager.getLives() <= 0) {
            gameRunning = false;
            winOrLoseLabel.toFront();
            mainMenuButton.toFront();
            winOrLoseLabel.setText("You lost all your lives!");
            winOrLoseLabel.setTextFill(Color.RED);
            mainMenuButton.setVisible(true);
            if (initialCarts.size() - cartsLeft != 0) {
                moneyEarned = moneyEarned/(initialCarts.size() - cartsLeft);
            }
            moneyEarnedLabel.setText("You earned $" + moneyEarned);
            updateLabels();
        }
    }

    /** when the player selects a tower form the comboBox towerSelectionComboBox
     *
     * @author msh254
     */
    @FXML
    private void towerSelected() {
        currentTower = towerSelectionComboBox.getValue();
    }

    /** Updates the comboBox to display "" (nothing).
     *
     * @author msh254
     */
    private void updateComboBox() {
        towerSelectionComboBox.setValue("");
    }
}

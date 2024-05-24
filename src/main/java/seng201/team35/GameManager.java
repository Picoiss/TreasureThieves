package seng201.team35;

import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * GameManager class that controls the flow of the app and holds data of important variables
 * @author msh254, nsr36
 */

public class GameManager {
    private String playerName;
    private int moneyAmount = 0;
    private int totalMoney = 0;
    private int numOfRounds;
    private String gameDifficulty;
    private int lives;
    private int currentRound = 1;
    private int totalCartsDestroyed = 0;
    private List<Tower> towersUsedInPreviousRound;
    private Map<Point, Tower> towerPositionMap = new HashMap<>();
    private List<Tower> mainTowerList = new ArrayList<>();
    private List<Tower> reserveTowerList= new ArrayList<>();
    private List<Upgrade> upgradesList= new ArrayList<>();
    private final List<Tower> defaultTowers = new ArrayList<>();
    private final List<Upgrade> defaultUpgrades = new ArrayList<>();
    private final Consumer<GameManager> setupScreenLauncher;
    private final Consumer<GameManager> mainMenuLauncher;
    private final Consumer<GameManager> shopLauncher;
    private final Consumer<GameManager> inventoryLauncher;
    private final Consumer<GameManager> gameLauncher;
    private final Consumer<GameManager> failMenuLauncher;
    private final Consumer<GameManager> winMenuLauncher;
    private final Runnable clearScreen;
    private String currentModifier;
    private Boolean modifiersInitialised = false;
    private String globalModifier1;
    private String globalModifier2;
    private String globalModifier3;
    private Boolean modifierSelected = false;

    /**Constructor for GameManager.
     * Creates a GameManager Instance and initialises the launchers of
     * all screens and initialises all available towers and upgrades
     *
     * @author msh254, nsr36
     *
     * @param setupScreenLauncher used to launch the setup screen
     * @param mainMenuLauncher used to launch the main menu screen
     * @param shopLauncher used to launch the shop screen
     * @param inventoryLauncher used to launch the inventory screen
     * @param gameLauncher used to launch the gameplay screen
     * @param failMenuLauncher used to launch the fail menu screen
     * @param winMenuLauncher used to launch the win menu screen
     * @param clearScreen used to clear the current screen
     */
    public GameManager(Consumer<GameManager> setupScreenLauncher,
                       Consumer<GameManager> mainMenuLauncher,
                       Consumer<GameManager> shopLauncher,
                       Consumer<GameManager> inventoryLauncher,
                       Consumer<GameManager> gameLauncher,
                       Consumer<GameManager> failMenuLauncher,
                       Consumer<GameManager> winMenuLauncher,
                       Runnable clearScreen) {
        this.setupScreenLauncher = setupScreenLauncher;
        this.mainMenuLauncher = mainMenuLauncher;
        this.shopLauncher = shopLauncher;
        this.inventoryLauncher = inventoryLauncher;
        this.gameLauncher = gameLauncher;
        this.failMenuLauncher = failMenuLauncher;
        this.winMenuLauncher = winMenuLauncher;
        this.clearScreen = clearScreen;
        Tower bronzeArcher = new Tower("Bronze Archer", 1, 100, "Bronze", 100);
        Tower bronzeDwarf = new Tower("Bronze Dwarf", 4, 125, "Bronze", 125);
        Tower bronzeVillager = new Tower("Bronze Villager", 6, 150, "Bronze", 150);
        Tower silverKnight = new Tower("Silver Knight", 1, 150, "Silver", 150);
        Tower silverPriest = new Tower("Silver Priest", 4, 170, "Silver", 170);
        Tower silverAlchemist = new Tower("Silver Alchemist", 5, 190, "Silver", 190);
        Tower goldGiant = new Tower("Gold Giant", 2, 200, "Gold", 200);
        Tower goldGoblin = new Tower("Gold Goblin", 3, 240, "Gold", 240);
        Tower goldPirate = new Tower("Gold Pirate", 4, 275, "Gold", 275);
        Tower diamondMage = new Tower("Diamond Mage", 2, 400, "Diamond", 400);
        Tower diamondNecromancer = new Tower("Diamond Necromancer", 3, 425, "Diamond", 425);
        Tower diamondMinotaur = new Tower("Diamond Minotaur", 4, 460, "Diamond", 460);
        Tower emeraldElf = new Tower("Emerald Elf", 2, 575, "Emerald", 575);
        Tower emeraldPhoenix = new Tower("Emerald Phoenix", 6, 625, "Emerald", 625);
        Tower emeraldPegasus = new Tower("Emerald Pegasus", 5, 700, "Emerald", 700);
        Tower rubyDragon = new Tower("Ruby Dragon", 5, 700, "Ruby", 700);
        Tower rubyOrcMage = new Tower("Ruby OrcMage", 4, 850, "Ruby", 850);
        Tower rubyGolem = new Tower("Ruby Golem", 6, 1000, "Ruby", 1000);
        defaultTowers.addAll(List.of(bronzeArcher, bronzeDwarf, bronzeVillager, silverKnight, silverPriest,
                silverAlchemist, goldGiant, goldGoblin, goldPirate, diamondMage, diamondNecromancer, diamondMinotaur,
                emeraldElf, emeraldPhoenix, emeraldPegasus, rubyDragon, rubyOrcMage, rubyGolem));
        Upgrade bronzeUpgrade = new Upgrade(10, 10, "Bronze", 100, "Active");
        Upgrade silverUpgrade = new Upgrade(10, 10, "Silver", 150, "Active");
        Upgrade goldUpgrade = new Upgrade(10, 10, "Gold", 200, "Active");
        Upgrade diamondUpgrade = new Upgrade(10, 10, "Diamond", 350, "Active");
        Upgrade emeraldUpgrade = new Upgrade(10, 10, "Emerald", 500, "Active");
        Upgrade rubyUpgrade = new Upgrade(10, 10, "Ruby", 700, "Active");
        defaultUpgrades.addAll(List.of(bronzeUpgrade, silverUpgrade, goldUpgrade,
                diamondUpgrade, emeraldUpgrade, rubyUpgrade));
        launchSetupScreen();
    }

    /**
     * Set the player name
     * @param name player name
     */
    public void setPlayerName(String name) { this.playerName = name; }

    /**
     * Returns the player's name as a string
     * @return playerName string
     */
    public String getPlayerName() { return playerName; }

    /**
     * Set the number of rounds to be played in one run of the game
     * @param rounds number of rounds to play
     */
    public void setNumOfRounds(int rounds) {
        this.numOfRounds = rounds;
    }

    /**
     * Return the number of rounds in this run of the game
     * @return numOfRounds int
     */
    public int getNumOfRounds() {
        return numOfRounds;
    }

    /**
     * Return the total money the player has earned through the entire run of the game
     * @return totalMoney int
     */
    public int getTotalMoney() {
        return totalMoney;
    }

    /**
     * Increase the total money by a reward
     * @param money amount to increase totalMoney
     */
    public void incrementTotalMoney(int money) {
        this.totalMoney += money;
    }

    /**
     * Return the total number of carts destroyed through the entire run of the game
     * @return totalCartsDestroyed int
     */
    public int getTotalCartsDestroyed() {
        return totalCartsDestroyed;
    }

    /**
     * Increase the total number of carts destroyed by one
     * The increment is fixed at 1 because this method at the same time a cart is destroyed
     */
    public void incrementTotalCartsDestroyed() {
        this.totalCartsDestroyed += 1;
    }

    /**
     * Set the game difficulty for the entire run of the game
     * @param difficulty chosen difficulty of the game
     */
    public void setGameDifficulty(String difficulty) {
        this.gameDifficulty = difficulty;
    }

    /**
     * Return the game difficulty
     * @return gameDifficulty String
     */
    public String getGameDifficulty() {
        return gameDifficulty;
    }

    /**
     * Set the lives a player starts the game with
     * @param lives number of lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Return the number of lives remaining for the rest of the game
     * @return lives int
     */
    public int getLives() {
        return lives;
    }

    /**
     * Reduce the number of lives by a given number
     * @param livesLost number of lives to reduce
     */
    public void changeLives(int livesLost) {
        lives -= livesLost;
    }

    /**
     * Increase the value of current round by one
     * The increment is fixed at 1 since a player can only progress one round at a time
     */
    public void changeCurrentRound() {
        currentRound += 1;
    }

    /**
     * Return the current round (the lowest round the player has not cleared)
     * @return currentRound int
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * Set the main tower list to a given list of towers
     * @param towerList list of towers
     */
    public void setMainTowerList(List<Tower> towerList) { this.mainTowerList = towerList; }

    /**
     * Return the main tower list
     * @return mainTowerList
     */
    public List<Tower> getMainTowerList() {
        return mainTowerList;
    }

    /**
     * Add a tower to the main tower list
     * @param tower the tower needed to be added
     */
    public void addMainTower(Tower tower) {
        mainTowerList.add(tower);
    }

    /**
     * Remove a tower from the main tower list
     * @param tower the tower that is needed to be removed
     */
    public void removeMainTower(Tower tower) {
        mainTowerList.remove(tower);
    }

    /**
     * Return the reserve tower list
     * @return reserveTowerList
     */
    public List<Tower> getReserveTowerList() { return reserveTowerList; }

    /**
     * Add a tower to the reserve tower list
     * @param tower the tower needed to be added
     */
    public void addReserveTower(Tower tower) {
        reserveTowerList.add(tower);
    }

    /**
     * Remove a tower from the reserve tower list
     * @param tower the tower that is needed to be removed
     */
    public void removeReserveTower(Tower tower) {
        reserveTowerList.remove(tower);
    }

    /**
     * Return the list of upgrades the player owns
     * @return upgradesList
     */
    public List<Upgrade> getUpgradesList() { return upgradesList; }

    /**
     * Add an upgrade to the upgrade list
     * @param upgrade the upgrade that is needed to be added
     */
    public void addUpgrade(Upgrade upgrade) {
        upgradesList.add(upgrade);
    }

    /**
     * Remove an upgrade from the upgrade list
     * @param upgrade the upgrade that is needed to be removed
     */
    public void removeUpgrade(Upgrade upgrade) {
        upgradesList.remove(upgrade);
    }

    /**
     * Return the list of default towers (all towers in the game)
     * @return defaultTowers
     */
    public List<Tower> getDefaultTowers() { return defaultTowers; }

    /**
     * Return the list of default upgrades (all upgrades in the game)
     * @return defaultUpgrades
     */
    public List<Upgrade> getDefaultUpgrades() { return defaultUpgrades; }

    /**
     * Transition to the setup screen
     */
    public void launchSetupScreen() { setupScreenLauncher.accept(this); }

    /**
     * Close the setup screen and open the main menu screen
     */
    public void closeSetupScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }

    /**
     * Open the main menu screen
     */
    public void launchMainMenuScreen() {
        mainMenuLauncher.accept(this);
    }

    /**
     * Close the main menu screen and open the shop screen
     */
    public void mainMenuToShopScreen() {
        clearScreen.run();
        launchShopScreen();
    }

    /**
     * Close the main menu screen and open the inventory screen
     */
    public void mainMenuToInventoryScreen() {
        clearScreen.run();
        launchInventoryScreen();
    }

    /**
     * Close the main menu screen and open the game screen
     */
    public void mainMenuToGameScreen() {
        clearScreen.run();
        launchGameScreen();
    }

    /**
     * Open the shop screen
     */
    public void launchShopScreen() {
        shopLauncher.accept(this);
    }

    /**
     * Close the shop screen and open the main menu screen
     */
    public void closeShopScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }

    /**
     * Open the inventory screen
     */
    public void launchInventoryScreen() {
        inventoryLauncher.accept(this);
    }

    /**
     * Close the inventory screen and open the main menu screen
     */
    public void closeInventoryScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }

    /**
     * Open the game screen
     */
    public void launchGameScreen() {
        gameLauncher.accept(this);
    }

    /**
     * Close the game screen and open the main menu screen
     */
    public void gameToMainMenuScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }

    /**
     * Close the game screen and open the fail menu screen
     */
    public void gameToFailMenuScreen() {
        clearScreen.run();
        launchFailMenuScreen();
    }

    /**
     * Close the game screen and open the win menu screen
     */
    public void gameToWinMenuScreen() {
        clearScreen.run();
        launchWinMenuScreen();
    }

    /**
     * Open the fail menu screen
     */
    public void launchFailMenuScreen() {
        failMenuLauncher.accept(this);
    }

    /**
     * Open the win menu screen
     */
    public void launchWinMenuScreen() {
        winMenuLauncher.accept(this);
    }

    /**
     * Return the amount of money the player currently has
     * @return moneyAmount int
     */
    public int getMoneyAmount() { return moneyAmount;}

    /**
     * Change the amount of money the play currently has
     * Inputting a positive integer increases the money
     * Inputting a negative integer reduces the money
     * @param changeAmount amount to change
     */
    public void changeMoneyAmount(int changeAmount) {
        moneyAmount += changeAmount;
    }

    /**
     * Reset the list with the towers that were used in the most recent round played
     * This is used to increase the change of one of these towers to break
     * @param towers
     */
    public void setTowersUsedInPreviousRound(List<Tower> towers) {
        towersUsedInPreviousRound = towers;
    }

    /**
     * Return a list of towers used in the most recent round played
     * @return towersUsedInPreviousRound
     */
    public List<Tower> getTowersUsedInPreviousRound() {
        return towersUsedInPreviousRound;
    }

    /**
     * Return a tower class instance using the tower name to identify it
     * @param towerName name of tower
     * @return tower
     */
    public Tower getTowerClass(String towerName) {
        for (Tower tower : mainTowerList) {
            if (tower.getName().equals(towerName)) {
                return tower;
            }
        }
        for (Tower tower : defaultTowers) {
            if (tower.getName().equals(towerName)) {
                return tower;
            }
        }
        System.out.println("Tower with name '" + towerName + "' not found.");
        return null;
    }

    /**
     * Return an upgrade class instance using the upgrade name to identify it
     * @param upgradeName name of upgrade
     * @return upgrade
     */
    public Upgrade getUpgradeClass(String upgradeName) {
        for (Upgrade upgrade : defaultUpgrades) {
            if (Objects.equals(upgradeName, upgrade.getResourceType())) {
                return upgrade;
            }
        }
        System.out.println("potential error in getting upgrade class from type");
        return null;
    }

    /**
     * Store the position of a tower in the grid while playing a round
     * @param position where the tower is placed
     * @param tower the tower
     */
    public void placeTowerAt(Point position, Tower tower) {
        towerPositionMap.put(position, tower);
    }

    /**
     * Return a tower by searching the given position
     * @param position point to search
     * @return tower
     */
    public Tower getTowerAt(Point position) {
        return towerPositionMap.get(position);
    }

    /**
     * Check whether a modifier has been selected or not
     * @return modifierSelected Boolean
     */
    public Boolean getModifierSelected() {
        return modifierSelected;
    }

    /**
     * Set modifierSelected true to indicate a modifier has been selected
     */
    public void setModifierSelectedTrue() {
        modifierSelected = true;
    }

    /**
     * Set modifierSelected false to indicate a modifier has been not been selected
     */
    public void setModifiersSelectedFalse() {
        modifierSelected = false;
    }

    /**
     * Check whether the modifiers have been initialised or not
     * @return modifiersInitialised Boolean
     */
    public boolean getModifiersInitialised() {
        return modifiersInitialised;
    }

    /**
     * Set modifiersInitialised true to indicate the modifiers have been initialised
     */
    public void setModifiersInitialisedTrue() {
        modifiersInitialised = true;
    }

    /**
     * Set modifiersInitialised false to indicate the modifiers have not been initialised
     */
    public void setModifiersInitialisedFalse() {
        modifiersInitialised = false;
    }

    /**
     * Set the first modifier to the given string
     * @param modifier1 string
     */
    public void setGlobalModifier1(String modifier1) {
        globalModifier1 = modifier1;
    }

    /**
     * Set the second modifier to the given string
     * @param modifier2 string
     */
    public void setGlobalModifier2(String modifier2) {
        globalModifier2 = modifier2;
    }

    /**
     * Set the third modifier to the given string
     * @param modifier3 string
     */
    public void setGlobalModifier3(String modifier3) {
        globalModifier3 = modifier3;
    }

    /**
     * Return the first modifier
     * @return globalModifier1
     */
    public String getGlobalModifier1() {
        return globalModifier1;
    }

    /**
     * Return the second modifier
     * @return globalModifier2
     */
    public String getGlobalModifier2() {
        return globalModifier2;
    }

    /**
     * Return the third modifier
     * @return globalModifier3
     */
    public String getGlobalModifier3() {
        return globalModifier3;
    }

    /**
     * Set the currently selected modifier to the given string
     * @param modifier modifier selected
     */
    public void setModifier(String modifier) {
        currentModifier = modifier;
    }

    /**
     * Return the currently selected modifier
     * @return currentModifier
     */
    public String getModifier() {
        return currentModifier;
    }
}

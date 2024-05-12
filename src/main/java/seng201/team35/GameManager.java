package seng201.team35;

import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameManager {
    private String playerName;
    private int numOfRounds;
    private String gameDifficulty;
    private List<Tower> mainTowerList;
    private List<Tower> reserveTowerList;
    private List<Upgrade> upgradesList;
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
        defaultTowers.addAll(List.of(new Tower("Cobbler", 100, "Cobblestone", 100),
                new Tower("Granny", 100, "Granite", 350),
                new Tower("Mark", 150, "Marble", 500),
                new Tower("Silvia", 50, "Silver", 1000),
                new Tower("Sylvester", 120, "Silver", 2000),
                new Tower("Goldberg", 80, "Gold", 3000)));
        defaultUpgrades.addAll(List.of(new Upgrade(10, 10, "Cobblestone", 100),
                new Upgrade(10, 10, "Granite", 150),
                new Upgrade(10, 10, "Marble", 200),
                new Upgrade(10, 10, "Silver", 350),
                new Upgrade(10, 10, "Gold", 500)));
        launchSetupScreen();
    }

    public void setPlayerName(String name) { this.playerName = name; }
    public String getPlayerName() { return playerName; }

    public void setNumOfRounds(int rounds) {
        this.numOfRounds = rounds;
    }
    public int getNumOfRounds() {
        return numOfRounds;
    }

    public void setGameDifficulty(String difficulty) {
        this.gameDifficulty = difficulty;
    }
    public String getGameDifficulty() {
        return gameDifficulty;
    }

    public void setMainTowerList(List<Tower> towerList) { this.mainTowerList = towerList; }
    public List<Tower> getMainTowerList() { return mainTowerList; }

    public void setReserveTowerList(List<Tower> towerList) { this.reserveTowerList = towerList; }
    public List<Tower> getReserveTowerList() { return reserveTowerList; }

    public void setUpgradesList(List<Upgrade> upgradeList) { this.upgradesList = upgradeList; }
    public List<Upgrade> getUpgradesList() { return upgradesList; }

    public List<Tower> getDefaultTowers() { return defaultTowers; }

    public List<Upgrade> getDefaultUpgrades() { return defaultUpgrades; }

    public void launchSetupScreen() { setupScreenLauncher.accept(this); }
    public void closeSetupScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }

    public void launchMainMenuScreen() {
        mainMenuLauncher.accept(this);
    }
    public void mainMenuToShopScreen() {
        clearScreen.run();
        launchShopScreen();
    }
    public void mainMenuToInventoryScreen() {
        clearScreen.run();
        launchInventoryScreen();
    }
    public void mainMenuToGameScreen() {
        clearScreen.run();
        launchGameScreen();
    }

    public void launchShopScreen() {
        shopLauncher.accept(this);
    }
    public void closeShopScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }

    public void launchInventoryScreen() {
        inventoryLauncher.accept(this);
    }
    public void closeInventoryScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }

    public void launchGameScreen() {
        gameLauncher.accept(this);
    }
    public void gameToMainMenuScreen() {
        clearScreen.run();
        launchMainMenuScreen();
    }
    public void gameToFailMenuScreen() {
        clearScreen.run();
        launchFailMenuScreen();
    }
    public void gameToWinMenuScreen() {
        clearScreen.run();
        launchWinMenuScreen();
    }

    public void launchFailMenuScreen() {
        failMenuLauncher.accept(this);
    }
    public void closeFailMenuScreen() {
        System.exit(0);
    }

    public void launchWinMenuScreen() {
        winMenuLauncher.accept(this);
    }
    public void closeWinMenuScreen() {
        System.exit(0);
    }
}

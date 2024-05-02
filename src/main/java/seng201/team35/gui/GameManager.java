package seng201.team35.gui;

import seng201.team35.models.Game;
import seng201.team35.models.Tower;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameManager {
    private String playerName;
    private List<Tower> towerList;
    private final List<Tower> defaultTowers = new ArrayList<>();
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
                       Runnable clearScreen) {
        this.setupScreenLauncher = setupScreenLauncher;
        //this
        this.clearScreen = clearScreen;
    }

    public void setPlayerName(String name) { this.playerName = name; }

    public String getPlayerName() { return playerName; }

    public void setTowerList(List<Tower> towerList) { this.towerList = towerList; }

    public List<Tower> getTowerList() { return towerList; }

    public List<Tower> getDefaultTowers() { return defaultTowers; }

    public void launchSetupScreen() { setupScreenLauncher.accept(this); }

    //Add more methods for launching other screens

    public void closeSetupScreen() {
        clearScreen.run();
        // launchMainMenuScreen();
    }

    public void close
}

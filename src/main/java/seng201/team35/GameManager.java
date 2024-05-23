package seng201.team35;

import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GameManager {
    private String playerName;
    private int moneyAmount = 0;
    private int totalMoney = 0;
    private int numOfRounds;
    private String gameDifficulty;
    private int lives;
    private int currentRound = 1;
    private int totalCartsDestroyed = 0;
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
    /**
     * Initialise all available towers
     */
    private static final Tower BronzeArcher = new Tower("Bronze Archer", 100,100, "Bronze", 100);
    private static final Tower BronzeDwarf = new Tower("Bronze Dwarf", 75,125, "Bronze", 125);
    private static final Tower BronzeVillager = new Tower("Bronze Villager", 120,150, "Bronze", 150);
    private static final Tower SilverKnight = new Tower("Silver Knight",80,150, "Silver", 150);
    private static final Tower SilverPriest = new Tower("Silver Priest",100,170, "Silver", 170);
    private static final Tower SilverAlchemist = new Tower("Silver Alchemist",150,190, "Silver", 190);
    private static final Tower GoldGiant = new Tower("Gold Giant", 70,200, "Gold", 200);
    private static final Tower GoldGoblin = new Tower("Gold Goblin", 100,240, "Gold", 240);
    private static final Tower GoldPirate = new Tower("Gold Pirate", 40,275, "Gold", 275);
    private static final Tower DiamondMage = new Tower("Diamond Mage",20, 400, "Diamond", 400);
    private static final Tower DiamondNecromancer = new Tower("Diamond Necromancer",20, 425, "Diamond", 425);
    private static final Tower DiamondMinotaur = new Tower("Diamond Minotaur",45, 460, "Diamond", 460);
    private static final Tower EmeraldElf = new Tower("Emerald Elf", 45,575, "Emerald", 575);
    private static final Tower EmeraldPhoenix = new Tower("Emerald Phoenix", 25,625, "Emerald", 625);
    private static final Tower EmeraldPegasus = new Tower("Emerald Pegasus", 50,700, "Emerald", 700);
    private static final Tower RubyDragon = new Tower("Ruby Dragon",25, 700, "Ruby", 700);
    private static final Tower RubyOrcMage = new Tower("Ruby OrcMage",25, 850, "Ruby", 850);
    private static final Tower RubyGolem = new Tower("Ruby Golem",10, 1000, "Ruby", 1000);
    /**
     * Initialise all available upgrades
     */
    private static final Upgrade BronzeUpgrade = new Upgrade(10, 10, "Bronze", 100, "Active");
    private static final Upgrade SilverUpgrade = new Upgrade(10, 10, "Silver", 150, "Active");
    private static final Upgrade GoldUpgrade = new Upgrade(10, 10, "Gold", 200, "Active");
    private static final Upgrade DiamondUpgrade = new Upgrade(10, 10, "Diamond", 350, "Active");
    private static final Upgrade EmeraldUpgrade = new Upgrade(10, 10, "Emerald", 500, "Active");
    private static final Upgrade RubyUpgrade = new Upgrade(10, 10, "Ruby", 700, "Active");
    private Boolean modifiersInitialised = false;
    private String globalModifier1;
    private String globalModifier2;
    private String globalModifier3;
    private Boolean modifierSelected = false;
    public Boolean getModifierSelected() {
        return modifierSelected;
    }
    public void setModifierSelectedTrue() {
        modifierSelected = true;
    }
    public void setModifiersSelectedFalse() {
        modifierSelected = false;
    }

    public boolean getModifiersInitialised() {
        return modifiersInitialised;
    }
    public void setModifiersInitialisedTrue() {
        modifiersInitialised = true;
    }
    public void setModifiersInitialisedFalse() {
        modifiersInitialised = false;
    }
    public void setGlobalModifier1(String modifier1) {
        globalModifier1 = modifier1;
    }

    public void setGlobalModifier2(String modifier2) {
        globalModifier2 = modifier2;
    }

    public void setGlobalModifier3(String modifier3) {
        globalModifier3 = modifier3;
    }

    public String getGlobalModifier1() {
        return globalModifier1;
    }
    public String getGlobalModifier2() {
        return globalModifier2;
    }
    public String getGlobalModifier3() {
        return globalModifier3;
    }

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
        defaultTowers.addAll(List.of(BronzeArcher, BronzeDwarf, BronzeVillager,
                SilverKnight, SilverPriest, SilverAlchemist, GoldGiant, GoldGoblin,
                GoldPirate, DiamondMage, DiamondNecromancer, DiamondMinotaur, EmeraldElf,
                EmeraldPhoenix, EmeraldPegasus, RubyDragon, RubyOrcMage, RubyGolem));
        defaultUpgrades.addAll(List.of(BronzeUpgrade, SilverUpgrade, GoldUpgrade,
                DiamondUpgrade, EmeraldUpgrade, RubyUpgrade));
        launchSetupScreen();
    }
    public void placeTowerAt(Point position, Tower tower) {
        towerPositionMap.put(position, tower);
    }

    public void removeTowerAt(Point position) {
        towerPositionMap.remove(position);
    }

    public Tower getTowerAt(Point position) {
        return towerPositionMap.get(position);
    }
    public Tower getTowerByName(String towerName) {
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
    public void setPlayerName(String name) { this.playerName = name; }
    public String getPlayerName() { return playerName; }
    public void setModifier(String modifier) {
        currentModifier = modifier;
    }
    public String getModifier() {
        return currentModifier;
    }
    public void setNumOfRounds(int rounds) {
        this.numOfRounds = rounds;
    }
    public int getNumOfRounds() {
        return numOfRounds;
    }
    public int getTotalMoney() {
        return totalMoney;
    }
    public void incrementTotalMoney(int money) {
        this.totalMoney += money;
    }

    public int getTotalCartsDestroyed() {
        return totalCartsDestroyed;
    }
    public void incrementTotalCartsDestroyed() {
        this.totalCartsDestroyed += 1;
    }

    public void setGameDifficulty(String difficulty) {
        this.gameDifficulty = difficulty;
    }
    public String getGameDifficulty() {
        return gameDifficulty;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    public int getLives() {
        return lives;
    }
    public void changeLives(int livesLost) {
        lives -= livesLost;
    }
    public void changeCurrentRound() {
        currentRound += 1;
    }
    public int getCurrentRound() {
        return currentRound;
    }

    public void setMainTowerList(List<Tower> towerList) { this.mainTowerList = towerList; }
    public List<Tower> getMainTowerList() {
        return mainTowerList;
    }
    public void addMainTower(Tower tower) {
        mainTowerList.add(tower);
    }
    public void removeMainTower(Tower tower) {
        mainTowerList.remove(tower);
    }
    public void setReserveTowerList(List<Tower> towerList) { this.reserveTowerList = towerList; }
    public List<Tower> getReserveTowerList() { return reserveTowerList; }
    public void addReserveTower(Tower tower) {
        reserveTowerList.add(tower);
    }
    public void removeReserveTower(Tower tower) {
        reserveTowerList.remove(tower);
    }

    public void setUpgradesList(List<Upgrade> upgradeList) { this.upgradesList = upgradeList; }
    public List<Upgrade> getUpgradesList() { return upgradesList; }
    public void addUpgrade(Upgrade upgrade) {
        upgradesList.add(upgrade);
    }
    public void removeUpgrade(Upgrade upgrade) {
        upgradesList.remove(upgrade);
    }

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
    public int getMoneyAmount() { return moneyAmount;}
    public void changeMoneyAmount(int changeAmount) {
        moneyAmount += changeAmount;
    }




    public Tower getTowerClass(String towerName) {
        for (Tower tower : defaultTowers) {
            if (towerName == tower.getName()) {
                return tower;
            }
        }
        System.out.println("potential error in getting tower class from name");
        return null;
    }
    public Upgrade getUpgradeClass(String upgradeName) {
        for (Upgrade upgrade : defaultUpgrades) {
            if (upgradeName == upgrade.getResourceType()) {
                return upgrade;
            }
        }
        System.out.println("potential error in getting upgrade class from type");
        return null;
    }
}

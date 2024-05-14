package seng201.team35;

import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GameManager {
    private String playerName;
    private int moneyAmount = 0;
    private int numOfRounds;
    private String gameDifficulty;
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
    private Tower BronzeArcher = new Tower("Bronze Archer", 100,75, "Bronze", 100);
    private Tower BronzeDwarf = new Tower("Bronze Dwarf", 75,100, "Bronze", 100);
    private Tower BronzeVillager = new Tower("Bronze Villager", 120,100, "Bronze", 100);
    private Tower SilverKnight = new Tower("Silver Knight",80,50, "Silver", 350);
    private Tower SilverPriest = new Tower("Silver Priest",100,120, "Silver", 350);
    private Tower SilverAlchemist = new Tower("Silver Alchemist",150,130, "Silver", 350);
    private Tower GoldGiant = new Tower("Gold Giant", 70,80, "Gold", 500);
    private Tower GoldGoblin = new Tower("Gold Goblin", 100,50, "Gold", 500);
    private Tower GoldWolf = new Tower("Gold Wolf", 40,70, "Gold", 500);
    private Tower DiamondMage = new Tower("Diamond Mage",20, 50, "Diamond", 1000);
    private Tower DiamondNecromancer = new Tower("Diamond Necromancer",80, 70, "Diamond", 1000);
    private Tower DiamondMinotaur = new Tower("Diamond Minotaur",100, 50, "Diamond", 1000);
    private Tower EmeraldElf = new Tower("Emerald Elf", 100,80, "Emerald", 2000);
    private Tower EmeraldPheonix = new Tower("Emerald Pheonix", 120,60, "Emerald", 2000);
    private Tower EmeraldPegasus = new Tower("Emerald Pegasus", 100,120, "Emerald", 2000);
    private Tower RubyDragon = new Tower("Ruby Dragon",150, 50, "Ruby", 3000);
    private Tower RubyWyvern = new Tower("Ruby Wyvern",90, 200, "Ruby", 3000);
    private Tower RubyGolem = new Tower("Ruby Golem",100, 250, "Ruby", 3000);

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
                GoldWolf, DiamondMage, DiamondNecromancer, DiamondMinotaur, EmeraldElf,
                EmeraldPheonix, EmeraldPegasus, RubyDragon, RubyWyvern, RubyGolem));
        defaultUpgrades.addAll(List.of(new Upgrade(10, 10, "Bronze", 100, "Active"),
                new Upgrade(10, 10, "Silver", 150, "Active"),
                new Upgrade(10, 10, "Gold", 200, "Active"),
                new Upgrade(10, 10, "Diamond", 350, "Active"),
                new Upgrade(10, 10, "Emerald", 500, "Active"),
                new Upgrade(10, 10, "Ruby", 700, "Active")));
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
        System.out.println("error in getting tower class from name");
        return null;
    }
    public Upgrade getUpgradeClass(String upgradeName) {
        for (Upgrade upgrade : defaultUpgrades) {
            if (upgradeName == upgrade.getResourceType()) {
                return upgrade;
            }
        }
        System.out.println("error in getting upgrade class from type");
        return null;
    }
}

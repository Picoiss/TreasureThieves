package seng201.team35;

import seng201.team35.models.Tower;
import seng201.team35.models.Upgrade;

import java.awt.*;
import java.util.*;
import java.util.List;
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
        Tower bronzeArcher = new Tower("Bronze Archer", 3, 100, "Bronze", 100);
        Tower bronzeDwarf = new Tower("Bronze Dwarf", 2, 125, "Bronze", 125);
        Tower bronzeVillager = new Tower("Bronze Villager", 1, 150, "Bronze", 150);
        Tower silverKnight = new Tower("Silver Knight", 3, 150, "Silver", 150);
        Tower silverPriest = new Tower("Silver Priest", 3, 170, "Silver", 170);
        Tower silverAlchemist = new Tower("Silver Alchemist", 2, 190, "Silver", 190);
        Tower goldGiant = new Tower("Gold Giant", 3, 200, "Gold", 200);
        Tower goldGoblin = new Tower("Gold Goblin", 2, 240, "Gold", 240);
        Tower goldPirate = new Tower("Gold Pirate", 1, 275, "Gold", 275);
        Tower diamondMage = new Tower("Diamond Mage", 3, 400, "Diamond", 400);
        Tower diamondNecromancer = new Tower("Diamond Necromancer", 2, 425, "Diamond", 425);
        Tower diamondMinotaur = new Tower("Diamond Minotaur", 1, 460, "Diamond", 460);
        Tower emeraldElf = new Tower("Emerald Elf", 3, 575, "Emerald", 575);
        Tower emeraldPhoenix = new Tower("Emerald Phoenix", 2, 625, "Emerald", 625);
        Tower emeraldPegasus = new Tower("Emerald Pegasus", 1, 700, "Emerald", 700);
        Tower rubyDragon = new Tower("Ruby Dragon", 2, 700, "Ruby", 700);
        Tower rubyOrcMage = new Tower("Ruby OrcMage", 1, 850, "Ruby", 850);
        Tower rubyGolem = new Tower("Ruby Golem", 1, 1000, "Ruby", 1000);
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
    public void placeTowerAt(Point position, Tower tower) {
        towerPositionMap.put(position, tower);
    }

    public Tower getTowerAt(Point position) {
        return towerPositionMap.get(position);
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
    public List<Tower> getReserveTowerList() { return reserveTowerList; }
    public void addReserveTower(Tower tower) {
        reserveTowerList.add(tower);
    }
    public void removeReserveTower(Tower tower) {
        reserveTowerList.remove(tower);
    }
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

    public void launchWinMenuScreen() {
        winMenuLauncher.accept(this);
    }
    public int getMoneyAmount() { return moneyAmount;}
    public void changeMoneyAmount(int changeAmount) {
        moneyAmount += changeAmount;
    }

    public void setTowersUsedInPreviousRound(List<Tower> towers) {
        towersUsedInPreviousRound = towers;
    }

    public List<Tower> getTowersUsedInPreviousRound() {
        return towersUsedInPreviousRound;
    }

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
    public Upgrade getUpgradeClass(String upgradeName) {
        for (Upgrade upgrade : defaultUpgrades) {
            if (Objects.equals(upgradeName, upgrade.getResourceType())) {
                return upgrade;
            }
        }
        System.out.println("potential error in getting upgrade class from type");
        return null;
    }
}

package seng201.team35.services;
public class ProjectileSwitch {
    public static String getProjectileSprite(String towerName) {
        switch (towerName) {
            case "Bronze Archer":
                return"Arrow Long.png";
            case "Bronze Dwarf", "Gold Giant":
                return "Spear.png";
            case "Silver Knight", "Diamond Minotaur":
                return "Sword.png";
            case "Silver Priest", "Gold Goblin", "Diamond Necromancer", "Emerald Pegasus", "Ruby Dragon", "Emerald Elf", "Diamond Mage", "Emerald Phoenix", "Ruby OrcMage", "Silver Alchemist":
                return "Fireball.png";
            case "Gold Pirate", "Ruby Golem":
                return "Bullet.png";
            default:
                return "Arrow Long.png";
        }
    }
}
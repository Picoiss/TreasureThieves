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
            case "Silver Priest", "Gold Goblin", "Diamond Necromancer", "Emerald Pegasus", "Ruby Dragon", "Emerald Elf":
                return "Fireball.png";
            case "Silver Alchemist":
                return "Fireball Purple.png";
            case "Gold Pirate", "Ruby Golem":
                return "Bullet.png";
            case "Diamond Mage":
                return "Fireball Blue.png";
            case "Emerald Phoenix":
            case "Ruby OrcMage":
                return "Fireball RedGreen.png";
            default:
                return "Arrow Long.png";
        }
    }
}
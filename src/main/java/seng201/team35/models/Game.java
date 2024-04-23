package seng201.team35.models;
import java.util.ArrayList;

public class Game {
    private int round;
    private int lives;
    private String result;
    private Boolean notFinished = true;

    private ArrayList<Tower> Towers = new ArrayList<Tower>();
    private ArrayList<Cart> Carts = new ArrayList<Cart>();
    private ArrayList<Upgrade> Upgrades = new ArrayList<Upgrade>();

    public Game(ArrayList<Tower> Towers, ArrayList<Cart> Carts, ArrayList<Upgrade> Upgrades, int lives) {
        this.Towers = Towers;
        this.Carts = Carts;
        this.Upgrades = Upgrades;
        this.lives = lives;
    }
    public void runGame(){

        //Missing increaseTime function

        while(notFinished) {
            for (Cart cart : Carts) {
                cart.increasePosition();
            }
            for (Tower tower : Towers) {
                for(Cart cart : Carts) {
                    if(cart.getRecourseType() == tower.getResourceType()) {
                        cart.fill(tower.getResourceAmount());
                        tower.depleteResourceAmount(tower.getResourceAmount());
                    }
                }
            }
            if (lives <= 0) {
                notFinished = false;
                result = "Lost";
                break;
            }
            int fillAmount = 0;
            for(Cart cart : Carts) {
                if(cart.isFill() == true) {
                    fillAmount += 1;
                }
            }
            if (fillAmount == Carts.size()) {
                result = "Won";
                notFinished = false;
                break;
            }

        }
    }
}

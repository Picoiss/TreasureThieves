package seng201.team35.services;

import seng201.team35.models.Cart;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CartRound {

    public static class CartSpawn {
        public Cart cart;
        public long spawnTime;

        public CartSpawn(Cart cart, long spawnTime) {
            this.cart = cart;
            this.spawnTime = spawnTime;
        }
    }

    public static List<CartSpawn> getCartsForRound(int roundNumber, int cartNumDecrease, int cartNumIncrease) {
        List<CartSpawn> carts = new ArrayList<>();
        int lastCartDelay = 0;
        switch (roundNumber) {
            case 1:
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(6).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(9).toNanos()));
                lastCartDelay = 9;
                break;
            case 2:
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(7).toNanos()));
                lastCartDelay = 7;
                break;
            case 3:
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(125 , "Silver", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Gold", 1), Duration.ofSeconds(6).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Gold", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(11).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(13).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(14).toNanos()));
                lastCartDelay = 14;
                break;
            case 4:
                carts.add(new CartSpawn(new Cart(125, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Bronze", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(150 , "Silver", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(150, "Gold", 1), Duration.ofSeconds(6).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Gold", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Bronze", 1), Duration.ofSeconds(11).toNanos()));
                carts.add(new CartSpawn(new Cart(75, "Silver", 1), Duration.ofSeconds(12).toNanos()));
                carts.add(new CartSpawn(new Cart(75, "Silver", 1), Duration.ofSeconds(14).toNanos()));
                lastCartDelay = 14;
                break;
                // include the lastCartDelay statement set to the time of the last cart for the round
            case 5:
                carts.add(new CartSpawn(new Cart(125, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 2), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(150 , "Gold", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(150, "Gold", 1), Duration.ofSeconds(6).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Gold", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Diamond", 1), Duration.ofSeconds(10).toNanos()));
                lastCartDelay = 10;
                break;
            case 6:
                carts.add(new CartSpawn(new Cart(125, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(75, "Gold", 1), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Gold", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Diamond", 1.25), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(250 , "Diamond", 1.5), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(250, "Diamond", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(250, "Diamond", 1), Duration.ofSeconds(10).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Silver", 1), Duration.ofSeconds(12).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Silver", 1), Duration.ofSeconds(13).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Silver", 1), Duration.ofSeconds(14).toNanos()));
                lastCartDelay = 14;
                break;
            case 7:
                carts.add(new CartSpawn(new Cart(175, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Gold", 1), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Gold", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Diamond", 2), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(225 , "Diamond", 2), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Diamond", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(300, "Diamond", 3), Duration.ofSeconds(10).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Gold", 1), Duration.ofSeconds(11).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Silver", 1), Duration.ofSeconds(12).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Silver", 1), Duration.ofSeconds(13).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 1), Duration.ofSeconds(14).toNanos()));
                lastCartDelay = 14;
                break;
            case 8:
                carts.add(new CartSpawn(new Cart(800, "Emerald", 3.5), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Bronze", 1), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Silver", 2), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(225 , "Silver", 2), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Gold", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(300, "Gold", 3), Duration.ofSeconds(10).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 1), Duration.ofSeconds(11).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 1), Duration.ofSeconds(12).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 1), Duration.ofSeconds(13).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 1), Duration.ofSeconds(14).toNanos()));
                lastCartDelay = 14;
                break;
            case 9:
                carts.add(new CartSpawn(new Cart(250, "Diamond", 0.5), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Diamond", 0.5), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Diamond", 0.5), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Diamond", 0.5), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(225 , "Diamond", 0.5), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Diamond", 0.5), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(300, "Diamond", 0.5), Duration.ofSeconds(10).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 0.5), Duration.ofSeconds(10).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 0.5), Duration.ofSeconds(12).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 0.5), Duration.ofSeconds(13).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Emerald", 0.5), Duration.ofSeconds(14).toNanos()));
                lastCartDelay = 14;
                break;
            case 10:
                carts.add(new CartSpawn(new Cart(175, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(200, "Silver", 1), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Gold", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Silver", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(225 , "Diamond", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(225, "Emerald", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(300, "Emerald", 2), Duration.ofSeconds(10).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Emerald", 1), Duration.ofSeconds(11).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 1), Duration.ofSeconds(12).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Diamond", 1), Duration.ofSeconds(13).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Ruby", 2), Duration.ofSeconds(14).toNanos()));
                lastCartDelay = 14;
                break;
            case 11:
                carts.add(new CartSpawn(new Cart(1250, "Ruby", 5), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Gold", 2), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Gold", 2), Duration.ofSeconds(9).toNanos()));
                carts.add(new CartSpawn(new Cart(215, "Gold", 2), Duration.ofSeconds(11).toNanos()));
                lastCartDelay = 11;
                break;
        }
        Random rand = new Random();
        if (cartNumDecrease != 0) {
            for (int i = 0; i < cartNumDecrease; i++) {
                carts.remove(rand.nextInt(carts.size()));
            }
        }
        if (cartNumIncrease != 0) {
            int delay = lastCartDelay;
            for (int i = 0; i < cartNumIncrease; i++) {
                delay++;
                if (roundNumber <= 3) {
                    carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(delay).toNanos()));
                }
                else if (roundNumber <= 6) {
                    carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(delay).toNanos()));
                }
                else if (roundNumber <= 9) {
                    carts.add(new CartSpawn(new Cart(175, "Gold", 1), Duration.ofSeconds(delay).toNanos()));
                }
                else if (roundNumber <= 11) {
                    carts.add(new CartSpawn(new Cart(225, "Diamond", 1), Duration.ofSeconds(delay).toNanos()));
                }
                else if (roundNumber <= 13) {
                    carts.add(new CartSpawn(new Cart(350, "Emerald", 1), Duration.ofSeconds(delay).toNanos()));
                }
                else {
                    carts.add(new CartSpawn(new Cart(525, "Ruby", 1), Duration.ofSeconds(delay).toNanos()));
                }
            }
        }
        return carts;
    }
}

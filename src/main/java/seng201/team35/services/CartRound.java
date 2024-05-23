package seng201.team35.services;

import seng201.team35.GameManager;
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

        public Cart getCart() {
            return cart;
        }

        public long getSpawnTime() {
            return spawnTime;
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
                carts.add(new CartSpawn(new Cart(125 , "Silver", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Gold", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(175, "Gold", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(7).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(9).toNanos()));
                lastCartDelay = 9;
                break;
            case 4:
                // include the lastCartDelay statement set to the time of the last cart for the round
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
                delay++;
            }
        }
        return carts;
    }
}

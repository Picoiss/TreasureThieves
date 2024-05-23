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

        public Cart getCart() {
            return cart;
        }
    }

    public static List<CartSpawn> getCartsForRound(int roundNumber, int cartNumDecrease, int cartNumIncrease) {
        List<CartSpawn> carts = new ArrayList<>();
        switch (roundNumber) {
            case 1:
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(6).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(8).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(9).toNanos()));
                break;
            case 2:
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(2).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(4).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(5).toNanos()));
                carts.add(new CartSpawn(new Cart(125, "Silver", 1), Duration.ofSeconds(7).toNanos()));
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
        }
        Random cart = new Random();
        if (cartNumDecrease != 0) {
            for (int i = 0; i < cartNumDecrease; i++) {
                carts.remove(cart.nextInt(carts.size()));
            }
        }
        if (cartNumIncrease == 1) {
            carts.add(new CartSpawn(carts.get(cart.nextInt(carts.size())).getCart(), Duration.ofSeconds(10).toNanos()));
        }
        if (cartNumIncrease == 2){
            carts.add(new CartSpawn(carts.get(cart.nextInt(carts.size())).getCart(), Duration.ofSeconds(10).toNanos()));
            carts.add(new CartSpawn(carts.get(cart.nextInt(carts.size())).getCart(), Duration.ofSeconds(11).toNanos()));
        }
        return carts;
    }
}

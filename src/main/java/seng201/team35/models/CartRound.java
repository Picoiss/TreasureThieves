package seng201.team35.models;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartRound {
    public static class CartSpawn {
        public Cart cart;
        public long spawnTime;

        public CartSpawn(Cart cart, long spawnTime) {
            this.cart = cart;
            this.spawnTime = spawnTime;
        }
    }

    public static List<CartSpawn> getCartsForRound(int roundNumber) {
        List<CartSpawn> carts = new ArrayList<>();
        switch (roundNumber) {
            case 1:
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(5).toNanos()));
                break;
            case 2:
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(1).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(3).toNanos()));
                carts.add(new CartSpawn(new Cart(100, "Bronze", 1), Duration.ofSeconds(5).toNanos()));
                break;
        }
        return carts;
    }
}

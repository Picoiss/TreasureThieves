package seng201.team35.models;

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
                carts.add(new CartSpawn(new Cart(100, "Bronze Cart", 1), 0));
                carts.add(new CartSpawn(new Cart(100, "Bronze Cart", 1), 1_000_000_000));
                carts.add(new CartSpawn(new Cart(100, "Bronze Cart", 1), 2_000_000_000));
                break;
        }
        return carts;
    }
}

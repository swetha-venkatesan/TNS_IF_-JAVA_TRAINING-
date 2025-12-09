package model;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> items;

    public ShoppingCart() {
        items = new HashMap<>();
    }

    public void addProduct(Product p, int qty) {
        items.put(p, items.getOrDefault(p, 0) + qty);
    }

    public Map<Product, Integer> getItems() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public void removeProduct(Product p) {
        items.remove(p);
    }
}

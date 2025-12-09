package service;

import model.Product;
import java.util.*;

public class ProductService {

    private Map<Integer, Product> products = new LinkedHashMap<>();

    public void addProduct(Product product) {
        products.put(product.getProductId(), product);
    }

    public void removeProduct(int productId) {
        products.remove(productId);
    }

    public Product getProduct(int id) {
        return products.get(id);
    }

    public Collection<Product> getAllProducts() {
        return products.values();
    }
}

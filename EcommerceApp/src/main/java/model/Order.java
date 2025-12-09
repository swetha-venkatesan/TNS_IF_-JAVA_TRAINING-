package model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private Customer customer;
    private List<ProductQuantityPair> items;
    private String status;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new ArrayList<>();
        this.status = "Pending";
    }

    public void addItem(Product product, int qty) {
        items.add(new ProductQuantityPair(product, qty));
    }

    public int getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public List<ProductQuantityPair> getItems() { return items; }
    public Customer getCustomer() { return customer; }

    public void setStatus(String status) { this.status = status; }
}

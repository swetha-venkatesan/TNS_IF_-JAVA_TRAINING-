package service;

import model.*;
import java.util.List;

public class OrderService {

    private List<Order> orders;
    private int orderIdCounter = 1;

    public OrderService(List<Order> sharedOrders) {
        this.orders = sharedOrders;
    }

    public Order placeOrder(Customer customer) {
        Order order = new Order(orderIdCounter++, customer);

        for (var entry : customer.getShoppingCart().getItems().entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();

            p.reduceStock(qty);
            order.addItem(p, qty);
        }

        customer.getOrders().add(order);
        customer.getShoppingCart().clear();
        orders.add(order);

        return order;
    }
}

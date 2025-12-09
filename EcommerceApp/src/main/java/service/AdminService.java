package service;

import model.Admin;
import model.Order;
import java.util.*;

public class AdminService {

    private List<Admin> admins = new ArrayList<>();
    private List<Order> orders;

    public AdminService(List<Order> sharedOrders) {
        this.orders = sharedOrders;
    }

    public void createAdmin(Admin admin) {
        admins.add(admin);
    }

    public List<Admin> getAdmins() {
        return admins;
    }

    public void updateOrderStatus(int orderId, String status) {
        for (Order o : orders) {
            if (o.getOrderId() == orderId) {
                o.setStatus(status);
                break;
            }
        }
    }

    public List<Order> getAllOrders() {
        return orders;
    }
}

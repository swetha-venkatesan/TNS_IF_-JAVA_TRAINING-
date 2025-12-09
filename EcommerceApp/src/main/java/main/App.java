package main;

import model.*;
import service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Shared storage
        List<Order> sharedOrders = new ArrayList<>();

        ProductService productService = new ProductService();
        CustomerService customerService = new CustomerService();
        OrderService orderService = new OrderService(sharedOrders);
        AdminService adminService = new AdminService(sharedOrders);

        // Pre-seed (optional) - none added here, will follow expected interaction

        boolean running = true;
        while (running) {
            System.out.println("1. Admin Menu");
            System.out.println("2. Customer Menu");
            System.out.println("3. Exit");
            System.out.println("  ");
            System.out.print("Choose an option: ");
            System.out.println("  ");

            int mainChoice = Integer.parseInt(sc.nextLine().trim());

            switch (mainChoice) {
                case 1:
                    adminMenu(sc, productService, adminService);
                    break;
                case 2:
                    customerMenu(sc, productService, customerService, orderService);
                    break;
                case 3:
                    System.out.println("Exiting..."); running = false; break;
                default:
                    System.out.println("Invalid option"); break;
            }
        }

        sc.close();
    }
// vino
    private static void adminMenu(Scanner sc, ProductService productService, AdminService adminService) {
        boolean inAdmin = true;
        while (inAdmin) {
        	System.out.println(" ");
            System.out.println("Admin Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. View Products");
            System.out.println("4. Create Admin");
            System.out.println("5. View Admins");
            System.out.println("6. Update Order Status");
            System.out.println("7. View Orders");
            System.out.println("8. Return");
            System.out.println("  ");
            System.out.print("Choose an option: ");
            System.out.println("  ");

            int choice = Integer.parseInt(sc.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    int pid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter Product Name: ");
                    String pname = sc.nextLine().trim();
                    System.out.print("Enter Product Price: ");
                    double price = Double.parseDouble(sc.nextLine().trim());
                    System.out.print("Enter Stock Quantity: ");
                    int stock = Integer.parseInt(sc.nextLine().trim());
                    Product p = new Product(pid, pname, price, stock);
                    productService.addProduct(p);
                    System.out.println("Product added successfully!");
                    break;
                case 2:
                    System.out.print("Enter Product ID to remove: ");
                    int rid = Integer.parseInt(sc.nextLine().trim());
                    productService.removeProduct(rid);
                    System.out.println("Product removed (if existed).");
                    break;
                case 3:
                    System.out.println("Products:");
                    for (Product prod : productService.getAllProducts()) {
                        System.out.println(prod.toString());
                    }
                    break;
                case 4:
                    System.out.print("Enter Admin ID: ");
                    int aid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter Username: ");
                    String aname = sc.nextLine().trim();
                    System.out.print("Enter Email: ");
                    String aemail = sc.nextLine().trim();
                    Admin admin = new Admin(aid, aname, aemail);
                    adminService.createAdmin(admin);
                    System.out.println("Admin created successfully!"); 
                    break;
                case 5:
                    System.out.println("Admins:");
                    for (Admin aa : adminService.getAdmins()) {
                        System.out.println(aa.toString());
                    }
                    break;
                case 6:
                    System.out.print("Enter Order ID: ");
                    int oid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter new status (Completed/Delivered/Cancelled): ");
                    String status = sc.nextLine().trim();
                    adminService.updateOrderStatus(oid, status);
                    System.out.println("Order status updated (if order existed).");
                    break;
                case 7:
                    System.out.println("Orders:");
                    for (Order o : adminService.getAllOrders()) {
                        System.out.println("Order ID: " + o.getOrderId() + ", Customer: " + o.getCustomer().getUsername() + ", Status: " + o.getStatus());
                        for (ProductQuantityPair pq : o.getItems()) {
                            System.out.println("  Product: " + pq.getProduct().getName() + ", Quantity: " + pq.getQuantity());
                        }
                    }
                    break;
                case 8:
                    System.out.println("Exiting Admin..."); inAdmin = false; break;
                default:
                    System.out.println("Invalid option"); break;
            }
        }
    }

    private static void customerMenu(Scanner sc, ProductService productService, CustomerService customerService, OrderService orderService) {
        boolean inCustomer = true;
        while (inCustomer) {
            System.out.println("Customer Menu:");
            System.out.println("1. Create Customer");
            System.out.println("2. View Customers");
            System.out.println("3. Place Order");
            System.out.println("4. View Orders");
            System.out.println("5. View Products");
            System.out.println("6. Return");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(sc.nextLine().trim());
// vino
            switch (choice) {
                case 1:
                    System.out.print("Enter User ID: ");
                    int uid = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter Username: ");
                    String uname = sc.nextLine().trim();
                    System.out.print("Enter Email: ");
                    String uemail = sc.nextLine().trim();
                    System.out.print("Enter Address: ");
                    String addr = sc.nextLine().trim();
                    Customer customer = new Customer(uid, uname, uemail, addr);
                    customerService.createCustomer(customer);
                    System.out.println("Customer created successfully!"); 
                    break;
//                    vino
                case 2:
                    System.out.println("Customers:");
                    for (Customer c : customerService.getCustomers()) {
                        System.out.println(c.toString());
                    }
                    break;
                case 3:
                    System.out.print("Enter Customer ID: ");
                    int cid = Integer.parseInt(sc.nextLine().trim());
                    Customer cust = customerService.getCustomer(cid);
                    if (cust == null) {
                        System.out.println("Customer not found!"); break;
                    }
                    while (true) {
                        System.out.print("Enter Product ID to add to order (or -1 to complete): ");
                        int pid = Integer.parseInt(sc.nextLine().trim());
                        if (pid == -1) break;
                        Product prod = productService.getProduct(pid);
                        if (prod == null) {
                            System.out.println("Product not found!"); continue;
                        }
                        System.out.print("Enter quantity: ");
                        int q = Integer.parseInt(sc.nextLine().trim());
                        cust.getShoppingCart().addProduct(prod, q);
                    }
                    orderService.placeOrder(cust);
                    System.out.println("Order placed successfully!"); 
                    break;
                case 4:
                    System.out.print("Enter Customer ID: ");
                    int cuid = Integer.parseInt(sc.nextLine().trim());
                    Customer cst = customerService.getCustomer(cuid);
                    if (cst == null) {
                        System.out.println("Customer not found!"); break;
                    }
                    System.out.println("Orders:");
                    for (Order o : cst.getOrders()) {
                        System.out.println("Order ID: " + o.getOrderId() + ", Status: " + o.getStatus());
                        for (ProductQuantityPair pq : o.getItems()) {
                            System.out.println("  Product: " + pq.getProduct().getName() + ", Quantity: " + pq.getQuantity());
                        }
                    }
                    break;
                case 5:
                    System.out.println("Products:");
                    for (Product prod : productService.getAllProducts()) {
                        System.out.println(prod.toString());
                    }
                    break;
                case 6:
                    System.out.println("Exiting Customer Menu..."); inCustomer = false; break;
                default:
                    System.out.println("Invalid option"); break;
            }
        }
    }
}

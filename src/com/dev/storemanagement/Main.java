package com.dev.storemanagement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Model.Customer;
import Model.CustomerDAO;
import Model.Order;
import Model.OrderDAO;
import Model.OrderItem;
import Model.Product;
import Model.ProductDAO;

public class Main {

    private static double totalAmount;

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductDAO productDAO = new ProductDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        OrderDAO orderDAO = new OrderDAO();

        while (true) {
            System.out.println("Consumer Electronics Store Management System");
            System.out.println("1. Product Management");
            System.out.println("2. Customer Management");
            System.out.println("3. Order Management");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    productManagement(scanner, productDAO);
                    break;
                case 2:
                    customerManagement(scanner, customerDAO);
                    break;
                case 3:
                    orderManagement(scanner, orderDAO);
                    break;
                case 4:
                    System.out.println("Exiting the system. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void productManagement(Scanner scanner, ProductDAO productDAO) {
        System.out.println("Product Management");
        System.out.println("1. Add a new product");
        System.out.println("2. View product details");
        System.out.println("3. Update product information");
        System.out.println("4. Delete a product");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                addProduct(scanner, productDAO);
                break;
            case 2:
                viewProduct(scanner, productDAO);
                break;
            case 3:
                updateProduct(scanner, productDAO);
                break;
            case 4:
                deleteProduct(scanner, productDAO);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addProduct(Scanner scanner, ProductDAO productDAO) {
        System.out.print("Enter product name: ");
        scanner.nextLine(); // Consume newline left-over
        String name = scanner.nextLine();
        System.out.print("Enter product description: ");
        String description = scanner.nextLine();
        System.out.print("Enter product price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter stock quantity: ");
        int stockQuantity = scanner.nextInt();

        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);

        productDAO.addProduct(product);
        System.out.println("Product added successfully.");
    }

    private static void viewProduct(Scanner scanner, ProductDAO productDAO) {
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        Product product = productDAO.getProduct(productId);

        if (product != null) {
            System.out.println("Product ID: " + product.getProductId());
            System.out.println("Name: " + product.getName());
            System.out.println("Description: " + product.getDescription());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Stock Quantity: " + product.getStockQuantity());
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void updateProduct(Scanner scanner, ProductDAO productDAO) {
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        Product product = productDAO.getProduct(productId);

        if (product != null) {
            System.out.print("Enter new product name: ");
            scanner.nextLine(); 
            String name = scanner.nextLine();
            System.out.print("Enter new product description: ");
            String description = scanner.nextLine();
            System.out.print("Enter new product price: ");
            double price = scanner.nextDouble();
            System.out.print("Enter new stock quantity: ");
            int stockQuantity = scanner.nextInt();

            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);

            productDAO.updateProduct(product);
            System.out.println("Product updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private static void deleteProduct(Scanner scanner, ProductDAO productDAO) {
        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();
        productDAO.deleteProduct(productId);
        System.out.println("Product deleted successfully.");
    }

    private static void customerManagement(Scanner scanner, CustomerDAO customerDAO) {
        System.out.println("Customer Management");
        System.out.println("1. Add a new customer");
        System.out.println("2. View customer details");
        System.out.println("3. Update customer information");
        System.out.println("4. Delete a customer");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                addCustomer(scanner, customerDAO);
                break;
            case 2:
                viewCustomer(scanner, customerDAO);
                break;
            case 3:
                updateCustomer(scanner, customerDAO);
                break;
            case 4:
                deleteCustomer(scanner, customerDAO);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void addCustomer(Scanner scanner, CustomerDAO customerDAO) {
        System.out.print("Enter customer name: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter customer email: ");
        String email = scanner.nextLine();
        System.out.print("Enter customer phone number: ");
        String phoneNumber = scanner.nextLine();
        System.out.print("Enter customer address: ");
        String address = scanner.nextLine();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);

        customerDAO.addCustomer(customer);
        System.out.println("Customer added successfully.");
    }

    private static void viewCustomer(Scanner scanner, CustomerDAO customerDAO) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        Customer customer = customerDAO.getCustomer(customerId);

        if (customer != null) {
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Name: " + customer.getName());
            System.out.println("Email: " + customer.getEmail());
            System.out.println("Phone Number: " + customer.getPhoneNumber());
            System.out.println("Address: " + customer.getAddress());
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void updateCustomer(Scanner scanner, CustomerDAO customerDAO) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        Customer customer = customerDAO.getCustomer(customerId);

        if (customer != null) {
            System.out.print("Enter new customer name: ");
            scanner.nextLine(); 
            String name = scanner.nextLine();
            System.out.print("Enter new customer email: ");
            String email = scanner.nextLine();
            System.out.print("Enter new customer phone number: ");
            String phoneNumber = scanner.nextLine();
            System.out.print("Enter new customer address: ");
            String address = scanner.nextLine();

            customer.setName(name);
            customer.setEmail(email);
            customer.setPhoneNumber(phoneNumber);
            customer.setAddress(address);

            customerDAO.updateCustomer(customer);
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void deleteCustomer(Scanner scanner, CustomerDAO customerDAO) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        customerDAO.deleteCustomer(customerId);
        System.out.println("Customer deleted successfully.");
    }

    private static void orderManagement(Scanner scanner, OrderDAO orderDAO) {
        System.out.println("Order Management");
        System.out.println("1. Place a new order");
        System.out.println("2. View order details");
        System.out.println("3. Update order information");
        System.out.println("4. Cancel an order");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                placeOrder(scanner, orderDAO);
                break;
            case 2:
                viewOrder(scanner, orderDAO);
                break;
            case 3:
                updateOrder(scanner, orderDAO);
                break;
            case 4:
                cancelOrder(scanner, orderDAO);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void placeOrder(Scanner scanner, OrderDAO orderDAO) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        System.out.print("Enter number of items: ");
        int itemCount = scanner.nextInt();

        List<OrderItem> orderItems = new ArrayList<>();
 
        for (int i = 0; i < itemCount; i++) {
            System.out.print("Enter product ID: ");
            int productId = scanner.nextInt();
            System.out.print("Enter quantity: ");
            int quantity = scanner.nextInt();
            System.out.print("Enter price: ");
            double price = scanner.nextDouble();

            OrderItem item = new OrderItem();
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setPrice(price);
            orderItems.add(item);

            
        }

        Order order = new Order();
        order.setCustomerId(customerId);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus("pending");
        order.setOrderItems(orderItems);

        orderDAO.placeOrder(order);
        System.out.println("Order placed successfully.");
    }

    private static void viewOrder(Scanner scanner, OrderDAO orderDAO) {
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        Order order = orderDAO.getOrder(orderId);

        if (order != null) {
            System.out.println("Order ID: " + order.getOrderId());
            System.out.println("Customer ID: " + order.getCustomerId());
            System.out.println("Order Date: " + order.getOrderDate());
            System.out.println("Total Amount: " + order.getTotalAmount());
            System.out.println("Status: " + order.getStatus());
            System.out.println("Order Items:");
            for (OrderItem item : order.getOrderItems()) {
                System.out.println("  - Product ID: " + item.getProductId());
                System.out.println("    Quantity: " + item.getQuantity());
                System.out.println("    Price: " + item.getPrice());
            }
        } else {
            System.out.println("Order not found.");
        }
    }

    private static void updateOrder(Scanner scanner, OrderDAO orderDAO) {
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        Order order = orderDAO.getOrder(orderId);

        if (order != null) {
            System.out.print("Enter new customer ID: ");
            int customerId = scanner.nextInt();
            System.out.print("Enter new order date (yyyy-MM-ddTHH:mm:ss): ");
            scanner.nextLine(); 
            LocalDateTime orderDate = LocalDateTime.parse(scanner.nextLine());
            System.out.print("Enter new total amount: ");
            double totalAmount = scanner.nextDouble();
            System.out.print("Enter new status: ");
            scanner.nextLine(); 
            String status = scanner.nextLine();

            order.setCustomerId(customerId);
            order.setOrderDate(orderDate);
            order.setTotalAmount(totalAmount);
            order.setStatus(status);

            orderDAO.updateOrder(order);
            System.out.println("Order updated successfully.");
        } else {
            System.out.println("Order not found.");
        }
    }

    private static void cancelOrder(Scanner scanner, OrderDAO orderDAO) {
        System.out.print("Enter order ID: ");
        int orderId = scanner.nextInt();
        orderDAO.cancelOrder(orderId);
        System.out.println("Order cancelled successfully.");
    }
}

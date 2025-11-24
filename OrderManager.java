package Tabletap;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderManager {
    private static final String FILE = "orders.ser";
    private List<Order> orders;

    public OrderManager() {
        orders = loadOrders();
    }

    @SuppressWarnings("unchecked")
    private List<Order> loadOrders() {
        File f = new File(FILE);
        if (!f.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            return (List<Order>) obj;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE))) {
            oos.writeObject(orders);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addOrder(Order order) {
        orders.add(order);
        saveOrders();
    }

    public synchronized void updateOrder(Order order) {
        // assuming reference equality; we can replace by id if implemented
        saveOrders();
    }

    public synchronized List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public synchronized List<Order> getPendingOrders() {
        return orders.stream().filter(o -> "Pending".equals(o.status)).collect(Collectors.toList());
    }

    public synchronized void markOrderBilled(Order order) {
        order.status = "Billed";
        saveOrders();
    }

    public static String newSessionCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}


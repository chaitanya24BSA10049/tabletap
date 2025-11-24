package Tabletap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order implements Serializable {
    public int tableNumber;
    public String sessionCode; // unique per dining session
    public List<OrderItem> items = new ArrayList<>();
    public String status; // "Pending", "Billed"
    public Date timestamp;

    public Order(int tableNumber, String sessionCode) {
        this.tableNumber = tableNumber;
        this.sessionCode = sessionCode;
        this.status = "Pending";
        this.timestamp = new Date();
    }

    public double totalAmount() {
        double sum = 0.0;
        for (OrderItem it : items) {
            sum += it.totalPrice();
        }
        return sum;
    }

    @Override
    public String toString() {
        return "Table " + tableNumber + " | " + sessionCode + " | " + status + " | ₹" + totalAmount();
    }
}


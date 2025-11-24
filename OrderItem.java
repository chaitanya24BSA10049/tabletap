package Tabletap;

import java.io.Serializable;

public class OrderItem implements Serializable {
    public String name;
    public int quantity;
    public double priceEach;

    public OrderItem(String name, int quantity, double priceEach) {
        this.name = name;
        this.quantity = quantity;
        this.priceEach = priceEach;
    }

    public double totalPrice() {
        return priceEach * quantity;
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " = ₹" + totalPrice();
    }
}


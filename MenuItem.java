package Tabletap;

import java.io.Serializable;

public class MenuItem implements Serializable {
    public String name;
    public double price;

    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name + " - ₹" + price;
    }
}


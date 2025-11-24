package Tabletap;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderManager manager = new OrderManager();

            // Build default menu (you can edit later)
            List<MenuItem> menu = new ArrayList<>();
            menu.add(new MenuItem("Mustard Florrets", 700));
            menu.add(new MenuItem("Okra Salad", 650));
            menu.add(new MenuItem("Eggplant Curry", 450));
            menu.add(new MenuItem("Palak Paneer", 690));
            menu.add(new MenuItem("Chole Bhature", 550));
            menu.add(new MenuItem("Dal Makhani", 660));
            menu.add(new MenuItem("Lamb Chops", 750));
            menu.add(new MenuItem("Roasted Chicken ", 650));
            menu.add(new MenuItem("Grilled Scallops", 540));
            menu.add(new MenuItem("Fried Salmon & chips", 500));
            menu.add(new MenuItem("Bread Basket", 400));
            menu.add(new MenuItem("Chicken Biryani", 600));

            String[] options = {"Table Mode", "Kitchen Mode", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Choose mode", "TableTap",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, options[0]);

            if (choice == 0) {
                TableUI t = new TableUI(manager, menu);
                t.setVisible(true);
            } else if (choice == 1) {
                KitchenUI k = new KitchenUI(manager);
                k.setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }
}

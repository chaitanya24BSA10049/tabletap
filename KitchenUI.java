package Tabletap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class KitchenUI extends JFrame {
    private OrderManager manager;
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> orderList = new JList<>(listModel);

    public KitchenUI(OrderManager manager) {
        super("Kitchen Mode - TableTap - Large Screen");
        this.manager = manager;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        initUI();
        refreshOrders();
    }

    private void initUI() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshBtn = new JButton("Refresh");
        top.add(refreshBtn);
        JButton markDoneBtn = new JButton("Mark Selected as Billed");
        top.add(markDoneBtn);

        refreshBtn.addActionListener(e -> refreshOrders());

        markDoneBtn.addActionListener(e -> {
            int idx = orderList.getSelectedIndex();
            if (idx < 0) {
                JOptionPane.showMessageDialog(this, "Select an order from the list");
                return;
            }
            List<Order> pending = manager.getPendingOrders();
            if (idx >= pending.size()) {
                JOptionPane.showMessageDialog(this, "Selection out of range");
                return;
            }
            Order o = pending.get(idx);
            manager.markOrderBilled(o);
            refreshOrders();
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        orderList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        getContentPane().add(new JScrollPane(orderList), BorderLayout.CENTER);
    }

    private void refreshOrders() {
        listModel.clear();
        for (Order o : manager.getPendingOrders()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Table ").append(o.tableNumber)
                    .append(" | Code: ").append(o.sessionCode)
                    .append(" | Items: ");
            for (OrderItem it : o.items) {
                sb.append(it.name).append("x").append(it.quantity).append(", ");
            }
            sb.append(" | Total ₹").append(o.totalAmount());
            listModel.addElement(sb.toString());
        }
    }
}


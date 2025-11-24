package Tabletap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class TableUI extends JFrame {
    private OrderManager manager;
    private List<MenuItem> menu;
    private int tableNumber;
    private String sessionCode;
    private Order currentOrder;

    private DefaultListModel<String> cartModel = new DefaultListModel<>();
    private JList<String> cartList = new JList<>(cartModel);

    public TableUI(OrderManager manager, List<MenuItem> menu) {
        super("Table Mode - TableTap");
        this.manager = manager;
        this.menu = menu;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        top.add(new JLabel("Table Number:"));
        JTextField tableField = new JTextField(3);
        top.add(tableField);
        JButton startBtn = new JButton("Start Session");
        top.add(startBtn);

        startBtn.addActionListener(e -> {
            try {
                tableNumber = Integer.parseInt(tableField.getText().trim());
                sessionCode = OrderManager.newSessionCode();
                currentOrder = new Order(tableNumber, sessionCode);
                JOptionPane.showMessageDialog(this, "Session started. Code: " + sessionCode);
                setTitle("Table Mode - Table " + tableNumber + " - " + sessionCode);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid table number");
            }
        });

        // Menu panel
        JPanel menuPanel = new JPanel(new BorderLayout());
        DefaultListModel<MenuItem> menuModel = new DefaultListModel<>();
        for (MenuItem m : menu) menuModel.addElement(m);
        JList<MenuItem> menuList = new JList<>(menuModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuPanel.add(new JScrollPane(menuList), BorderLayout.CENTER);

        JButton addBtn = new JButton("Add to Order");
        menuPanel.add(addBtn, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> {
            MenuItem selected = menuList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(this, "Select a menu item first");
                return;
            }
            if (currentOrder == null) {
                JOptionPane.showMessageDialog(this, "Start a session first");
                return;
            }
            String qtyStr = JOptionPane.showInputDialog(this, "Quantity for " + selected.name, "1");
            if (qtyStr == null) return;
            int qty = 1;
            try {
                qty = Integer.parseInt(qtyStr.trim());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity, using 1");
            }
            OrderItem oi = new OrderItem(selected.name, qty, selected.price);
            currentOrder.items.add(oi);
            cartModel.addElement(oi.toString());
            // Save snapshot as an order entry so kitchen sees it: (we add order each time)
            // Option A: Add a new order each add — but better: only add once when checkout.
            // Here we'll add a copy to manager so kitchen sees "in-progress" orders:
            manager.addOrder(currentOrder);
        });

        // Cart and actions
        JPanel right = new JPanel(new BorderLayout());
        right.add(new JLabel("My Order:"), BorderLayout.NORTH);
        right.add(new JScrollPane(cartList), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton billBtn = new JButton("Generate Bill");
        JButton proceedPaymentBtn = new JButton("Proceed to Payment (show UPI)");
        bottom.add(proceedPaymentBtn);
        bottom.add(billBtn);
        right.add(bottom, BorderLayout.SOUTH);

        proceedPaymentBtn.addActionListener(e -> {
            if (currentOrder == null) {
                JOptionPane.showMessageDialog(this, "Start session and add items first.");
                return;
            }
            double total = currentOrder.totalAmount();
            // Simple QR placeholder
            JTextArea ta = new JTextArea("UPI QR CODE placeholder\nAmount: ₹" + total + "\n(Scan to pay)\nSession: " + sessionCode);
            ta.setEditable(false);
            ta.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
            JOptionPane.showMessageDialog(this, new JScrollPane(ta), "UPI Payment", JOptionPane.PLAIN_MESSAGE);
        });

        billBtn.addActionListener(e -> {
            if (currentOrder == null || currentOrder.items.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No items to bill.");
                return;
            }
            double total = currentOrder.totalAmount();
            int res = JOptionPane.showConfirmDialog(this, "Total: ₹" + total + "\nGenerate bill and finish session?", "Confirm Bill", JOptionPane.YES_NO_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                // Mark existing orders for this session as billed
                manager.markOrderBilled(currentOrder);
                JOptionPane.showMessageDialog(this, "Billed! Session ended for code: " + sessionCode);
                // clear current order and prepare new session code for next customers
                currentOrder = null;
                cartModel.clear();
                sessionCode = null;
                setTitle("Table Mode - TableTap");
            }
        });

        // Layout
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(menuPanel, BorderLayout.WEST);
        getContentPane().add(right, BorderLayout.CENTER);
    }
}
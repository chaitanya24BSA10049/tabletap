# tabletap
Overview of the Project
This project is a Java Swing-based application for restaurant table ordering and kitchen management. It supports customer ordering, bill generation, and kitchen staff management in a simple, modular desktop app.

Features
Table Mode: Menu browsing, placing orders, generating bills, and simulated payment.
Kitchen Mode: Viewing active orders, marking orders as billed, tracking order history.
Multiple classes for clean separation of concerns (Order, OrderItem, MenuItem, Manager, UI).
Intuitive graphical user interface built fully with Java Swing.

Technologies/Tools Used
Java 8+ (core language)
Java Swing (desktop GUI)
No external libraries required

Steps to Install & Run the Project
Ensure Java (JDK 8 or above) is installed on your system.
Place all provided .java files in a single directory.

Compile all files:

text
javac *.java
Launch the main application:

text
java MainApp
Upon running, choose Table Mode or Kitchen Mode as per your role.

Instructions for Testing
Table Mode: Simulate a table session, select items from the menu, and proceed to checkout for bill calculation and payment.

Kitchen Mode: View pending orders, update order statuses, mark them as billed, and track the fulfilled orders.

Modifications to menu and session can be made directly in the MainApp.java file if custom menu items are needed.

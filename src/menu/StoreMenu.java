package menu;

import dao.ClothingItemDAO;
import model.Shirt;

import java.sql.SQLException;
import java.util.Scanner;

public class StoreMenu {

    private ClothingItemDAO dao = new ClothingItemDAO();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("""
                    === CLOTHING STORE ===
                    1. Add shirt
                    2. View all items
                    3. Delete item
                    0. Exit
                    """);

            int choice = scanner.nextInt();

            try {
                switch (choice) {
                    case 1 -> addShirt();
                    case 2 -> viewAll();
                    case 3 -> deleteItem();
                    case 0 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addShirt() throws SQLException {
        System.out.print("Name: ");
        String name = scanner.next();

        System.out.print("Price: ");
        double price = scanner.nextDouble();

        System.out.print("Size: ");
        String size = scanner.next();

        dao.addShirt(new Shirt(0, name, price, size));
    }

    private void viewAll() throws SQLException {
        dao.getAllItems().forEach(System.out::println);
    }

    private void deleteItem() throws SQLException {
        System.out.print("ID: ");
        dao.deleteItem(scanner.nextInt());
    }
}
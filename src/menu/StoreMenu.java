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
            System.out.println("\n=== CLOTHING STORE (DB) ===");
            System.out.println("1. Add shirt");
            System.out.println("2. View all items");
            System.out.println("3. Delete item (with confirm)");
            System.out.println("4. Search by name");
            System.out.println("5. Update price");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addShirt();
                    case 2 -> viewAll();
                    case 3 -> deleteItem();
                    case 4 -> searchByName();
                    case 5 -> updatePrice();
                    case 0 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void addShirt() throws SQLException {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Price: "); double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Size: "); String size = scanner.nextLine();
        dao.addItem(new Shirt(0, name, price, size), "Shirt", size);
        System.out.println("Success!");
    }

    private void viewAll() throws SQLException {
        dao.getAllItems().forEach(System.out::println);
    }

    private void searchByName() throws SQLException {
        System.out.print("Search query: ");
        String query = scanner.nextLine();
        dao.searchByName(query).forEach(System.out::println);
    }

    private void updatePrice() throws SQLException {
        System.out.print("ID: "); int id = Integer.parseInt(scanner.nextLine());
        System.out.print("New Price: "); double price = Double.parseDouble(scanner.nextLine());
        dao.updatePrice(id, price);
        System.out.println("Price updated!");
    }

    private void deleteItem() throws SQLException {
        System.out.print("ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Are you sure? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            if (dao.deleteItem(id)) System.out.println("Deleted.");
            else System.out.println("Not found.");
        }
    }
}
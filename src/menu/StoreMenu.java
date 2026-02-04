package menu;

import dao.ClothingItemDAO;
import model.ClothingItem;
import model.Shirt;
import model.Pants;

import java.sql.SQLException;
import java.util.Scanner;

public class StoreMenu {

    private ClothingItemDAO dao = new ClothingItemDAO();
    private Scanner scanner = new Scanner(System.in);

    public void run() {
        while (true) {
            System.out.println("\n=== CLOTHING STORE (DB) ===");
            System.out.println("1. Add shirt");
            System.out.println("2. Add pants");
            System.out.println("3. View all items");
            System.out.println("4. Update item");
            System.out.println("5. Delete item");
            System.out.println("6. Search by name");
            System.out.println("7. Search by price range");
            System.out.println("8. Search by min price");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addShirt();
                    case 2 -> addPants();
                    case 3 -> viewAll();
                    case 4 -> updateItem();
                    case 5 -> deleteItem();
                    case 6 -> searchByName();
                    case 7 -> searchByPriceRange();
                    case 8 -> searchByMinPrice();
                    case 0 -> System.exit(0);
                }
            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    private void addShirt() throws SQLException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Size: ");
        String size = scanner.nextLine();

        dao.addItem(new Shirt(0, name, price, size), "Shirt", size);
        System.out.println("Added");
    }

    private void addPants() throws SQLException {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Price: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.print("Length: ");
        int length = Integer.parseInt(scanner.nextLine());

        dao.addItem(new Pants(0, name, price, length), "Pants", String.valueOf(length));
        System.out.println("Added");
    }

    private void viewAll() throws SQLException {
        dao.getAllItems().forEach(System.out::println);
    }

    private void searchByName() throws SQLException {
        System.out.print("Query: ");
        dao.searchByName(scanner.nextLine()).forEach(System.out::println);
    }

    private void searchByPriceRange() throws SQLException {
        System.out.print("Min price: ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Max price: ");
        double max = Double.parseDouble(scanner.nextLine());

        dao.searchByPriceRange(min, max).forEach(System.out::println);
    }

    private void searchByMinPrice() throws SQLException {
        System.out.print("Min price: ");
        double min = Double.parseDouble(scanner.nextLine());

        dao.searchByMinPrice(min).forEach(System.out::println);
    }

    private void updateItem() throws SQLException {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        ClothingItem oldItem = dao.getAllItems().stream()
                .filter(i -> i.getId() == id)
                .findFirst()
                .orElse(null);

        if (oldItem == null) {
            System.out.println("Not found");
            return;
        }

        System.out.print("New name (" + oldItem.getName() + "): ");
        String name = scanner.nextLine();
        if (name.isBlank()) name = oldItem.getName();

        System.out.print("New price (" + oldItem.getPrice() + "): ");
        String priceInput = scanner.nextLine();
        double price = priceInput.isBlank() ? oldItem.getPrice() : Double.parseDouble(priceInput);

        if (oldItem instanceof Shirt) {
            System.out.print("New size: ");
            String size = scanner.nextLine();
            dao.updateItem(new Shirt(id, name, price, size), "Shirt", size);
        } else if (oldItem instanceof Pants) {
            System.out.print("New length: ");
            int length = Integer.parseInt(scanner.nextLine());
            dao.updateItem(new Pants(id, name, price, length), "Pants", String.valueOf(length));
        }

        System.out.println("Updated");
    }

    private void deleteItem() throws SQLException {
        System.out.print("ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Confirm (yes/no): ");
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            if (dao.deleteItem(id)) System.out.println("Deleted");
            else System.out.println("Not found");
        }
    }
}
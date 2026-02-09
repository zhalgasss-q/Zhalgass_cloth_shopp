package dao;

import model.ClothingItem;
import model.Shirt;
import model.Pants;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothingItemDAO {

    public void addItem(ClothingItem item, String type, String extra) throws SQLException {
        String sql = "INSERT INTO clothing_items(name, price, size, type) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, extra);
            ps.setString(4, type);
            ps.executeUpdate();
        }
    }

    public List<ClothingItem> getAllItems() throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_items ORDER BY id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                items.add(mapRow(rs));
            }
        }
        return items;
    }

    public List<ClothingItem> searchByName(String query) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();
        String sql = "SELECT * FROM clothing_items WHERE name ILIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + query + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                items.add(mapRow(rs));
            }
        }
        return items;
    }

    public void updatePrice(int id, double newPrice) throws SQLException {
        String sql = "UPDATE clothing_items SET price = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, newPrice);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    public boolean updateItem(ClothingItem item, String type, String extra) throws SQLException {
        String sql = """
                UPDATE clothing_items
                SET name = ?, price = ?, size = ?, type = ?
                WHERE id = ?
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, item.getName());
            ps.setDouble(2, item.getPrice());
            ps.setString(3, extra);
            ps.setString(4, type);
            ps.setInt(5, item.getId());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM clothing_items WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    public List<ClothingItem> searchByPriceRange(double minPrice, double maxPrice) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();

        String sql = """
                SELECT * FROM clothing_items
                WHERE price BETWEEN ? AND ?
                ORDER BY price
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(mapRow(rs));
            }
        }
        return items;
    }

    public List<ClothingItem> searchByMinPrice(double minPrice) throws SQLException {
        List<ClothingItem> items = new ArrayList<>();

        String sql = """
SELECT * FROM clothing_items
                WHERE price >= ?
                ORDER BY price DESC
                """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, minPrice);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                items.add(mapRow(rs));
            }
        }
        return items;
    }

    private ClothingItem mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        double price = rs.getDouble("price");
        String type = rs.getString("type");
        String size = rs.getString("size");

        if ("Pants".equalsIgnoreCase(type)) {
            return new Pants(id, name, price, Integer.parseInt(size));
        }
        return new Shirt(id, name, price, size);
    }
}

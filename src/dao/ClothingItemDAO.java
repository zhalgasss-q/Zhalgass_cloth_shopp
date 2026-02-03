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

    public boolean deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM clothing_items WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private ClothingItem mapRow(ResultSet rs) throws SQLException {
        String type = rs.getString("type");
        if ("Pants".equalsIgnoreCase(type)) {
            return new Pants(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), 32);
        }
        return new Shirt(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"), rs.getString("size"));
    }
}
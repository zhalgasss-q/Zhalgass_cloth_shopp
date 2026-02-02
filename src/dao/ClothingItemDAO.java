package dao;

import model.ClothingItem;
import model.Shirt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClothingItemDAO {

    public void addShirt(Shirt shirt) throws SQLException {
        String sql = "INSERT INTO clothing_items(name, price, size) VALUES (?, ?, ?)";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, shirt.getName());
        ps.setDouble(2, shirt.getPrice());
        ps.setString(3, shirt.getSize());

        ps.executeUpdate();
        conn.close();
    }

    public List<ClothingItem> getAllItems() throws SQLException {
        List<ClothingItem> items = new ArrayList<>();

        String sql = "SELECT * FROM clothing_items";
        Connection conn = DatabaseConnection.getConnection();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        while (rs.next()) {
            items.add(
                    new Shirt(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getDouble("price"),
                            rs.getString("size")
                    )
            );
        }

        conn.close();
        return items;
    }

    public void deleteItem(int id) throws SQLException {
        String sql = "DELETE FROM clothing_items WHERE id = ?";

        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();
        conn.close();
    }
}
package com.example.netflix.repositories;

import com.example.netflix.infrastructure.DatabaseConfig;
import com.example.netflix.models.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final DatabaseConfig config;

    public UserRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();

            if (keys.next()) {
                int id  = keys.getInt(1);
                user.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add new user", e);
        }
    }

    public List<User> readAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("name");

                User user = new User(id, name, email);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new  RuntimeException("Failed to read all users", e);
        }

        return users;
    }

    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2,user.getEmail());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new  RuntimeException("Failed to update user", e);
        }
    }

    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new  RuntimeException("Failed to delete user", e);
        }
    }
}

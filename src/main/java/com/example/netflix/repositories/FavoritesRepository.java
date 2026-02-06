package com.example.netflix.repositories;

import com.example.netflix.infrastructure.DatabaseConfig;
import com.example.netflix.models.Favorite;
import com.example.netflix.models.Movie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {
    private DatabaseConfig config;

    public FavoritesRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void addFavorite(Favorite favorite) {
        String sql = "INSERT INTO favorites (usr_id, movie_id) VALUES (?, ?)";

        try (Connection conn= config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, favorite.getUserId());
            stmt.setInt(2, favorite.getMovieId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add movie to favorites", e);
        }
    }

    public int getFavoriteIdByEmailAndMovie(String email, int movieId) {
        String sql = """
                SELECT fav_id FROM favorites f
                INNER JOIN users u ON f.usr_id = u.id
                WHERE u.email = ? AND f.movie_id = ?
                """;

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setInt(2, movieId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("fav_id");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to get favorite id", e);
        }

        return 0;
    }

    public List<Movie> readAllFavorites(String email) {
        List<Movie> favorites = new ArrayList<>();
        String sql = """
                SELECT m.id, m.name, m.genre, m.length, m.description FROM movies m
                INNER JOIN favorites f ON m.id = f.movie_id
                INNER JOIN users u ON f.usr_id = u.id WHERE u.email = ?""";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("name");
                String genre = rs.getString("genre");
                String length = rs.getString("length");
                String description= rs.getString("description");

                Movie movie = new Movie(id, title, genre, length, description);
                favorites.add(movie);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to read all favorites", e);
        }

        return favorites;
    }

    public Boolean removeFavorite(int id) {
        String sql = "DELETE FROM favorites WHERE fav_id = ?";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to remove favorite", e);
        }
    }
}

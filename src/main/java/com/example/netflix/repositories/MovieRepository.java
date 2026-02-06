package com.example.netflix.repositories;

import com.example.netflix.infrastructure.DatabaseConfig;
import com.example.netflix.models.Movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private final DatabaseConfig config;

    public MovieRepository(DatabaseConfig config) {
        this.config = config;
    }

    public void addMovie (Movie movie) {
       String sql ="insert into movies (name, genre, length, description) VALUES (?,?,?,?)";
          try (Connection conn = config.getConnection();
               PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

              stmt.setString(1,movie.getTitle());
              stmt.setString(2,movie.getGenre());
              stmt.setString(3,movie.getLength());
              stmt.setString(4,movie.getDescription());
              stmt.executeUpdate();

              ResultSet keys = stmt.getGeneratedKeys();

              if (keys.next()) {
                  int id  = keys.getInt(1);
                  movie.setId(id);
              }

          } catch(SQLException e) {
              throw new RuntimeException("something happen while trying to add the movie please try again ");


          }
    }

    public List<Movie> readAllMovies() {

        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT id, name, genre, length, description FROM movies";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("name");
                String genre = rs.getString("genre");
                String length = rs.getString("length");
                String description= rs.getString("description");

                Movie movie = new Movie(id, title, genre, length, description);
                movies.add(movie);
            }

        } catch (SQLException e) {
            throw new  RuntimeException("Failed to read all Movies", e);
        }

        return movies;
    }

    public Boolean removeMovie (int id){
        String sql ="Delete FROM movies WHERE ID = ?";
        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            stmt.setInt(1,id);

            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e){
            throw new RuntimeException("can't find the Movie");
        }

    }
}




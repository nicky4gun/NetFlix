package com.example.netflix;

import com.example.netflix.infrastructure.DatabaseConfig;
import com.example.netflix.models.Movie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    private DatabaseConfig config;

    public void setConfig(DatabaseConfig config) {
        this.config = config;
        testConnection();
        testDb();

        showMovies();
        getAllMovies();
    }

    // FXML her
    @FXML private TableView<Movie> movieTableView;
    @FXML private TableColumn<Movie, Integer> movieIdColumn;
    @FXML private TableColumn<Movie, String> movieNameColumn;
    @FXML private TableColumn<Movie, String> movieGenreColumn;
    @FXML private TableColumn<Movie, String> movieLengthColumn;
    @FXML private TableColumn<Movie, String> movieDescriptionColumn;

    @FXML private TextField emailTxt;
    @FXML private TextField movieTxt;
    @FXML private Button add;
    @FXML private Button remove;
    @FXML private Label errorLabel;

    private ObservableList<Movie> movieList;

    public void initialize() {
        movieList = FXCollections.observableArrayList();

        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        movieLengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        movieDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    // Milepæl B: Viser antal film i console + test af connection
    public void testDb() {
        String sql = "SELECT COUNT(*) FROM movies";

        try (Connection conn = config.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            int count = 0;
            if (rs.next()) {
                count += rs.getInt(1);
            }

            System.out.println("Number of movies in database: " + count);

        } catch (SQLException e) {
            System.out.println("Database connection error");
        }
    }

    public void testConnection() {
        try {
            Connection conn = config.getConnection();
            System.out.println("Connection to database");
        } catch (SQLException e) {
            System.out.println("Database connection error");
        }
    }

    public void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showMovies( ){
        if (config == null) {
            System.err.println("Config not set yet!");
            return;
        }

        movieList.clear();
        movieList.addAll(getAllMovies());
        movieTableView.setItems(movieList);
    }

    public List<Movie> readAllMovies() {
        if (config == null) {
            throw new IllegalStateException("DatabaseConfig not set");
        }

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

    public List<Movie> getAllMovies() {
        return readAllMovies();
    }
}

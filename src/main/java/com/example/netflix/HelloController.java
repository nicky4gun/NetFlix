package com.example.netflix;

import com.example.netflix.infrastructure.DatabaseConfig;
import com.example.netflix.models.Movie;
import com.example.netflix.services.FavoritesService;
import com.example.netflix.services.MovieService;
import com.example.netflix.services.UserService;
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
    private UserService userService;
    private MovieService movieService;
    private FavoritesService favoritesService;

    public void setConfig(DatabaseConfig config) {
        this.config = config;
        testConnection();
        testDb();
    }

    public void setService(UserService userService, MovieService movieService, FavoritesService favoritesService) {
        this.userService = userService;
        this.movieService = movieService;
        this.favoritesService = favoritesService;

        loadMovies();
    }

    // FXML her
    @FXML private TableView<Movie> movieTableView;
    @FXML private TableColumn<Movie, Integer> movieIdColumn;
    @FXML private TableColumn<Movie, String> movieNameColumn;
    @FXML private TableColumn<Movie, String> movieGenreColumn;
    @FXML private TableColumn<Movie, String> movieLengthColumn;
    @FXML private TableColumn<Movie, String> movieDescriptionColumn;
    @FXML private ListView<Movie> favoritesList;

    @FXML private TextField emailTxt;
    @FXML private TextField movieTxt;
    @FXML private Button add;
    @FXML private Button remove;
    @FXML private Label errorLabel;
    @FXML private Button confirmEmail;

    private ObservableList<Movie> movieList;
    private ObservableList<Movie> favorites;

    public void initialize() {
        movieList = FXCollections.observableArrayList();
        favorites = FXCollections.observableArrayList();

        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        movieLengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        movieDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    // Milepæl B: Viser antal film i console + test af connection (Skal ikke ligge her i ui)
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

    public void loadMovies( ){
        movieList.clear();
        movieList.addAll(movieService.getAllMovies());
        movieTableView.setItems(movieList);
    }

    public void onActionConfirmEmail()  {
       String email = emailTxt.getText();
       List<Movie> favoriteMovies = favoritesService.getFavoritesByEmail(email);

       favorites.clear();
       favorites.addAll(favoriteMovies);
       favoritesList.setItems(favorites);
       emailTxt.clear();

       if (email.isEmpty()) {
           refreshFavoritesView();
       }
    }

    public void refreshFavoritesView() {
        favorites.clear();
    }

    public void add() {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
        String email = emailTxt.getText();

        if (selectedMovie != null && email != null) {
            favoritesService.addFavorite(email, selectedMovie.getId());
            favorites.add(selectedMovie);
        }
    }

    public void remove() {

    }

    public void onActionMovieTxt() {

    }
}

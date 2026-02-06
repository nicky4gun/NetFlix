package com.example.netflix.ui;

import com.example.netflix.models.Movie;
import com.example.netflix.services.FavoritesService;
import com.example.netflix.services.MovieService;
import com.example.netflix.services.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.util.List;

public class StreamingController {
    private UserService userService;
    private MovieService movieService;
    private FavoritesService favoritesService;

    @FXML private TableView<Movie> movieTableView;
    @FXML private TableColumn<Movie, Integer> movieIdColumn;
    @FXML private TableColumn<Movie, String> movieNameColumn;
    @FXML private TableColumn<Movie, String> movieGenreColumn;
    @FXML private TableColumn<Movie, String> movieLengthColumn;
    @FXML private TableColumn<Movie, String> movieDescriptionColumn;
    @FXML private ListView<Movie> favoritesList;

    @FXML private TextField emailTxt;
    @FXML private Label errorLabel;

    private ObservableList<Movie> movieList;
    private ObservableList<Movie> favorites;

    public void setService(UserService userService, MovieService movieService, FavoritesService favoritesService) {
        this.userService = userService;
        this.movieService = movieService;
        this.favoritesService = favoritesService;

        loadMovies();
    }

    public void initialize() {
        movieList = FXCollections.observableArrayList();
        favorites = FXCollections.observableArrayList();

        movieIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        movieNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        movieGenreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        movieLengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
        movieDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    public void loadMovies( ){
        movieList.clear();
        movieList.addAll(movieService.getAllMovies());
        movieTableView.setItems(movieList);
    }

    @FXML
    protected void onActionConfirmEmail() {
       String email = emailTxt.getText();

        if (email == null || email.isEmpty()) {
            favorites.clear();
            return;
        }

        try {
           List<Movie> favoriteMovies = favoritesService.getFavoritesByEmail(email);
           favorites.clear();
           favorites.addAll(favoriteMovies);
           favoritesList.setItems(favorites);

           errorLabel.setText("Success: Loaded " + favoriteMovies.size() + " movies");
           errorLabel.setTextFill(Color.GREEN);
           emailTxt.clear();

       } catch (IllegalArgumentException e) {
           errorLabel.setText("Error: " + e.getMessage());
           errorLabel.setTextFill(Color.RED);
       }
    }

    @FXML
    protected void add() {
        Movie selectedMovie = movieTableView.getSelectionModel().getSelectedItem();
        String email = emailTxt.getText();

        if (selectedMovie != null && email != null) {
            try {
                favoritesService.addFavorite(email, selectedMovie.getId());
                favorites.add(selectedMovie);

                errorLabel.setText("Success: Added movie " + selectedMovie.getTitle() + " to " + email + "'s favorites");
                errorLabel.setTextFill(Color.GREEN);
                emailTxt.clear();

            } catch (IllegalArgumentException e) {
                errorLabel.setText("Error: " + e.getMessage());
                errorLabel.setTextFill(Color.RED);
            }
        }
    }

    @FXML
    protected void remove() {
        Movie selectedMovie = favoritesList.getSelectionModel().getSelectedItem();
        String email = emailTxt.getText();

        try {
            if (selectedMovie != null && email != null) {
                favoritesService.deleteFavorite(email, selectedMovie.getId());
                favorites.remove(selectedMovie);
            }

            errorLabel.setText("Success: Removed " + selectedMovie.getTitle() + " from favorites");
            errorLabel.setTextFill(Color.GREEN);

        } catch (IllegalArgumentException e) {
            errorLabel.setText("Error: " + e.getMessage());
            errorLabel.setTextFill(Color.RED);
        }
    }
}

package com.example.netflix;

import com.example.netflix.infrastructure.DatabaseConfig;
import com.example.netflix.repositories.FavoritesRepository;
import com.example.netflix.repositories.MovieRepository;
import com.example.netflix.repositories.UserRepository;
import com.example.netflix.services.FavoritesService;
import com.example.netflix.services.MovieService;
import com.example.netflix.services.UserService;
import com.example.netflix.ui.StreamingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StreamingApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Initialize classes
        DatabaseConfig config = new DatabaseConfig();
        UserRepository userRepository = new UserRepository(config);
        MovieRepository movieRepository = new MovieRepository(config);
        FavoritesRepository favoritesRepository = new FavoritesRepository(config);

        UserService userService = new UserService(userRepository);
        MovieService movieService = new MovieService(movieRepository);
        FavoritesService favoritesService = new FavoritesService(favoritesRepository, userRepository);

        FXMLLoader fxmlLoader = new FXMLLoader(StreamingApplication.class.getResource("netflix.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 450);

        // Service Injection
        StreamingController controller = fxmlLoader.getController();
        controller.setService(userService, movieService, favoritesService);

        stage.setTitle("Streaming Platform");
        stage.setScene(scene);
        stage.show();
    }
}

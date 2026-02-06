package com.example.netflix;

import com.example.netflix.infrastructure.DatabaseConfig;
import com.example.netflix.repositories.FavoritesRepository;
import com.example.netflix.repositories.MovieRepository;
import com.example.netflix.repositories.UserRepository;
import com.example.netflix.services.FavoritesService;
import com.example.netflix.services.MovieService;
import com.example.netflix.services.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
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

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("netflix.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 950, 450);

        // Temporary Injection (meant to be services)
        HelloController controller = fxmlLoader.getController();
        controller.setConfig(config);
        controller.setService(userService, movieService, favoritesService);

        stage.setTitle("Streaming Platform");
        stage.setScene(scene);
        stage.show();
    }
}

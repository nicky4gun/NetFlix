package com.example.netflix;

import com.example.netflix.infrastructure.DatabaseConfig;
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

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("netflix.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);

        // Temporary Injection (meant to be services)
        HelloController controller = fxmlLoader.getController();
        controller.setConfig(config);

        stage.setTitle("Streaming Platform");
        stage.setScene(scene);
        stage.show();
    }
}

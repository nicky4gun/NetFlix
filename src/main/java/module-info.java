module com.example.netflix {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.netflix to javafx.fxml;
    exports com.example.netflix;

    opens com.example.netflix.infrastructure to javafx.fxml;
    exports com.example.netflix.infrastructure;

    opens com.example.netflix.models to javafx.fxml;
    exports com.example.netflix.models;

    opens com.example.netflix.services to javafx.fxml;
    exports com.example.netflix.services;

    opens com.example.netflix.repositories to javafx.fxml;
    exports com.example.netflix.repositories;
    exports com.example.netflix.ui;
    opens com.example.netflix.ui to javafx.fxml;
}
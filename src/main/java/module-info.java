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
}
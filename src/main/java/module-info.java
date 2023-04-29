module com.example.aisi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.aisi to javafx.fxml;
    exports com.example.aisi;
    exports com.example.aisi.model;
    opens com.example.aisi.model;
}
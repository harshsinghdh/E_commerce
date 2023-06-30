module com.example.ecommercce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.ecommercce to javafx.fxml;
    exports com.example.ecommercce;
}
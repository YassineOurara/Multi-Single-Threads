module com.example.tp_sys {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tp_sys to javafx.fxml;
    exports com.example.tp_sys;
}
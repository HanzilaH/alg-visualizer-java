module com.example.algvisualizerjava {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.algvisualizerjava to javafx.fxml;
    exports com.example.algvisualizerjava;
}
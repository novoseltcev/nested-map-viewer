module com.nested_map_viewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    opens com.nested_map_viewer to javafx.fxml;
    opens com.nested_map_viewer.controller to javafx.fxml;
    exports com.nested_map_viewer.app;
    exports com.nested_map_viewer.controller;
    opens com.nested_map_viewer.app to javafx.fxml;
}
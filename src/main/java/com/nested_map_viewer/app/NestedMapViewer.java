package com.nested_map_viewer.app;


public class NestedMapViewer extends CustomApp {
    public NestedMapViewer() {
        super("viewer.fxml", "NMapViewer");
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    protected void show() {
        stage.show();
    }
}


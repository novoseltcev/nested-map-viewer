package com.nested_map_viewer.controller;

import com.nested_map_viewer.app.NotifyApp;
import javafx.scene.control.Label;

import java.io.StreamCorruptedException;


/**
 * javafx controller class for start handling exception
 * @author Novoseltcev Stanislav
 * @version 1.0
 */
public class NotifyController extends Controller {

    public Label errorLabel;

    @Override
    protected void initialize() {
        Exception error = ((NotifyApp)app).getException();

        Class<? extends Exception> errorType = error.getClass();
        String errorText = "Handling unexpected error";
        if (errorType.equals(StreamCorruptedException.class)) {
            errorText = "Invalid to load file";
        }
        errorLabel.setText(errorText);
    }
}

package com.nested_map_viewer.controller;

import com.nested_map_viewer.app.CustomApp;


/**
 * Abstract controller for javafx
 * @author Novoseltcev Stanislav
 * @version 1.0
 */
public abstract class Controller {
    protected CustomApp app;

    public void setApp(CustomApp app) {
        this.app = app;
        initialize();
    }

    protected abstract void initialize();
}

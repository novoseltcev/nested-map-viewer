package com.nested_map_viewer.controller;

import com.nested_map_viewer.app.CustomApp;

public abstract class Controller {
    protected CustomApp app;

    public void setApp(CustomApp app) {
        this.app = app;
        initialize();
    }

    protected abstract void initialize();
}

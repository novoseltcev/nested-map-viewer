package com.nested_map_viewer.controller;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.nested_map_viewer.utils.Utils;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import com.nested_map_viewer.app.NotifyApp;
import com.nested_map_viewer.model.Chain;
import com.nested_map_viewer.model.NestedMap;





/**
 * Controller by processing user requests
 * @author Novoseltcev Stanislav
 * @version 1.0
 */
public class ViewController extends Controller {
    public Menu fileMenu;
    public MenuItem linkMenu;
    public MenuItem loadLinkMenu;
    public MenuItem saveLinkMenu;

    public TextField nodeFilter;
    public ListView<String> nodeView;
    public TextField nestedFilter1;
    public ListView<String> nestedView1;
    public TextField nestedFilter2;
    public ListView<String> nestedView2;
    public TextField nestedFilter3;
    public ListView<String> nestedView3;
    public TextField nestedFilter4;
    public ListView<String> nestedView4;
    public TextField nestedFilter5;
    public ListView<String> nestedView5;
    public TextField nestedFilter6;
    public ListView<String> nestedView6;
    public TextField nestedFilter7;
    public ListView<String> nestedView7;

    NestedMap<String> nestedMap;
    Chain<String> chain;

    HashMap<Integer, ListView<String>> indexToView;
    HashMap<Integer, TextField> indexToFilter;

    @Override
    protected void initialize() {
        indexToView = new HashMap<>() {{
            put(0, nodeView);
            put(1, nestedView1);
            put(2, nestedView2);
            put(3, nestedView3);
            put(4, nestedView4);
            put(5, nestedView5);
            put(6, nestedView6);
            put(7, nestedView7);
        }};
        indexToFilter = new HashMap<>() {{
            put(0, nodeFilter);
            put(1, nestedFilter1);
            put(2, nestedFilter2);
            put(3, nestedFilter3);
            put(4, nestedFilter4);
            put(5, nestedFilter5);
            put(6, nestedFilter6);
            put(7, nestedFilter7);
        }};

        setFilterHandler(nodeFilter);
        setFilterHandler(nestedFilter1);
        setFilterHandler(nestedFilter2);
        setFilterHandler(nestedFilter3);
        setFilterHandler(nestedFilter4);
        setFilterHandler(nestedFilter5);
        setFilterHandler(nestedFilter6);
        setFilterHandler(nestedFilter7);
    }

    private void setFilterHandler(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newValue, oldValue)) {
                try{
                    updateViews();
                } catch (StreamCorruptedException e) {
                    try {
                        new NotifyApp(e, Alert.AlertType.WARNING);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    private void setShortcutHandler() {
        final KeyCombination saveComb = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN);
        final KeyCombination loadComb = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN);
        app.getStage().getScene().setOnKeyPressed(event -> {
            try {
                if (saveComb.match(event)) {
                    saveLink();
                    event.consume(); // <-- stops passing the event to next node
                } else if (loadComb.match(event)) {
                    loadLink();
                    event.consume();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /** choose File from FileChooser and load NestedMap
     * */
    public void openFile() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open HashMap");
        fileChooser.setInitialDirectory(new File("data"));
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("JSON files)", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(app.getStage());
        try {
            if (selectedFile != null) {
                String jsonStr = Utils.readFile(selectedFile.getAbsolutePath());
                TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
                };
                nestedMap = new NestedMap<>(new ObjectMapper().readValue(jsonStr, typeRef));
                chain = new Chain<>();
                updateViews();
                setShortcutHandler();
                linkMenu.setDisable(false);
            }
        } catch (IOException e) {
            new NotifyApp(e, Alert.AlertType.WARNING);
        }
    }

    private void activateSelection(ListView<String> view, TextField field, List<String> values) {
        field.setEditable(true);
        view.setItems(
                FXCollections.observableArrayList(
                        values.stream().filter(e -> e.toLowerCase()
                                        .startsWith(field.getText().toLowerCase()))
                                .toList()
                ));
    }

    private void deactivateSelection(ListView<String> view, TextField field) {
        field.setText("");
        field.setEditable(false);
        view.setItems(
                FXCollections.observableArrayList());
    }

    private void updateViews() throws StreamCorruptedException {
        nestedMap.setChain(chain);
        for (int i = 0; i < 8; i++) {
            TextField field = indexToFilter.get(i);
            ListView<String> view = indexToView.get(i);
            if (i <= chain.size()) {
                activateSelection(view, field, nestedMap.getKeyTrace().get(i));
            } else {
                deactivateSelection(view, field);
            }
        }
    }

    private void reduceLink(int index, String selectionItem) {
        int length = chain.size();
        for (int i = index; i < length; i++) {
            chain.pollLast();
        }
        chain.add(selectionItem);
    }

    public void clickedByView(int index) throws Exception {
        try {
            String item = indexToView.get(index).getSelectionModel().getSelectedItem();
            if (item != null && !item.isEmpty()) {
                reduceLink(index, item);
                updateViews();
            }
        } catch (StreamCorruptedException e) {
            new NotifyApp(e, Alert.AlertType.WARNING);
        }
    }

    public void clickedByView0() throws Exception {
        clickedByView(0);
    }

    public void clickedByView1() throws Exception {
        clickedByView(1);
    }

    public void clickedByView2() throws Exception {
        clickedByView(2);
    }

    public void clickedByView3() throws Exception {
        clickedByView(3);
    }

    public void clickedByView4() throws Exception {
        clickedByView(4);
    }

    public void clickedByView5() throws Exception {
        clickedByView(5);
    }

    public void clickedByView6() throws Exception {
        clickedByView(6);
    }

    public void clickedByView7() {
        String item = nestedView7.getSelectionModel().getSelectedItem();
        if (item != null && !item.isEmpty()) {
            if (chain.size() == 8)
                chain.pollLast();
            chain.add(item);
        }
    }

    public void saveLink() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save link");
        fileChooser.setInitialDirectory(new File("data"));

        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter(".link", "*.link"),
                new ExtensionFilter("other", "*")
        );
        File selectedFile = fileChooser.showSaveDialog(app.getStage());
        if (selectedFile != null) {
            chain.saveToFile(selectedFile);
        }
    }

    public void loadLink() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load link");
        fileChooser.setInitialDirectory(new File("data"));

        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter(".link", "*.link"),
                new ExtensionFilter("other", "*")
        );
        File selectedFile = fileChooser.showOpenDialog(app.getStage());
        try{
            if (selectedFile != null) {
                chain = Chain.getFromFile(selectedFile);
                updateViews();
                for (int i = 0; i < chain.size(); i++) {
                    indexToView.get(i).getSelectionModel().select(chain.get(i));
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            new NotifyApp(e, Alert.AlertType.WARNING);
        }
    }
}
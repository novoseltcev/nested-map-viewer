package com.nested_map_viewer.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nested_map_viewer.model.Link;
import com.nested_map_viewer.model.NestedMap;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ViewController extends Controller {
    public Menu fileMenu;
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
    Link<String> link;

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

        setColumnHandler(nodeFilter);
        setColumnHandler(nestedFilter1);
        setColumnHandler(nestedFilter2);
        setColumnHandler(nestedFilter3);
        setColumnHandler(nestedFilter4);
        setColumnHandler(nestedFilter5);
        setColumnHandler(nestedFilter6);
        setColumnHandler(nestedFilter7);


    }

    private void setColumnHandler(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(newValue, oldValue)) {
                updateViews();
            }
        });
    }

    private String readFile(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        }
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
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void openFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open HashMap");
        fileChooser.setInitialDirectory(new File("data"));
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("JSON files)", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(app.getStage());
        if (selectedFile != null) {
            String jsonStr = readFile(selectedFile.getAbsolutePath());
            TypeReference<Map<String, Object>> typeRef = new TypeReference<>() {
            };
            nestedMap = new NestedMap<>(new ObjectMapper().readValue(jsonStr, typeRef));
            link = new Link<>();
            updateViews();
            setShortcutHandler();
        }
    }

    private void setNewSelection(ListView<String> view, TextField field, List<String> values) {
        field.setEditable(true);
        view.setItems(
                FXCollections.observableArrayList(
                        values.stream().filter(e -> e.toLowerCase()
                                        .startsWith(field.getText().toLowerCase()))
                                .toList()
                ));
    }

    private void cancelSelection(ListView<String> view, TextField field) {
        field.setText("");
        field.setEditable(false);
        view.setItems(
                FXCollections.observableArrayList());
    }

    private void updateViews() {
        nestedMap.setLink(link);
        for (int i = 0; i < 8; i++) {
            TextField field = indexToFilter.get(i);
            ListView<String> view = indexToView.get(i);
            if (i <= link.size()) {
                setNewSelection(view, field, nestedMap.getKeyTrace().get(i));
            } else {
                cancelSelection(view, field);
            }
        }
    }

    private void reduceLink(int index, String selectionItem) {
        int length = link.size();
        for (int i = index; i < length; i++) {
            link.pollLast();
        }
        link.add(selectionItem);
    }

    public void clickedByView(int index) {
        String item = indexToView.get(index).getSelectionModel().getSelectedItem();
        if (item != null && !item.isEmpty()) {
            reduceLink(index, item);
            updateViews();
        }
    }

    public void clickedByView0() {
        clickedByView(0);
    }

    public void clickedByView1() {
        clickedByView(1);
    }

    public void clickedByView2() {
        clickedByView(2);
    }

    public void clickedByView3() {
        clickedByView(3);
    }

    public void clickedByView4() {
        clickedByView(4);
    }

    public void clickedByView5() {
        clickedByView(5);
    }

    public void clickedByView6() {
        clickedByView(6);
    }

    public void clickedByView7() {
        String item = nestedView7.getSelectionModel().getSelectedItem();
        if (item != null && !item.isEmpty()) {
            if (link.size() == 8)
                link.pollLast();
            link.add(item);
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
            FileOutputStream fout = new FileOutputStream(selectedFile);
            ObjectOutputStream out = new ObjectOutputStream(fout);
            out.writeObject(link);
        }
    }

    public void loadLink() throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load link");
        fileChooser.setInitialDirectory(new File("data"));

        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter(".link", "*.link"),
                new ExtensionFilter("other", "*")
        );
        File selectedFile = fileChooser.showOpenDialog(app.getStage());
        if (selectedFile != null) {
            FileInputStream fin = new FileInputStream(selectedFile);
            ObjectInputStream in = new ObjectInputStream(fin);
            link = (Link<String>) in.readObject();
            updateViews();
            for (int i = 0; i < link.size(); i++) {
                indexToView.get(i).getSelectionModel().select(link.get(i));
            }
        }
    }
}
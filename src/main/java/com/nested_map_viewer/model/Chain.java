package com.nested_map_viewer.model;

import java.io.*;
import java.util.LinkedList;

/**
 * Chain class for selection on the map and save/load operations
 * @author Novoseltcev Stanislav
 * @version 1.0
 */
public class Chain<T> extends LinkedList<T> {
    public static Chain<String> getFromFile(File file) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        return (Chain<String>) in.readObject();
    }

    public void saveToFile(File file) throws IOException{
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(this);
    }
}

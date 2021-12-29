package com.nested_map_viewer.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Utils {
    static public String readFile(String file) throws IOException {
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
}

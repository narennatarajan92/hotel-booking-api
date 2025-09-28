package com.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static final String PROPERTY_FILE = "./src/test/resources/application.properties";
    private static Properties properties;

    public static void PropertyReader() {
        properties = new Properties();
        try (FileInputStream input = new FileInputStream(PROPERTY_FILE)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }


}



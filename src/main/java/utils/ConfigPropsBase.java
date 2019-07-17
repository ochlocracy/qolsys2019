package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropsBase {

    private final String fileName;
    private Properties properties;

    public ConfigPropsBase(String fileName) throws Exception {

        this.fileName = fileName;
        properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream(System.getProperty("user.dir") + "/src/main/resources/" + fileName);
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            throw new Exception("File not found");
        }
    }

    public String getString(String key) throws Exception {
        String property = properties.getProperty(key);
        if (property == null) {
            throw new Exception("Key is not present");
        }
        return property;
    }

}

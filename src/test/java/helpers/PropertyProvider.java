package helpers;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {
    private static PropertyProvider instance;

    @Getter
    private final Properties properties = new Properties();
    public static synchronized PropertyProvider getInstance() {
        if (instance == null) {
            instance = new PropertyProvider();
        }
        return instance;
    }

    private PropertyProvider() {
        try (InputStream propertiesInputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(System.getProperty("config.location.env"))) {
            properties.load(propertiesInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;
    private final String PROPERTY_FILE_PATH = "src/main/resources/config/Configuration.properties";

    public ConfigFileReader() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(PROPERTY_FILE_PATH));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + PROPERTY_FILE_PATH);
        }
    }

    public String getAWSAccessKeyId() {
        String key = properties.getProperty("AWS_ACCESS_KEY_ID");
        if (key != null) return key;
        else throw new RuntimeException("AWS_ACCESS_KEY_ID not specified in the Configuration.properties file");
    }

    public String getAWSSecretAccessKey() {
        String key = properties.getProperty("AWS_SECRET_ACCESS_KEY");
        if (key != null) return key;
        else throw new RuntimeException("AWS_SECRET_ACCESS_KEY not specified in the Configuration.properties file");
    }

    public String getGCPKey() {
        String key = properties.getProperty("GCP_KEY");
        if (key != null) return key;
        else throw new RuntimeException("GCP_KEY not specified in the Configuration.properties file");
    }

}

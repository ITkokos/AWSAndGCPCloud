package utils;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

//@Log4j2
public class ConfigFileReader {

    private Properties properties;
    private static final String PROPERTY_FILE_PATH = "src/main/resources/config/Configuration.properties";
    private static final Logger log = LogManager.getLogger(ConfigFileReader.class);

    public ConfigFileReader() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROPERTY_FILE_PATH))) {
            properties = new Properties();
            properties.load(reader);
        } catch (FileNotFoundException e) {
            log.error("Configuration.properties not found at " + PROPERTY_FILE_PATH, e);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public String getAWSAccessKeyId() {
        String key = properties.getProperty("AWS_ACCESS_KEY_ID");
        if (key == null) log.error("AWS_ACCESS_KEY_ID not specified in the Configuration.properties file");
        return key;
    }

    public String getAWSSecretAccessKey() {
        String key = properties.getProperty("AWS_SECRET_ACCESS_KEY");
        if (key == null) log.error("AWS_SECRET_ACCESS_KEY not specified in the Configuration.properties file");
        return key;
    }

    public String getGCPKey() {
        String key = properties.getProperty("GCP_KEY");
        if (key == null) log.error("GCP_KEY not specified in the Configuration.properties file");
        return key;
    }

}

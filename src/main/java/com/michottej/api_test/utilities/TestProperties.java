package com.michottej.api_test.utilities;

import com.michottej.api_test.exceptions.PropertiesNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestProperties {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PROPERTIES_PATH = "/application%s.properties";

    private static final String ENVIRONMENT = "environment";

    private static Properties properties;

    private TestProperties() {
        throw new IllegalStateException("Utility class");
    }

    public static String getProperty(String propertyName) {
        if (properties == null) {
            load();
        }

        Object value = properties.get(propertyName);
        if (value == null ) {
            throw new PropertiesNotFoundException(String.format("The property '%s' was not found.", propertyName));
        }
        return value.toString();
    }

    private static void load() {
        properties = new Properties();

        String environment = System.getProperty(ENVIRONMENT);
        LOGGER.info("Environment: " + environment);
        String envSuffix = environment != null ? "-".concat(environment) : "";

        String testPropertiesUri = String.format(PROPERTIES_PATH, envSuffix);

        try (InputStream inputStream = TestProperties.class.getResourceAsStream(testPropertiesUri)) {
            if (inputStream == null) {
                throw new PropertiesNotFoundException("Cannot find " + testPropertiesUri);
            }
            properties.load(inputStream);
        } catch (IOException e) {
            LOGGER.error("Could not load properties from resource");
        }
    }
}


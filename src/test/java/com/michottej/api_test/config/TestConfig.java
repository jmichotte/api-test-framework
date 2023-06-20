package com.michottej.api_test.config;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
public class TestConfig {

    public static final String SCHEMA_LOCATION = "schemas/%s";
}

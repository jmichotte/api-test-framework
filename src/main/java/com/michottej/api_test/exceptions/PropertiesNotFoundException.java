package com.michottej.api_test.exceptions;

public class PropertiesNotFoundException extends RuntimeException {

    public PropertiesNotFoundException() {}

    public PropertiesNotFoundException(String message) {
        super(message);
    }
}

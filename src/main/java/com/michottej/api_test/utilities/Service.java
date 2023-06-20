package com.michottej.api_test.utilities;

public enum Service {
    NUMBERS_API("numbers_api"),
    RESTFUL_API("restful_api");

    private final String name;

    Service(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getBaseUri() {
        return TestProperties.getProperty(this.name);
    }
}

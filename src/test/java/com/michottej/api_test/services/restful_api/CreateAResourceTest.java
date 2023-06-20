package com.michottej.api_test.services.restful_api;

import com.michottej.api_test.config.TestConfig;
import com.michottej.api_test.Reporting;
import com.michottej.api_test.test_data.TestDataConstants;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.michottej.api_test.services.restful_api.PropertyNames.*;
import static org.apache.http.HttpStatus.SC_OK;

@Epic(Reporting.RESTFUL_API)
@Feature(Reporting.CREATE_RESTFUL_RESOURCES)
public class CreateAResourceTest extends TestConfig {

    private Map<String, Object> requestBody;
    private String resourceId;

    @BeforeEach
    public void setup() {
        Map<String, Object> resourceData = new HashMap<>();
        resourceData.put(DESCRIPTION, TestDataConstants.RESOURCE_DESCRIPTION);
        resourceData.put(ENABLED, TestDataConstants.RESOURCE_ENABLED);

        requestBody = new HashMap<>();
        requestBody.put(NAME, TestDataConstants.RESOURCE_NAME);
        requestBody.put(DATA, resourceData);
    }

    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void shouldSuccessfullyCreateAResource() {
        Response response = RestfulApi.createAResource(requestBody);

        response.then()
                .statusCode(SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(String.format(SCHEMA_LOCATION, "create_restful_resource.json")));

        resourceId = response.then().extract().body().path(ID);
    }

    @AfterEach
    public void teardown() {
        RestfulApi.deleteASpecificResource(resourceId);
    }
}

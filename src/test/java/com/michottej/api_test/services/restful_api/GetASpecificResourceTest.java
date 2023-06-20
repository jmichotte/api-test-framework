package com.michottej.api_test.services.restful_api;

import com.michottej.api_test.config.TestConfig;
import com.michottej.api_test.test_data.RestfulResource;
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
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

@Epic(Reporting.RESTFUL_API)
@Feature(Reporting.GET_RESTFUL_RESOURCES)
public class GetASpecificResourceTest extends TestConfig {

    @Autowired
    RestfulResource restfulResource;

    @BeforeEach
    public void setup() {
        restfulResource.create();
    }

    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void shouldReturnASpecificResource() {
        Response response = RestfulApi.getASpecificResource(restfulResource.getId());

        response.then()
                .statusCode(SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(String.format(SCHEMA_LOCATION, "get_restful_resource.json")))
                .body(PropertyNames.NAME, equalTo(restfulResource.getName()))
                .body(PropertyNames.DATA+"."+ PropertyNames.DESCRIPTION, equalTo(restfulResource.getDescription()))
                .body(PropertyNames.DATA+"."+ PropertyNames.ENABLED, equalTo(restfulResource.getEnabled()));
    }

    @Test
    public void shouldReturnNotFoundWhenResourceDoesNotExist() {
        Response response = RestfulApi.getASpecificResource(TestDataConstants.INVALID_ID);

        response.then()
                .statusCode(SC_NOT_FOUND);
    }

    @AfterEach
    public void teardown() {
        restfulResource.delete();
    }
}

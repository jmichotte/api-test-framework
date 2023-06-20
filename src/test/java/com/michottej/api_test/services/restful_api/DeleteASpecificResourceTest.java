package com.michottej.api_test.services.restful_api;

import com.michottej.api_test.config.TestConfig;
import com.michottej.api_test.test_data.RestfulResource;
import com.michottej.api_test.Reporting;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

@Epic(Reporting.RESTFUL_API)
@Feature(Reporting.DELETE_RESTFUL_RESOURCES)
public class DeleteASpecificResourceTest extends TestConfig {

    @Autowired
    RestfulResource restfulResource;

    @BeforeEach
    public void setup() {
        restfulResource.create();
    }

    @Test
    public void shouldSuccessfullyDeleteAResource() {
        Response response = RestfulApi.deleteASpecificResource(restfulResource.getId());

        response.then()
                .statusCode(SC_OK)
                .body("message", equalTo(String.format("Object with id = %s has been deleted.", restfulResource.getId())));
    }
}

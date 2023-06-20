package com.michottej.api_test.services.numbers_api;


import com.michottej.api_test.config.TestConfig;
import com.michottej.api_test.Reporting;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;
import static com.michottej.api_test.services.numbers_api.PropertyNames.TYPE;

@Epic(Reporting.NUMBERS_API)
@Feature(Reporting.RANDOM_FACTS)
public class GetRandomFactsTest extends TestConfig {

    @Severity(SeverityLevel.BLOCKER)
    @Test
    public void shouldSuccessfullyReturnARandomMathFact() {
        Response response = NumbersApi.getARandomMathFact();

        response.then()
                .statusCode(SC_OK)
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(String.format(SCHEMA_LOCATION, "get_random_math_fact.json")))
                .body(TYPE, equalTo("math"));
    }

}

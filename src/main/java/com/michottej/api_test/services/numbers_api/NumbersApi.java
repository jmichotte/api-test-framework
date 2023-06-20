package com.michottej.api_test.services.numbers_api;

import com.michottej.api_test.services.Api;
import com.michottej.api_test.utilities.Service;
import com.michottej.api_test.utilities.Request;
import io.restassured.response.Response;

public class NumbersApi extends Api {

    private NumbersApi()  {
        throw new IllegalStateException("Utility class");
    }

    public static Response getARandomMathFact() {
        String requestUri = Request.formatUri(Service.NUMBERS_API, Endpoints.RANDOM, Endpoints.MATH);

        return get(requestUri);
    }
}

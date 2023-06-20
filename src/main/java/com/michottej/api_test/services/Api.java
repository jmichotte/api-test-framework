package com.michottej.api_test.services;

import com.michottej.api_test.utilities.Request;
import io.restassured.response.Response;

import java.util.Map;

public class Api {

    public static Response get(String requestUri) {
        Request request = new Request.RequestBuilder(requestUri)
                .build();

        return request.get();
    }

    public static Response post(String requestUri, Map<String, ?> requestBody) {
        Request request = new Request.RequestBuilder(requestUri)
                .withBody(requestBody)
                .build();

        return request.post();
    }

    public static Response patch(String requestUri, Map<String, ?> requestBody) {
        Request request = new Request.RequestBuilder(requestUri)
                .withBody(requestBody)
                .build();

        return request.patch();
    }

    public static Response delete(String requestUri) {
        Request request = new Request.RequestBuilder(requestUri)
                .build();

        return request.delete();
    }
}

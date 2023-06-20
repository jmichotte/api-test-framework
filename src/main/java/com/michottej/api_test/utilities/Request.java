package com.michottej.api_test.utilities;

import com.michottej.api_test.exceptions.InvalidRequestException;
import io.restassured.RestAssured;
import io.restassured.builder.MultiPartSpecBuilder;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.MultiPartSpecification;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpHeaders.AUTHORIZATION;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;

public class Request {
    private final String requestUri;
    private final Headers headers;
    private final Object body;
    private final Map<String, ?> params;
    private final MultiPartSpecification multipart;

    private final RequestSpecification requestSpecification;

    private Request(RequestBuilder builder) {
        this.requestUri = builder.requestUri;
        this.body = builder.body;
        this.params = builder.params;
        this.headers = builder.headers;
        this.multipart = builder.multipart;
        this.requestSpecification = buildRequestSpecification();
    }

    public static String formatUri(Service service, String... endpoints) {
        StringBuilder uri = new StringBuilder();

        uri.append(service.getBaseUri());

        for (String endpoint : endpoints) {
            if (endpoint.startsWith("/")) {
                uri.append(endpoint);
            } else {
                uri.append("/").append(endpoint);
            }
        }
        return uri.toString();
    }

    public static class RequestBuilder {
        private final String requestUri;
        private Headers headers;
        private Object body;
        private Map<String, ?> params;
        private MultiPartSpecification multipart;

        public RequestBuilder(String requestUri) {
            this.requestUri = requestUri;
            this.headers = defaultHeaders();
            this.params = new HashMap<>();
        }

        public RequestBuilder withBody(Object body) {
            this.body = body;
            return this;
        }

        public RequestBuilder withParams(Map<String, ?> params) {
            this.params = params;
            return this;
        }

        public RequestBuilder withFile(String fileLocation) {
            String fileName = fileLocation.substring(fileLocation.lastIndexOf("/")+1);
            this.multipart =
                new MultiPartSpecBuilder(
                    new File(fileLocation))
                        .fileName(fileName)
                        .build();
            return this;
        }

        public RequestBuilder withAuthKey(String authKey) {
            Header authorizationHeader = new Header(AUTHORIZATION, authKey);
            List<Header> headerList = new ArrayList<>(this.headers.asList());
            headerList.add(authorizationHeader);
            this.headers = new Headers(headerList);
            return this;
        }

        public RequestBuilder withContentType(String contentType) {
            Header contentTypeHeader = new Header(CONTENT_TYPE, contentType);
            List<Header> headerList = new ArrayList<>(this.headers.asList());
            headerList.add(contentTypeHeader);
            this.headers = new Headers(headerList);
            return this;
        }

        private Headers defaultHeaders() {
            Header contentType = new Header(CONTENT_TYPE,"application/json");
            List<Header> headerList = new ArrayList<>();
            headerList.add(contentType);
            return new Headers(headerList);
        }

        public Request build() {
            return new Request(this);
        }
    }

    private RequestSpecification buildRequestSpecification() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri(this.requestUri);

        return builder.build();
    }

    public Response get() {
        return given()
                .spec(this.requestSpecification)
                .headers(this.headers)
                .params(this.params)
                .when()
                .get();
    }

    public Response post() {
        if (this.body == null && this.multipart == null ) {
            throw new InvalidRequestException("This POST request is invalid. Please include a request body or multipart form data");
        }

        RequestSpecification request = given()
            .spec(this.requestSpecification)
            .headers(this.headers);

        if (this.body != null) {
            request.body(this.body);
        }

        if (this.multipart != null) {
            request.multiPart(this.multipart);
        }

        return request
            .when()
            .post();
    }

    public Response patch() {
        return given()
                .spec(this.requestSpecification)
                .headers(this.headers)
                .body(this.body)
                .when()
                .patch();
    }

    public Response delete() {
        return given()
                .spec(this.requestSpecification)
                .headers(this.headers)
                .when()
                .delete();
    }
}

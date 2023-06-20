package com.michottej.api_test.services.restful_api;

import com.michottej.api_test.services.Api;
import com.michottej.api_test.utilities.Service;
import com.michottej.api_test.utilities.Request;
import io.restassured.response.Response;

import java.util.Map;


public class RestfulApi extends Api {

    private RestfulApi() {
        throw new IllegalStateException("Utility class");
    }

    public static Response getAllResources() {
        String requestUri = Request.formatUri(Service.RESTFUL_API, Endpoints.OBJECTS);

        return get(requestUri);
    }

    public static Response createAResource(Map<String, Object> requestBody) {
        String requestUri = Request.formatUri(Service.RESTFUL_API, Endpoints.OBJECTS);

        return post(requestUri, requestBody);
    }

    public static Response getASpecificResource(String resourceId) {
        String requestUri = Request.formatUri(Service.RESTFUL_API, Endpoints.OBJECTS, resourceId);

        return get(requestUri);
    }

    public static Response updateASpecificResource(String resourceId, Map<String, Object> resourceData) {
        String requestUri = Request.formatUri(Service.RESTFUL_API, Endpoints.OBJECTS, resourceId);

        return patch(requestUri, resourceData);
    }

    public static Response deleteASpecificResource(String resourceId) {
        String requestUri = Request.formatUri(Service.RESTFUL_API, Endpoints.OBJECTS, resourceId);

        return delete(requestUri);
    }
}

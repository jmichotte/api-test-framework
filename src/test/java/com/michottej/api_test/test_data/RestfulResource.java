package com.michottej.api_test.test_data;

import com.michottej.api_test.exceptions.InvalidRequestException;
import com.michottej.api_test.services.restful_api.PropertyNames;
import io.restassured.response.Response;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.michottej.api_test.services.restful_api.RestfulApi;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("prototype")
public class RestfulResource {

    private String id;
    private String name;
    private String description;
    private Boolean enabled;

    public RestfulResource() {
        this.name = TestDataConstants.RESOURCE_NAME;
        this.description = TestDataConstants.RESOURCE_DESCRIPTION;
        this.enabled = TestDataConstants.RESOURCE_ENABLED;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void create() {
        Map<String, Object> resourceData = new HashMap<>();
        resourceData.put(PropertyNames.DESCRIPTION, description);
        resourceData.put(PropertyNames.ENABLED, enabled);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put(PropertyNames.NAME, name);
        requestBody.put(PropertyNames.DATA, resourceData);

        Response response = RestfulApi.createAResource(requestBody);

        this.id = response.then().extract().body().path(PropertyNames.ID);
    }

    public void delete() {
        if (this.id == null) {
            throw new InvalidRequestException("The Restful resource does not exist yet. Please use create() to create it.");
        }
        RestfulApi.deleteASpecificResource(this.id);
    }
}

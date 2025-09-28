package com.utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TestContext {
    public RequestSpecification requestSpec;
    public RequestSpecification postPutRequestSpec;
    public Response response;
    public Map<String, String> session = new HashMap<>();
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public void setSessionContext(String key, String value) {
        session.put(key, value);
    }
    public String getSessionContext(String key) {
        return Optional.ofNullable((String) session.get(key))
                .orElseThrow(() -> new NoSuchElementException("Key '" + key + "' not found in TestContext!"));
    }

}

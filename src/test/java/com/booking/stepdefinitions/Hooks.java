package com.booking.stepdefinitions;

import com.pojo.AuthReq;
import com.utils.*;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.File;

import static io.restassured.RestAssured.given;

public class Hooks {
    private TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @BeforeAll
    public static void before_or_after_all() {
        PropertyReader.PropertyReader();
        new File("target/logs").mkdirs();
        ReportRenamer.init();
    }

    @Before(value = "not @noAuth", order = 0)
    public void beforeScenario(Scenario scenario) {
        RestAssured.reset();
        RestAssured.filters(new JsonLoggingFilter());
        AuthReq authReq = new AuthReq();
        authReq.setUsername(PropertyReader.getValue("user.username"));
        authReq.setPassword(PropertyReader.getValue("user.password"));

        Resources resource = Resources.valueOf("GetAuthTokenAPI");

        RequestSpecification request = new RequestSpecBuilder().setBaseUri(PropertyReader.getValue("application.baseURI")).
                setContentType(ContentType.JSON).build();
        RequestSpecification res = given().spec(request).body(authReq);
        ResponseSpecification responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).expectContentType(ContentType.JSON).build();
        Response response = res.when().post(resource.getResource())
                .then().spec(responseSpecification).extract().response();

        String token = response.path("token");
        context.setSessionContext("token", token);
    }

    @Before(order = 1)
    public void setup() {
        RestAssured.reset();
        RestAssured.filters(new JsonLoggingFilter());
        RequestSpecification request = new RequestSpecBuilder()
                .setBaseUri(PropertyReader.getValue("application.baseURI"))
                .setContentType(ContentType.JSON)
                .setAccept(PropertyReader.getValue("accept"))
                .build();

        context.requestSpec = given().spec(request);
        context.postPutRequestSpec = given().spec(request);
    }

}

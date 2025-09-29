package com.booking.stepdefinitions;

import com.booking.model.Booking;
import com.utils.BookingFactory;
import com.utils.TestContext;
import com.utils.Resources;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class BookingStepDefinitions {
    private final TestContext context;
    private Booking requestBody;
    private Response response;

    public BookingStepDefinitions(TestContext context) { this.context = context; }

    private static int newRoomId() {
        return 3000 + (int)(Math.random() * 1000);
    }

    @Given("User wants to do a booking with below booking details")
    public void prepareBooking(DataTable bookingDetails) {
        for (Map<String,String> row : bookingDetails.asMaps(String.class,String.class)) {
            int roomId = newRoomId();
            context.setSessionContext("roomid", String.valueOf(roomId));
            requestBody = com.utils.BookingFactory.fromMap(row, roomId);
            row.forEach((k,v) -> context.setSessionContext(k, v));
        }
    }

    @Given("User wants to do a booking with below booking details to check error responses")
    public void prepareBookingForError(DataTable bookingDetails) {
        for (Map<String,String> row : bookingDetails.asMaps(String.class,String.class)) {
            row.forEach((k,v) -> context.setSessionContext(k, v));
            int roomId = Integer.parseInt(context.getSessionContext("roomid"));
            requestBody = BookingFactory.fromMap(row, roomId);
        }
    }

    @When("User sends a POST request to {string} with the booking details")
    public void sendPost(String endpoint) {
        Resources res = Resources.valueOf(endpoint);
        // Print the request body for debugging using Jackson
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(requestBody);
            System.out.println("Request Body: " + json);
        } catch (Exception e) {
            System.out.println("Failed to serialize request body: " + e.getMessage());
        }
        response = given()
                .spec(context.postPutRequestSpec)
                .body(requestBody)
                .when()
                .post(res.getResource());
        context.setResponse(response);
    }

    @Then("the response status code should be {int}")
    public void verifyStatus(int statusCode) {
        System.out.println(response);
        Assertions.assertEquals(statusCode, response.getStatusCode(), "Unexpected status code");
    }

    @Then("the response should match the {string} json schema")
    public void verifySchema(String schemaFile) {
        response.then().assertThat().body(io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath("schemas/" + schemaFile));
    }

    @And("the response should contain the created booking details")
    public void verifyContents() {
        System.out.println( response);
        Assertions.assertEquals(context.getSessionContext("firstname"), response.jsonPath().getString("firstname"));
        Assertions.assertEquals(context.getSessionContext("lastname"), response.jsonPath().getString("lastname"));
        String deposit = context.getSessionContext("depositpaid");
        if (deposit != null) {
            Assertions.assertEquals(Boolean.parseBoolean(deposit), response.jsonPath().getBoolean("depositpaid"));
        }
        Assertions.assertEquals(context.getSessionContext("checkin"), response.jsonPath().getString("bookingdates.checkin"));
        Assertions.assertEquals(context.getSessionContext("checkout"), response.jsonPath().getString("bookingdates.checkout"));
//        Assertions.assertEquals(context.getSessionContext("email"), response.jsonPath().getString("email"));
//        Assertions.assertEquals(context.getSessionContext("phone"), response.jsonPath().getString("phone"));
    }
}
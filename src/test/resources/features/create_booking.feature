@booking_regression @create_booking @noAuth
Feature: Create Booking

  Scenario: User should be able to create booking with valid details
    Given User wants to do a booking with below booking details
      | firstname   | lastname    | depositpaid | email            | phone         | checkin    | checkout   |
      | TestFName   | TestLName   | true        | michael@test.com | 8424454515455 | 2025-09-27 | 2025-9-30  |
    When User sends a POST request to "CreateBookingAPI" with the booking details
    Then the response status code should be 200
    And the response should match the "CreateUpdateBookingSchema.json" json schema
    And the response should contain the created booking details

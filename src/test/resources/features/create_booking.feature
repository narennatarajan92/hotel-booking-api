@booking_regression @create_booking @noAuth
Feature: Create Booking

  Scenario: User should be able to create booking with valid details
    Given User wants to do a booking with below booking details
      | firstname   | lastname    | depositpaid | email            | phone         | checkin    | checkout   |
      | TestFName   | TestLName   | true        | michael@test.com | 8424454515455 | 2025-09-27 | 2025-09-30 |
    When User sends a POST request to "CreateBookingAPI" with the booking details
    Then the response status code should be 201
    And the response should match the "CreateUpdateBookingSchema.json" json schema
    And the response should contain the created booking details

  @negative @validation
  Scenario Outline: Create booking with invalid or missing fields returns 4xx
    Given User wants to do a booking with below booking details
      | firstname   | lastname    | depositpaid | email            | phone         | checkin    | checkout   |
      | <firstname> | <lastname>  | <deposit>   | <email>          | <phone>       | <checkin>  | <checkout> |
    When User sends a POST request to "CreateBookingAPI" with the booking details
    Then the response status code should be <status>
    And the response body should contain "<expectedField>"

    Examples:
    # missing / blank fields
      | firstname                               | lastname                                           | deposit | email            | phone      | checkin    | checkout   | status | expectedField         |
      |                                         | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | firstname             |
      | TestFName                               |                                                    | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | lastname              |
      | NULL                                    | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | firstname             |
      | TestFName                               | NULL                                               | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | lastname              |
    # length violations for firstname / lastname
      | ab                                      | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | firstname             |
      | aVeryLongFirstNameThatExceedsMaximumLen | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | firstname             |
      | TestFName                               | ab                                                 | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | lastname              |
      | TestFName                               | aVeryLongLastNameThatWillExceedThirtyCharactersXYZ | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | lastname              |
    # boolean / type violations
      | TestFName                               | TestLName                                          | yes     | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | depositpaid           |
      | TestFName                               | TestLName                                          | 123     | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | depositpaid           |
    # email format
      | TestFName                               | TestLName                                          | true    | not-an-email     | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | email                 |
      | TestFName                               | TestLName                                          | true    | test@.com        | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | email                 |

    # date format and ordering violations
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-26 | 400    | bookingdates          |
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 27-09-2025 | 30-09-2025 | 400    | bookingdates          |
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025/09/27 | 2025/09/30 | 400    | bookingdates          |
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 0000-00-00 | 0000-00-00 | 400    | bookingdates          |
    # roomid / bookingid constraints (use table-driven approach; if your Given auto-generates ids mark roomid/bookingid via special token ROW to assert server-side validation)
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | roomid                |
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | bookingid             |
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | roomid (zero)         |
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | roomid (neg)          |
    # bookingdates individual fields missing or invalid
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | bookingdates.checkin  |
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | bookingdates.checkout |
    # extra / unexpected fields
      | TestFName                               | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | unexpectedField       |
    # invalid values firstname
      | テスト                                     | TestLName                                          | true    | michael@test.com | 9999999999 | 2025-09-27 | 2025-09-30 | 400    | firstname             |
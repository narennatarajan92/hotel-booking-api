package com.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Resources {
    GetAuthTokenAPI("/api/auth/login"),
    ValidateAuthTokenAPI("/api/auth/validate"),
    LogoutAuthTokenAPI("/api/auth/logout"),
    CreateBookingAPI("/api/booking"),
    GetBookingByIDAPI("/api/booking/{id}"),
    GetBookingsByRoomIDAPI("/api/booking"),
    UpdateBookingAPI("/api/booking/{id}"),
    DeleteBookingAPI("/api/booking/{id}"),
    GetBookingSummaryAPI("/api/booking/summary"),
    GetBookingUnavailableAPI("/api/booking/unavailable");

    private String resource;

    Resources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
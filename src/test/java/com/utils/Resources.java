package com.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Resources {
    GetAuthTokenAPI("/api/auth/login"),
    ValidateAuthTokenAPI("/api/auth/validate"),
    LogoutAuthTokenAPI("/api/auth/logout"),
    CreateBookingAPI("/api/booking");

    private String resource;

    Resources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}

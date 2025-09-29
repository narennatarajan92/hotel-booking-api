package com.utils;

import com.booking.model.Booking;
import com.booking.model.BookingDates;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class BookingFactory {
    private BookingFactory() {}

    public static Booking fromMap(Map<String,String> row, int roomId) {
        Booking b = new Booking();
        b.setBookingid(randomId());
        b.setRoomid(roomId);
        b.setFirstname(defaultIfNull(row.get("firstname")));
        b.setLastname(defaultIfNull(row.get("lastname")));
        b.setDepositpaid(parseBoolean(row.get("depositpaid")));
//        b.setEmail(defaultIfNull(row.get("email")));
//        b.setPhone(defaultIfNull(row.get("phone")));
        b.setBookingdates(new BookingDates(defaultIfNull(row.get("checkin")), defaultIfNull(row.get("checkout"))));
        return b;
    }

    private static int randomId() {
        return 3000 + ThreadLocalRandom.current().nextInt(0, 1000);
    }

    private static String defaultIfNull(String v) { return v == null ? "" : v; }
    private static Boolean parseBoolean(String v) { return v == null ? null : Boolean.valueOf(v); }
}
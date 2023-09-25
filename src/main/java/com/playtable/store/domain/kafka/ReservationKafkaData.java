package com.playtable.store.domain.kafka;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record ReservationKafkaData(
        UUID storeId,
        @DateTimeFormat(pattern = "yyyy-mm-dd")
        LocalDate reservationDay,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime reservationTime,
        Integer totalPeople,
//        ReservationStatus status,
        String customerId,
        String customerName
) {
}

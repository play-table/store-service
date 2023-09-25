package com.playtable.store.domain.kafka;

import java.util.UUID;

public record ReservationOpenKafkaData(
        UUID storeId,
        Integer seats
) {
    public static ReservationOpenKafkaData of(UUID storeId, Integer seats){
        return new ReservationOpenKafkaData(storeId, seats);
    }
}

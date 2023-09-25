package com.playtable.store.domain.kafka;

import com.playtable.store.domain.entity.Category;
import com.playtable.store.domain.entity.RestDay;
import com.playtable.store.domain.entity.Store;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record StoreUpdateKafkaData(
        UUID storeId,
        String name,
        String contact,
        String address,
        Category category,
        String imageUrl,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime openTime,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime closeTime,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime breakStartTime,
        @DateTimeFormat(pattern = "HH:mm")
        LocalTime breakEndTime,
        List<String> days
) {
    public static StoreUpdateKafkaData of(Store store, List<RestDay> restDays) {
        return new StoreUpdateKafkaData(
                store.getId(),
                store.getName(),
                store.getContact(),
                store.getAddress(),
                store.getCategory(),
                store.getImageUrl(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getBreakStartTime(),
                store.getBreakEndTime(),
                restDays.stream()
                        .map(RestDay::getDayOfWeek)
                        .map(DayOfWeek::name)
                        .toList()
        );
    }
}

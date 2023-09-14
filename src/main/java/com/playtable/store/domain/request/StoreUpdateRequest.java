package com.playtable.store.domain.request;

import java.time.LocalTime;
import java.util.List;

public record StoreUpdateRequest(
        String name,
        String contact,
        String address,
        String category,
        String imageUrl,
        LocalTime openTime,
        LocalTime closeTime,
        LocalTime breakStartTime,
        LocalTime breakEndTime,
        List<String> days
) {
}

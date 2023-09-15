package com.playtable.store.domain.request;

import com.playtable.store.domain.entity.Category;
import com.playtable.store.domain.entity.Store;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record StoreRequest(
        String name,
        String contact,
        String address,
        String category,
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
    public Store toEntity(UUID ownerId){
        return Store.builder()
                .ownerId(ownerId)
                .name(name)
                .contact(contact)
                .address(address)
                .reviewCount(0)
                .totalRating(0L)
                .isReservationAble(false)
                .isWaitingAble(false)
                .category(Category.valueOf(category))
                .imageUrl(imageUrl)
                .openTime(openTime)
                .closeTime(closeTime)
                .breakStartTime(breakStartTime)
                .breakEndTime(breakEndTime)
                .build();
    }
}

package com.playtable.store.domain.dto;

import com.playtable.store.domain.entity.Category;
import com.playtable.store.domain.entity.Store;

import java.util.UUID;

public record StoreDto(
        UUID id,
        String name,
        String address,
        Category category,
        Integer reviewCount,
        Long totalRating
) {
    public static StoreDto from(Store store){
        return new StoreDto(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getCategory(),
                store.getReviewCount(),
                store.getTotalRating()
        );
    }
}

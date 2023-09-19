package com.playtable.store.domain.response;

import com.playtable.store.domain.entity.Category;
import com.playtable.store.domain.entity.Store;

import java.util.UUID;

public record StoreSummaryResponse(
        UUID id,
        String name,
        String address,
        Category category,
        String imageUrl,
        Integer reviewCount,
        Long totalRating
) {
    public static StoreSummaryResponse from(Store store){
        return new StoreSummaryResponse(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getCategory(),
                store.getImageUrl(),
                store.getReviewCount(),
                store.getTotalRating()
        );
    }
}

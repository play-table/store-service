package com.playtable.store.domain.response;

import com.playtable.store.domain.entity.Category;
import com.playtable.store.domain.entity.Store;

import java.util.UUID;

public record WaitingTopStoreResponse(
        UUID id,
        String name,
        String address,
        Category category,
        Integer reviewCount,
        Long totalRating,
        Integer todayWaitingCount
) {
    public static WaitingTopStoreResponse from(Store store, Integer todayWaitingCount){
        return new WaitingTopStoreResponse(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getCategory(),
                store.getReviewCount(),
                store.getTotalRating(),
                todayWaitingCount
        );
    }

}

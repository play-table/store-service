package com.playtable.store.domain.response;

import com.playtable.store.domain.dto.MenuDto;
import com.playtable.store.domain.dto.RestDayDto;
import com.playtable.store.domain.entity.Category;
import com.playtable.store.domain.entity.Store;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record StoreDetailResponse(
        UUID id,
        String name,
        String contact,
        String address,
        Category category,
        String imageUrl,
        Integer reviewCount,
        Long totalRating,
        LocalTime openTime,
        LocalTime closeTime,
        LocalTime breakStartTime,
        LocalTime breakEndTime,
        List<MenuDto> menus,
        List<RestDayDto> restDays,
        Boolean isWaitingAble,
        Boolean isReservationAble

) {
    public static StoreDetailResponse from(Store store) {
        return new StoreDetailResponse(
                store.getId(),
                store.getName(),
                store.getContact(),
                store.getAddress(),
                store.getCategory(),
                store.getImageUrl(),
                store.getReviewCount(),
                store.getTotalRating(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getBreakStartTime(),
                store.getBreakEndTime(),
                store.getMenus().stream().map(MenuDto::from).toList(),
                store.getRestDays().stream().map(RestDayDto::from).toList(),
                store.getIsWaitingAble(),
                store.getIsReservationAble()
        );
    }
}

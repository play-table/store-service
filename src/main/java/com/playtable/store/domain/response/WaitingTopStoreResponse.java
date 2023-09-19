package com.playtable.store.domain.response;

import com.playtable.store.domain.dto.StoreDto;
import com.playtable.store.domain.entity.DailyReservation;

public record WaitingTopStoreResponse(
        StoreDto store,
        Integer todayWaitingCount
) {
    public static WaitingTopStoreResponse from(DailyReservation dailyReservation){
        return new WaitingTopStoreResponse(
                StoreDto.from(dailyReservation.getStore()),
                dailyReservation.getTodayWaitingCount()
        );
    }
}

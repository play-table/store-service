package com.playtable.store.domain.dto;

import com.playtable.store.domain.entity.RestDay;

public record RestDayDto(
        String day
) {
    public static RestDayDto from(RestDay restDay) {
        return new RestDayDto(restDay.getDayOfWeek().name());
    }
}

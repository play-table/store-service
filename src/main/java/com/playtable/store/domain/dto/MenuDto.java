package com.playtable.store.domain.dto;

import com.playtable.store.domain.entity.Menu;

public record MenuDto(
        String name,
        Integer price
) {
    public static MenuDto from(Menu menu){
        return new MenuDto(
                menu.getName(),
                menu.getPrice()
        );
    }
}

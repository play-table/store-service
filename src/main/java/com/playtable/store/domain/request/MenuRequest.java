package com.playtable.store.domain.request;

import com.playtable.store.domain.entity.Menu;
import com.playtable.store.domain.entity.Store;

import java.util.UUID;

public record MenuRequest(
        String name, 
        Integer price
) {
    public Menu toEntity(UUID storeId){
        return Menu.builder()
                .name(name)
                .price(price)
                .store(Store.builder().id(storeId).build())
                .build();
    }
}

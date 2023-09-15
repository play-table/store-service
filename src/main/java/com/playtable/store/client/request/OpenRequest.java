package com.playtable.store.client.request;

public record OpenRequest(
        Integer seats
) {
    public static OpenRequest of(Integer seats){
        return new OpenRequest(seats);
    }
}

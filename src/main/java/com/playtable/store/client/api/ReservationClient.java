package com.playtable.store.client.api;

import com.playtable.store.client.request.OpenRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("RESERVATION-SERVICE")
public interface ReservationClient {

    @PostMapping("/api/v1/reservation/{storeId}/open")
    void reservationOpen(
            @PathVariable("storeId") String storeId,
            @RequestBody OpenRequest request
    );
}

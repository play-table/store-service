package com.playtable.store.controller;

import com.playtable.store.config.MemberTokenInfo;
import com.playtable.store.domain.request.*;
import com.playtable.store.domain.response.StoreDetailResponse;
import com.playtable.store.domain.response.StoreSummaryResponse;
import com.playtable.store.domain.response.WaitingTopStoreResponse;
import com.playtable.store.service.StoreCommandService;
import com.playtable.store.service.StoreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreCommandController {

    private final StoreCommandService storeCommandService;
    @PostMapping("/{storeId}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public void menuRegister(
            @PathVariable("storeId") UUID storeId,
            @RequestBody MenuRequest menuRequest
    ){
        storeCommandService.menuRegister(storeId, menuRequest);
    }

    @PutMapping("/{storeId}/reservation/open")
    public void reservationOpen(
            @PathVariable("storeId") UUID storeId,
            @RequestBody ReservationRequest reservationRequest
    ){
        storeCommandService.reservationOpen(storeId, reservationRequest);
    }

    @PutMapping("/{storeId}/waiting/open")
    public void waitingOpen(
            @PathVariable("storeId") UUID storeId
    ){
        storeCommandService.waitingOpen(storeId);
    }

    @PutMapping("/{storeId}/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public void increaseReservation(@PathVariable("storeId") UUID storeId){
//        storeCommandService.increaseReservation(storeId);
    }

    @PutMapping("/{storeId}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void reviewStatistics(
            @PathVariable("storeId") UUID storeId,
            @RequestBody ReviewStatisticsRequest reviewStatisticsRequest
    ){
//        storeCommandService.reviewStatistics(storeId, reviewStatisticsRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @AuthenticationPrincipal MemberTokenInfo memberTokenInfo,
            @RequestBody StoreRequest storeRequest
    ){
        storeCommandService.register(memberTokenInfo.getId(), storeRequest);
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable("storeId") UUID storeId,
            @RequestBody StoreUpdateRequest storeUpdateRequest
    ){
        storeCommandService.update(storeId, storeUpdateRequest);
    }
}

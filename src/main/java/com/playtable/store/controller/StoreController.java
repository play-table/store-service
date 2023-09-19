package com.playtable.store.controller;

import com.playtable.store.config.MemberTokenInfo;
import com.playtable.store.domain.entity.Store;
import com.playtable.store.domain.request.*;
import com.playtable.store.domain.response.StoreDetailResponse;
import com.playtable.store.domain.response.WaitingTopStoreResponse;
import com.playtable.store.domain.response.StoreSummaryResponse;
import com.playtable.store.service.StoreService;
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
public class StoreController {

    private final StoreService storeService;

    @GetMapping("/my")
    public List<StoreDetailResponse> getMyStore(@AuthenticationPrincipal MemberTokenInfo memberTokenInfo){
        return storeService.getMyStore(memberTokenInfo);
    }

    @PostMapping("/{storeId}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public void menuRegister(
            @PathVariable("storeId") UUID storeId,
            @RequestBody MenuRequest menuRequest
    ){
        storeService.menuRegister(storeId, menuRequest);
    }

    @PutMapping("/{storeId}/reservation/open")
    public void reservationOpen(
            @PathVariable("storeId") UUID storeId,
            @RequestBody ReservationRequest reservationRequest
    ){
        storeService.reservationOpen(storeId, reservationRequest);
    }

    @PutMapping("/{storeId}/waiting/open")
    public void waitingOpen(
            @PathVariable("storeId") UUID storeId
    ){
        storeService.waitingOpen(storeId);
    }

    @GetMapping
    public List<StoreSummaryResponse> getByName(@RequestParam("name") String name){
        return storeService.getByName(name);
    }
    @GetMapping("/{storeId}")
    public StoreDetailResponse getById(@PathVariable("storeId") UUID storeId){
        return storeService.getById(storeId);
    }

    @GetMapping("/waiting-top")
    public List<WaitingTopStoreResponse> getTodayWaitingTopStore(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate date
    ){
        return storeService.getWaitingTopStoreDate(date);
    }

    @PutMapping("/{storeId}/reservation")
    @ResponseStatus(HttpStatus.CREATED)
    public void increaseReservation(@PathVariable("storeId") UUID storeId){
        storeService.increaseReservation(storeId);
    }

    @PutMapping("/{storeId}/review")
    @ResponseStatus(HttpStatus.CREATED)
    public void reviewStatistics(
            @PathVariable("storeId") UUID storeId,
            @RequestBody ReviewStatisticsRequest reviewStatisticsRequest
    ){
        storeService.reviewStatistics(storeId, reviewStatisticsRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(
            @AuthenticationPrincipal MemberTokenInfo memberTokenInfo,
            @RequestBody StoreRequest storeRequest
    ){
        storeService.register(memberTokenInfo.getId(), storeRequest);
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable("storeId") UUID storeId,
            @RequestBody StoreUpdateRequest storeUpdateRequest
    ){
        storeService.update(storeId, storeUpdateRequest);
    }
}

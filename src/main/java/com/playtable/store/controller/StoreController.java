package com.playtable.store.controller;

import com.playtable.store.domain.request.MenuRequest;
import com.playtable.store.domain.request.StoreRequest;
import com.playtable.store.domain.request.StoreUpdateRequest;
import com.playtable.store.domain.response.StoreDetailResponse;
import com.playtable.store.domain.response.WaitingTopStoreResponse;
import com.playtable.store.domain.response.StoreSummaryResponse;
import com.playtable.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/{storeId}/menu")
    @ResponseStatus(HttpStatus.CREATED)
    public void menuRegister(
            @PathVariable("storeId") UUID storeId,
            @RequestBody MenuRequest menuRequest
    ){
        storeService.menuRegister(storeId, menuRequest);
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
    public void increaseReservation(@PathVariable("storeId") UUID storeId){
        storeService.increaseReservation(storeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody StoreRequest storeRequest){
        UUID ownerId = UUID.randomUUID();
        storeService.register(ownerId, storeRequest);
    }

    @PutMapping("/{storeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void update(
            @PathVariable("storeId") UUID storeId,
            @RequestBody StoreUpdateRequest storeUpdateRequest
    ){
        UUID ownerId = UUID.randomUUID();
        storeService.update(ownerId, storeId, storeUpdateRequest);
    }
}

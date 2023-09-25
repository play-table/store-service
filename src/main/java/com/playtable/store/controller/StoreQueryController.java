package com.playtable.store.controller;

import com.playtable.store.config.MemberTokenInfo;
import com.playtable.store.domain.request.*;
import com.playtable.store.domain.response.StoreDetailResponse;
import com.playtable.store.domain.response.WaitingTopStoreResponse;
import com.playtable.store.domain.response.StoreSummaryResponse;
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
public class StoreQueryController {

    private final StoreQueryService storeQueryService;

    @GetMapping("/my")
    public List<StoreDetailResponse> getMyStore(@AuthenticationPrincipal MemberTokenInfo memberTokenInfo){
        return storeQueryService.getMyStore(memberTokenInfo);
    }

    @GetMapping
    public List<StoreSummaryResponse> getByName(@RequestParam("name") String name){
        return storeQueryService.getByName(name);
    }
    @GetMapping("/{storeId}")
    public StoreDetailResponse getById(@PathVariable("storeId") UUID storeId){
        return storeQueryService.getById(storeId);
    }

    @GetMapping("/waiting-top")
    public List<WaitingTopStoreResponse> getTodayWaitingTopStore(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd")
            LocalDate date
    ){
        return storeQueryService.getWaitingTopStoreDate(date);
    }

}

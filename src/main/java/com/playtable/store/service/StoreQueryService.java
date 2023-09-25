package com.playtable.store.service;

import com.playtable.store.client.api.ReservationClient;
import com.playtable.store.client.request.OpenRequest;
import com.playtable.store.config.MemberTokenInfo;
import com.playtable.store.domain.entity.DailyReservation;
import com.playtable.store.domain.entity.RestDay;
import com.playtable.store.domain.entity.Store;
import com.playtable.store.domain.request.*;
import com.playtable.store.domain.response.StoreDetailResponse;
import com.playtable.store.domain.response.WaitingTopStoreResponse;
import com.playtable.store.domain.response.StoreSummaryResponse;
import com.playtable.store.repository.DailyReservationRepository;
import com.playtable.store.repository.MenuRepository;
import com.playtable.store.repository.RestDayRepository;
import com.playtable.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreQueryService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final RestDayRepository restDayRepository;
    private final DailyReservationRepository dailyReservationRepository;
    private final ReservationClient reservationClient;

    public StoreDetailResponse getById(UUID storeId){
        Store store = findById(storeId);
        return StoreDetailResponse.from(store);
    }

    public List<StoreSummaryResponse> getByName(String name) {
        return storeRepository
                .findByNameContaining(name)
                .stream()
                .map(StoreSummaryResponse::from)
                .toList();
    }

    public List<WaitingTopStoreResponse> getWaitingTopStoreDate(LocalDate date){
        return dailyReservationRepository
                .findTopTenStoreByReservationDate(
                        date==null ?
                                LocalDate.now() :
                                date)
                .stream()
                .map(WaitingTopStoreResponse::from)
                .toList();
    }

    public void reviewStatistics(
            UUID storeId,
            ReviewStatisticsRequest reviewStatisticsRequest
    ) {
        Store store = findById(storeId);
        store.reviewStatistics(reviewStatisticsRequest.rating());
    }

    private void isValidStoreOwner(Store store, UUID ownerId){
        if(!store.getOwnerId().equals(ownerId)){
            throw new IllegalArgumentException("invalid owner id : " + ownerId);
        }
    }

    public List<StoreDetailResponse> getMyStore(MemberTokenInfo memberTokenInfo) {
        return storeRepository
                .findByOwnerId(memberTokenInfo.getId())
                .stream()
                .map(StoreDetailResponse::from)
                .toList();
    }

    private Store findById(UUID id){
        return storeRepository
                .findByIdFetch(id)
                .orElseThrow(()-> new NoSuchElementException("store not found : " + id));
    }
}

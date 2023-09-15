package com.playtable.store.service;

import com.playtable.store.client.api.ReservationClient;
import com.playtable.store.client.request.OpenRequest;
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
public class StoreService {

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

    public void menuRegister(UUID storeId, MenuRequest menuRequest) {
        menuRepository.save(menuRequest.toEntity(storeId));
    }

    public void register(UUID ownerId, StoreRequest storeRequest){
        Store store = storeRepository.save(storeRequest.toEntity(ownerId));

        List<RestDay> restDays = makeRestDays(storeRequest.days(), store);

        restDayRepository.saveAll(restDays);
    }

    public void update(UUID storeId, StoreUpdateRequest storeUpdateRequest){
        Store store = findById(storeId);

        store.informationUpdate(
                storeUpdateRequest.name(),
                storeUpdateRequest.contact(),
                storeUpdateRequest.address(),
                storeUpdateRequest.category(),
                storeUpdateRequest.imageUrl(),
                storeUpdateRequest.openTime(),
                storeUpdateRequest.closeTime(),
                storeUpdateRequest.breakStartTime(),
                storeUpdateRequest.breakEndTime()
        );

        // query 최적화 숙제
        List<RestDay> beforeRestDays = restDayRepository.findByStore(store);
        restDayRepository.deleteAll(beforeRestDays);

        List<RestDay> restDays = makeRestDays(storeUpdateRequest.days(), store);
        restDayRepository.saveAll(restDays);
    }

    public void increaseReservation(UUID storeId) {

        Store store = findById(storeId);

        DailyReservation dailyReservation = dailyReservationRepository
                .findByStoreAndReservationDate(store, LocalDate.now())
                .orElseGet(
                        () -> dailyReservationRepository.save(
                                DailyReservation.createToday(store))
                );

        dailyReservation.increaseTotalCount();
    }

    public void reviewStatistics(
            UUID storeId,
            ReviewStatisticsRequest reviewStatisticsRequest
    ) {
        Store store = findById(storeId);
        store.reviewStatistics(reviewStatisticsRequest.rating());
    }
    public void reservationOpen(UUID storeId, ReservationRequest reservationRequest) {
        Store store = findById(storeId);
        store.reservationOpen();
        reservationClient.reservationOpen(
                storeId.toString(),
                OpenRequest.of(reservationRequest.seats())
        );
    }

    public void waitingOpen(UUID storeId) {
        Store store = findById(storeId);
        store.waitingOpen();
    }

    private List<RestDay> makeRestDays(List<String> days, Store store) {
        return days.stream()
                .map(day -> RestDay.builder()
                        .store(store)
                        .dayOfWeek(DayOfWeek.valueOf(day))
                        .build()
                )
                .toList();
    }

    private void isValidStoreOwner(Store store, UUID ownerId){
        if(!store.getOwnerId().equals(ownerId)){
            throw new IllegalArgumentException("invalid owner id : " + ownerId);
        }
    }

    private Store findById(UUID id){
        return storeRepository
                .findByIdFetch(id)
                .orElseThrow(()-> new NoSuchElementException("store not found : " + id));
    }

}

package com.playtable.store.service;

import com.playtable.store.domain.entity.DailyReservation;
import com.playtable.store.domain.entity.Store;
import com.playtable.store.repository.DailyReservationRepository;
import com.playtable.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreStatisticsService {

    private final StoreRepository storeRepository;
    private final DailyReservationRepository dailyReservationRepository;

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

    private Store findById(UUID id){
        return storeRepository
                .findByIdFetch(id)
                .orElseThrow(()-> new NoSuchElementException("store not found : " + id));
    }

}

package com.playtable.store.service;

import com.playtable.store.domain.entity.RestDay;
import com.playtable.store.domain.entity.Store;
import com.playtable.store.domain.request.MenuRequest;
import com.playtable.store.domain.request.ReservationRequest;
import com.playtable.store.domain.request.StoreRequest;
import com.playtable.store.domain.request.StoreUpdateRequest;
import com.playtable.store.kafka.StoreProducer;
import com.playtable.store.domain.kafka.ReservationOpenKafkaData;
import com.playtable.store.domain.kafka.StoreUpdateKafkaData;
import com.playtable.store.repository.MenuRepository;
import com.playtable.store.repository.RestDayRepository;
import com.playtable.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCommandService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final RestDayRepository restDayRepository;
    private final StoreProducer storeProducer;

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

        storeProducer.sendStoreUpdate(
                StoreUpdateKafkaData.of(store, restDays)
        );
    }

    public void reservationOpen(UUID storeId, ReservationRequest reservationRequest) {
        Store store = findById(storeId);
        store.reservationOpen();
        storeProducer.sendReservationOpen(
                ReservationOpenKafkaData.of(
                        storeId,
                        reservationRequest.seats())
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

    private Store findById(UUID id){
        return storeRepository
                .findByIdFetch(id)
                .orElseThrow(()-> new NoSuchElementException("store not found : " + id));
    }
}

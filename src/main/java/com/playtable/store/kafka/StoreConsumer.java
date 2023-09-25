package com.playtable.store.kafka;

import com.playtable.store.domain.kafka.ReservationKafkaData;
import com.playtable.store.service.StoreStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StoreConsumer {

    private final StoreStatisticsService storeStatisticsService;

    @KafkaListener(topics = TopicConfig.RESERVATION)
    public void listen(ReservationKafkaData data){
        storeStatisticsService.increaseReservation(data.storeId());
    }
}

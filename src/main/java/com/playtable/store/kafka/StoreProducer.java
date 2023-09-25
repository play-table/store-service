package com.playtable.store.kafka;

import com.playtable.store.domain.kafka.ReservationOpenKafkaData;
import com.playtable.store.domain.kafka.StoreUpdateKafkaData;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreProducer {

    private final KafkaTemplate<String, ReservationOpenKafkaData> reservationOpenKafkaTemplate;
    private final KafkaTemplate<String, StoreUpdateKafkaData> storeKafkaTemplate;

    public void sendReservationOpen(ReservationOpenKafkaData message){
        reservationOpenKafkaTemplate.send(TopicConfig.RESERVATION, message);
    }

    public void sendStoreUpdate(StoreUpdateKafkaData message){
        storeKafkaTemplate.send(TopicConfig.STORE, message);
    }
}

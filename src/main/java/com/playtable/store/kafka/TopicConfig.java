package com.playtable.store.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    public static final String STORE = "store";
    public static final String STORE_DLT = "store.DLT";
    public static final String RESERVATION = "reservation";
    public static final String RESERVATION_DLT = "reservation.DLT";

    @Bean
    public NewTopic topicStore(){
        return TopicBuilder
                .name(STORE)
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topicReservationOpen(){
        return TopicBuilder
                .name(RESERVATION)
                .partitions(1)
                .replicas(1)
                .build();
    }
}

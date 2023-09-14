package com.playtable.store.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class DailyReservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Store store;
    private LocalDate reservationDate;
    private Integer todayWaitingCount;

    public static DailyReservation createToday(Store store){
        return DailyReservation.builder()
                .store(store)
                .reservationDate(LocalDate.now())
                .todayWaitingCount(0)
                .build();
    }

    public void increaseTotalCount(){
        this.todayWaitingCount++;
    }
}

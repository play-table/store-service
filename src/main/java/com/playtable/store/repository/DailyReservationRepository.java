package com.playtable.store.repository;

import com.playtable.store.domain.entity.DailyReservation;
import com.playtable.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyReservationRepository extends JpaRepository<DailyReservation, Long> {

    @Query("select d from DailyReservation d " +
            "join fetch d.store " +
            "where d.reservationDate = :reservationDate " +
            "order by d.todayWaitingCount desc " +
            "limit 10")
    List<DailyReservation> findTopTenStoreByReservationDate(@Param("reservationDate") LocalDate reservationDate);

    Optional<DailyReservation> findByStoreAndReservationDate(Store store, LocalDate date);
}

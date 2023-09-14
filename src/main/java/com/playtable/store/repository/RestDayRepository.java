package com.playtable.store.repository;

import com.playtable.store.domain.entity.RestDay;
import com.playtable.store.domain.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestDayRepository extends JpaRepository<RestDay, Long> {

    List<RestDay> findByStore(Store store);
}

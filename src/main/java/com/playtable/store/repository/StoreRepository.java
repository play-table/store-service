package com.playtable.store.repository;

import com.playtable.store.domain.entity.Store;
import com.playtable.store.domain.response.StoreSummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {

    @Query("select s from Store s left join fetch s.restDays left join fetch s.menus where s.id = :id")
    Optional<Store> findByIdFetch(@Param("id") UUID id);

    List<Store> findByNameContaining(String name);
}

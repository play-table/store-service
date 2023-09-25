package com.playtable.store.service;

import com.playtable.store.domain.entity.Category;
import com.playtable.store.domain.entity.RestDay;
import com.playtable.store.domain.entity.Store;
import com.playtable.store.domain.request.StoreRequest;
import com.playtable.store.domain.request.StoreUpdateRequest;
import com.playtable.store.domain.response.StoreDetailResponse;
import com.playtable.store.repository.RestDayRepository;
import com.playtable.store.repository.StoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StoreQueryServiceTest {

    @Autowired
    StoreQueryService storeQueryService;
    @Autowired
    StoreCommandService storeCommandService;
    @Autowired
    StoreRepository storeRepository;
    @Autowired
    RestDayRepository restDayRepository;
    @Autowired
    EntityManager entityManager;

    @BeforeEach
    void init(){
        System.out.println("--------- init start ---------");
        initStore = storeRepository.save(
                Store.builder()
                        .id(null)
                        .ownerId(ownerId)
                        .name(name)
                        .contact(contact)
                        .address(address)
                        .category(Category.valueOf(category))
                        .imageUrl(imageUrl)
                        .reviewCount(10)
                        .totalRating(43L)
                        .openTime(openTime)
                        .closeTime(closeTime)
                        .breakStartTime(breakStartTime)
                        .breakEndTime(breakEndTime)
                        .restDays(new HashSet<>())
                        .menus(new HashSet<>())
                        .build());

        List<RestDay> restDays = days.stream()
                .map(day -> new RestDay(null, initStore, DayOfWeek.valueOf(day)))
                .toList();

        restDayRepository.saveAll(restDays);

        System.out.println("--------- init end ---------");
    }

    @AfterEach
    void clear(){

        System.out.println("--------- clear start ---------");

        restDayRepository.deleteAll();
        storeRepository.deleteAll();

        System.out.println("--------- clear end ---------");
    }
    
    @Nested
    class 가게_정보_조회{
        
        @Test
        void ID로_가게_정보_조회_성공(){
            //given
            //init에서 한 건 저장함

            //when
            StoreDetailResponse storeDetailResponse = storeQueryService.getById(initStore.getId());

            //then
            assertThat(storeDetailResponse.id()).isEqualTo(initStore.getId());
            assertThat(storeDetailResponse.name()).isEqualTo(initStore.getName());
            assertThat(storeDetailResponse.contact()).isEqualTo(initStore.getContact());
            assertThat(storeDetailResponse.address()).isEqualTo(initStore.getAddress());
            assertThat(storeDetailResponse.category()).isEqualTo(initStore.getCategory());
            assertThat(storeDetailResponse.imageUrl()).isEqualTo(initStore.getImageUrl());
            assertThat(storeDetailResponse.reviewCount()).isEqualTo(initStore.getReviewCount());
            assertThat(storeDetailResponse.totalRating()).isEqualTo(initStore.getTotalRating());
            assertThat(storeDetailResponse.imageUrl()).isEqualTo(initStore.getImageUrl());
            assertThat(storeDetailResponse.openTime()).isEqualTo(initStore.getOpenTime());
            assertThat(storeDetailResponse.closeTime()).isEqualTo(initStore.getCloseTime());
            assertThat(storeDetailResponse.breakStartTime()).isEqualTo(initStore.getBreakStartTime());
            assertThat(storeDetailResponse.breakEndTime()).isEqualTo(initStore.getBreakEndTime());
            assertThat(storeDetailResponse.menus()).hasSize(initStore.getMenus().size());
            assertThat(storeDetailResponse.restDays()).hasSize(initStore.getRestDays().size());
        }

        @Test
        void 없는_ID로_조회시_예외(){
            //given
            UUID invalidStoreId = UUID.randomUUID();

            //when, then
            assertThrows(
                    NoSuchElementException.class,
                    ()-> storeQueryService.getById(invalidStoreId));
        }

        @Test
        void 날짜별_예약_많은_가게_조회_성공(){

        }
    }

    @Nested
    class 가게_정보_수정{
        
        @Test
        void 가게_정보_수정_성공(){

            //given
            StoreUpdateRequest storeUpdateRequest = new StoreUpdateRequest(
                    "updateName",
                    "updateContact",
                    "updateContact",
                    "JAPANESE",
                    "updateImageUrl",
                    LocalTime.of(9, 30),
                    LocalTime.of(14, 30),
                    LocalTime.of(18, 30),
                    LocalTime.of(23, 30),
                    Arrays.asList("MONDAY", "SATURDAY", "SUNDAY")
            );

            //when
            storeCommandService.update(
                    initStore.getOwnerId(),
                    storeUpdateRequest);

            entityManager.flush();
            entityManager.clear();

            Store store = storeRepository.findById(initStore.getId()).get();
            //then
            assertThat(store.getName()).isEqualTo(storeUpdateRequest.name());
            assertThat(store.getContact()).isEqualTo(storeUpdateRequest.contact());
            assertThat(store.getAddress()).isEqualTo(storeUpdateRequest.address());
            assertThat(store.getCategory()).isEqualTo(Category.valueOf(storeUpdateRequest.category()));
            assertThat(store.getImageUrl()).isEqualTo(storeUpdateRequest.imageUrl());
            assertThat(store.getOpenTime()).isEqualTo(storeUpdateRequest.openTime());
            assertThat(store.getCloseTime()).isEqualTo(storeUpdateRequest.closeTime());
            assertThat(store.getBreakStartTime()).isEqualTo(storeUpdateRequest.breakStartTime());
            assertThat(store.getBreakEndTime()).isEqualTo(storeUpdateRequest.breakEndTime());
            assertThat(store.getRestDays()).hasSize(storeUpdateRequest.days().size());
        }

//        @Test
//        void 유효하지_않은_사장이_가게_정보_수정시_예외(){
//
//            //given
//            UUID invalidOwnerId = UUID.randomUUID();
//
//            //when, then
//            assertThrows(IllegalArgumentException.class,
//                    ()-> storeService.update(invalidOwnerId, initStore.getId(), null));
//        }
    }

    @Nested
    class 가게_정보_저장{
        UUID newOwnerId = UUID.randomUUID();
        String newName = "더좋은밥상";
        String newContact = "000-1111-2222";
        String newAddress = "서울시 독산동";
        String newCategory = "KOREAN";
        String newImageUrl = "imageUrl.png";
        LocalTime newOpenTime = LocalTime.of(11, 30);
        LocalTime newCloseTime = LocalTime.of(19, 30);
        LocalTime newBreakStartTime = LocalTime.of(14, 30);
        LocalTime newBreakEndTime = LocalTime.of(18, 0);
        List<String> newDays = Arrays.asList("SATURDAY", "SUNDAY");
        @Test
        void 가게_정보_저장_성공() {

            //given
            StoreRequest storeRequest = new StoreRequest(
                    newName,
                    newContact,
                    newAddress,
                    newCategory,
                    newImageUrl,
                    newOpenTime,
                    newCloseTime,
                    newBreakStartTime,
                    newBreakEndTime,
                    newDays
            );

            //when
            storeCommandService.register(ownerId, storeRequest);

            entityManager.flush();
            entityManager.clear();

            List<Store> stores = storeRepository.findAll();
            Store store = stores.get(stores.size() - 1);

            //then
            assertThat(store.getName()).isEqualTo(newName);
            assertThat(store.getContact()).isEqualTo(newContact);
            assertThat(store.getAddress()).isEqualTo(newAddress);
            assertThat(store.getCategory()).isEqualTo(Category.valueOf(newCategory));
            assertThat(store.getImageUrl()).isEqualTo(newImageUrl);
            assertThat(store.getOpenTime()).isEqualTo(newOpenTime);
            assertThat(store.getCloseTime()).isEqualTo(newCloseTime);
            assertThat(store.getBreakStartTime()).isEqualTo(newBreakStartTime);
            assertThat(store.getBreakEndTime()).isEqualTo(newBreakEndTime);
            assertThat(store.getRestDays()).hasSize(newDays.size());
        }
    }
    Store initStore;
    UUID ownerId = UUID.randomUUID();
    String name = "더좋은밥상";
    String contact = "000-1111-2222";
    String address = "서울시 독산동";
    String category = "KOREAN";
    String imageUrl = "imageUrl.png";
    LocalTime openTime = LocalTime.of(11, 30);
    LocalTime closeTime = LocalTime.of(19, 30);
    LocalTime breakStartTime = LocalTime.of(14, 30);
    LocalTime breakEndTime = LocalTime.of(18, 0);
    List<String> days = Arrays.asList("SATURDAY", "SUNDAY");

}
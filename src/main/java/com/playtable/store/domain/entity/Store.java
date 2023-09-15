package com.playtable.store.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Store {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID ownerId;
    private String name;
    private String contact;
    private String address;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String imageUrl;
    private Integer reviewCount;
    private Long totalRating;
    private Boolean isWaitingAble;
    private Boolean isReservationAble;
    private LocalTime openTime;
    private LocalTime closeTime;
    private LocalTime breakStartTime;
    private LocalTime breakEndTime;
    @OneToMany(mappedBy = "store")
    private List<DailyReservation> dailyReservations = new ArrayList<>();
    @OneToMany(mappedBy = "store")
    private Set<Menu> menus = new HashSet<>();
    @OneToMany(mappedBy = "store")
    private Set<RestDay> restDays = new HashSet<>();

    public void reviewStatistics(Long rating){
        this.totalRating += rating;
        this.reviewCount++;
    }

    public void reservationOpen(){
        this.isReservationAble = true;
    }

    public void waitingOpen(){
        this.isWaitingAble = true;
    }

    public void informationUpdate(
            String name,
            String contact,
            String address,
            String category,
            String imageUrl,
            LocalTime openTime,
            LocalTime closeTime,
            LocalTime breakStartTime,
            LocalTime breakEndTime
    ){
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.category = Category.valueOf(category);
        this.imageUrl = imageUrl;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.breakStartTime = breakStartTime;
        this.breakEndTime = breakEndTime;
    }
}

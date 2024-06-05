package com.tickethub.backend.performance.persist;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "performance")
public class PerformanceEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "performance_id")
    private Long performanceId;

    @Column(nullable = false)
    private String name;             // 공연이름

    @Column(name = "viewing_hours", nullable = false)
    private Integer viewingHours;    // 관람시간

    @Column(nullable = false)
    private LocalDate date;          // 공연 날짜

    @Column(nullable = false)
    private String cast;             // 출연진

    @Column(nullable = false)
    private String location;         // 장소

    @Column(name = "poster_path", nullable = false)
    private String posterPath;       // 이미지 경로

    private String category; // 카테고리

    @OneToMany(mappedBy = "performanceEntity")
    private List<SeatEntity> seats = new ArrayList<>();
}

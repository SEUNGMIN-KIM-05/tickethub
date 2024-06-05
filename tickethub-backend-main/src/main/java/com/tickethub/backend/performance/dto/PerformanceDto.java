package com.tickethub.backend.performance.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tickethub.backend.performance.persist.PerformanceEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerformanceDto {

    private Long performanceId;
    private String name;             // 공연이름
    private Integer viewingHours;    // 관람시간
    private LocalDate date;          // 공연 날짜
    private String cast;             // 출연진
    private String location;         // 장소
    private String posterPath;       // 이미지 경로
    private String category;         // 카테고리

    private List<SeatDto> seats;     // 좌석 정보

    // entity -> dto
    public static PerformanceDto from(PerformanceEntity performance) {
        return PerformanceDto.builder()
                .performanceId(performance.getPerformanceId())
                .name(performance.getName())
                .viewingHours(performance.getViewingHours())
                .date(performance.getDate())
                .cast(performance.getCast())
                .location(performance.getLocation())
                .posterPath(performance.getPosterPath())
                .category(performance.getCategory())
                .seats(performance.getSeats()
                        .stream()
                        .map(SeatDto::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
package com.tickethub.backend.event.dto;

import com.tickethub.backend.performance.dto.PerformanceDto;
import com.tickethub.backend.performance.dto.SeatDto;
import com.tickethub.backend.performance.persist.PerformanceEntity;
import com.tickethub.backend.performance.vo.ResponseSeat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventPerformanceDto {

    private Long performanceId;
    private String name;              // 공연이름
    private Integer viewingHours;     // 관람시간
    private LocalDate date;           // 공연 날짜
    private String cast;              // 출연진
    private String location;          // 장소
    private String posterPath;        // 이미지 경로
    private String category;          // 카테고리
    private List<SeatDto> seats;      // 좌석 정보
    private Integer discountPer;      // 할인률

    public static EventPerformanceDto from(PerformanceEntity performanceEntity, Integer discountPer) {
        return EventPerformanceDto.builder()
                .performanceId(performanceEntity.getPerformanceId())
                .name(performanceEntity.getName())
                .viewingHours(performanceEntity.getViewingHours())
                .date(performanceEntity.getDate())
                .cast(performanceEntity.getCast())
                .location(performanceEntity.getLocation())
                .posterPath(performanceEntity.getPosterPath())
                .category(performanceEntity.getCategory())
                .seats(performanceEntity.getSeats()
                        .stream()
                        .map(SeatDto::from)
                        .collect(Collectors.toList())
                )
                .discountPer(discountPer)
                .build();
    }
}

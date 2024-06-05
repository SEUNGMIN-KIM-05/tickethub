package com.tickethub.backend.performance.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.tickethub.backend.performance.dto.PerformanceDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePerformance {

    private Long performanceId;
    private String name;             // 공연이름
    private Integer viewingHours;    // 관람시간
    private LocalDate date;          // 공연 날짜
    private String cast;             // 출연진
    private String location;         // 장소
    private String posterPath;       // 이미지 경로
    private String category;          // 카테고리
    private List<ResponseSeat> seats;  // 좌석 정보

    // dto -> response
    public static ResponsePerformance fromDto(PerformanceDto performanceDto) {
        return ResponsePerformance.builder()
                .performanceId(performanceDto.getPerformanceId())
                .name(performanceDto.getName())
                .viewingHours(performanceDto.getViewingHours())
                .date(performanceDto.getDate())
                .cast(performanceDto.getCast())
                .location(performanceDto.getLocation())
                .posterPath(performanceDto.getPosterPath())
                .category(performanceDto.getCategory())
                .seats(
                    performanceDto.getSeats()
                        .stream()
                        .map(ResponseSeat::fromDto)
                        .collect(Collectors.toList())
                )
                .build();
    }
}
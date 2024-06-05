package com.tickethub.backend.book.vo;

import com.tickethub.backend.book.dto.BookDto;
import com.tickethub.backend.performance.dto.SeatDto;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseBookDetail {

    private String category; // 카테고리
    private String performanceName;  // 공연이름
    private Integer viewingHours;    // 관람시간
    private LocalDate date;          // 공연 날짜
    private String cast;             // 출연진
    private String location;         // 장소
    private String posterPath;       // 이미지 경로
    private List<SeatDto> seats;     // 좌석 정보
    private Integer totalPrice;      // 예매 가격

    public static ResponseBookDetail fromDto(BookDto bookDto) {
        SeatDto seatDto = bookDto.getSeatDtos().get(0);

        return ResponseBookDetail.builder()
                //.category()
                .performanceName(seatDto.getPerformanceName())
                .viewingHours(seatDto.getViewingHours())
                .date(seatDto.getDate())
                .cast(seatDto.getCast())
                .location(seatDto.getLocation())
                .posterPath(seatDto.getPosterPath())
                .seats(bookDto.getSeatDtos())
                .totalPrice(bookDto.getTotalPrice())
                .build();
    }
}
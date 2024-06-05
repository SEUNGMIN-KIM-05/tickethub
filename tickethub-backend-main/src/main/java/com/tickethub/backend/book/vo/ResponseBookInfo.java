package com.tickethub.backend.book.vo;

import com.tickethub.backend.book.dto.BookDto;
import com.tickethub.backend.performance.dto.SeatDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseBookInfo {

    private Long bookId;                // 예약번호
    private String category;            // 카테고리
    private String performanceName;     // 공연 이름
    private String cast;                // 출연진
    private List<String> seatNameList;  // 좌석 이름
    private Integer totalPrice;         // 결제 가격

    public static ResponseBookInfo fromDto(BookDto bookDto) {

        List<String> seatNameList = new ArrayList<>();
        for(SeatDto seatDto: bookDto.getSeatDtos()) {
            String seatName = "";
            seatNameList.add(seatDto.getSeatRow() + seatDto.getSeatCol());
        }

        return ResponseBookInfo.builder()
                .bookId(bookDto.getBookId())
                .category(bookDto.getSeatDtos().get(0).getCategory())
                .performanceName(bookDto.getSeatDtos().get(0).getPerformanceName())
                .cast(bookDto.getSeatDtos().get(0).getCast())
                .seatNameList(seatNameList)
                .totalPrice(bookDto.getTotalPrice())
                .build();
    }
}
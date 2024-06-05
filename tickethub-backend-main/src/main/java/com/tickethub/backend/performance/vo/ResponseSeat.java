package com.tickethub.backend.performance.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tickethub.backend.performance.dto.SeatDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseSeat {

    private Long seatId;
    private Integer price;
    private String seatRow;
    private String seatCol;
    private Boolean isBook;


    // dto -> response
    public static ResponseSeat fromDto(SeatDto seatDto) {
        return ResponseSeat.builder()
                .seatId(seatDto.getSeatId())
                .price(seatDto.getPrice())
                .seatRow(seatDto.getSeatRow())
                .seatCol(seatDto.getSeatCol())
                .isBook(seatDto.getIsBook())
                .build();
    }
}
package com.tickethub.backend.book.dto;

import com.tickethub.backend.book.persist.BookEntity;
import com.tickethub.backend.performance.dto.SeatDto;
import com.tickethub.backend.user.dto.UserDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Long bookId;
    private UserDto userDto;
    private List<SeatDto> seatDtos;
    private Integer totalPrice;

    public static BookDto from(BookEntity bookEntity) {
        return BookDto.builder()
                .bookId(bookEntity.getBookId())
                .userDto(UserDto.from(bookEntity.getUserEntity()))
                .seatDtos(
                        bookEntity.getSeatEntities()
                        .stream()
                        .map(SeatDto::from)
                        .collect(Collectors.toList())
                )
                .totalPrice(bookEntity.getTotalPrice())
                .build();
    }
}
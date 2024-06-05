package com.tickethub.backend.book.service;

import com.tickethub.backend.book.dto.BookDto;
import com.tickethub.backend.book.persist.BookEntity;
import com.tickethub.backend.book.persist.BookRepository;
import com.tickethub.backend.performance.persist.SeatEntity;
import com.tickethub.backend.performance.persist.SeatRepository;
import com.tickethub.backend.user.persist.UserEntity;
import com.tickethub.backend.user.persist.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final UserRepository userRepository;
    private final SeatRepository seatRepository;
    private final BookRepository bookRepository;

    @Transactional
    @Override
    public BookDto createBook(List<Long> seatIdList, String username) {

        // 유저 조회
        UserEntity userEntity = validateUser(username);
        List<SeatEntity> seatEntities = new ArrayList<>();
        int totalPrice = 0;

        // 좌석 조회 및 상태 변경
        for(Long seatId: seatIdList) {
            SeatEntity seatEntity = seatRepository.findById(seatId)
                    .orElseThrow(() -> new IllegalStateException("Seat Not Found"));

            if(seatEntity.getIsBook()) {
                throw new IllegalStateException("이미 예약된 좌석입니다");
            }
            seatEntity.setIsBook(true);


            if(seatEntity.getPerformanceEntity().getName().contains("하데스타운")) {
                totalPrice += seatEntity.getPrice() * 50 / 100;
                seatEntity.setPrice(seatEntity.getPrice() * 50 / 100);

            }else {
                totalPrice += seatEntity.getPrice();
            }
            seatEntities.add(seatEntity);
        }

        // 예약 생성
        BookEntity bookEntity = BookEntity
                .builder()
                .userEntity(userEntity)
                .seatEntities(seatEntities)
                .totalPrice(totalPrice)
                .build();

        // 예약 저장
        bookRepository.save(bookEntity);

        for(SeatEntity seatEntity: seatEntities) {
            seatEntity.changeBookEntity(bookEntity);
        }

        return BookDto.from(bookEntity);
    }

    // 유저 - 예매 목록 조회
    @Override
    public List<BookDto> getBookList(String username) {
        UserEntity userEntity = validateUser(username);

        return userEntity.getBooks()
                .stream()
                .map(BookDto::from)
                .collect(Collectors.toList());
    }

    // 유저 - 예매 상세 조회
    @Override
    public BookDto getBook(Long bookId, String username) {
        validateUser(username);

        BookEntity bookEntity = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book Not Found"));

        return BookDto.from(bookEntity);
    }

    // 유저 확인
    private UserEntity validateUser(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));
    }
}
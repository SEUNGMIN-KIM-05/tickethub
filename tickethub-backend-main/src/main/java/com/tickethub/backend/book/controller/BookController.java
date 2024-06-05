package com.tickethub.backend.book.controller;

import com.tickethub.backend.book.dto.BookDto;
import com.tickethub.backend.book.service.BookService;
import com.tickethub.backend.book.vo.ResponseBookDetail;
import com.tickethub.backend.book.vo.ResponseBookInfo;
import com.tickethub.backend.user.jwt.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;
    private final JWTUtil jwtUtil;

    // 예매
    @PostMapping
    public BookDto createBook(
            @CookieValue("token") String token,
            @RequestBody List<Long> seatIdList) {

        String username = jwtUtil.getUsername(token);

        log.info("seatIdList={}", seatIdList);

        return bookService.createBook(seatIdList, username);
    }

    // 예매 내역 전체 조회
    @GetMapping
    public List<ResponseBookInfo> getAllBook(
            @CookieValue("token") String token
    ) {
        String username = jwtUtil.getUsername(token);
        return bookService.getBookList(username)
                .stream()
                .map(ResponseBookInfo::fromDto)
                .collect(Collectors.toList());
    }

    // 예매 내역 자세히 조회
    @GetMapping("/{bookId}")
    public ResponseBookDetail getBook(
            @PathVariable("bookId") Long bookId,
            @CookieValue("token") String token
    ) {
        String username = jwtUtil.getUsername(token);

        return ResponseBookDetail.fromDto(
                bookService.getBook(bookId, username)
        );
    }
}
package com.tickethub.backend.performance.persist;

import com.tickethub.backend.book.persist.BookEntity;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seat")
public class SeatEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private PerformanceEntity performanceEntity;

    @Column(name = "seat_row")
    private String seatRow;

    @Column(name = "seat_col")
    private String seatCol;

    @Setter
    @Column(nullable = false)
    private Integer price; // 좌석 가격

    @Setter
    @Column(name = "is_book")
    private Boolean isBook; // 예약 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    public void changeBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }
}
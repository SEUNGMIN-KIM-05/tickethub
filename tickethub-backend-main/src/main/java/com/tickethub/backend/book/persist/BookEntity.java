package com.tickethub.backend.book.persist;

import com.tickethub.backend.performance.persist.SeatEntity;
import com.tickethub.backend.user.persist.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class BookEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity; // 회원

    @OneToMany(mappedBy = "bookEntity")
    private List<SeatEntity> seatEntities = new ArrayList<>();

    @Column(name = "total_price")
    private Integer totalPrice;

}

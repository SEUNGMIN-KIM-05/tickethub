package com.tickethub.backend.book.vo;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestBook {

    private List<Long> seatIdList;
}
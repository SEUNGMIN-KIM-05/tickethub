package com.tickethub.backend.performance.controller;

import com.tickethub.backend.performance.service.PerformanceService;
import com.tickethub.backend.performance.vo.ResponsePerformance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/performances")
@RequiredArgsConstructor
@Slf4j
public class PerformanceController {

    private final PerformanceService performanceService;

    // 공연 전체 조회
    @GetMapping
    public ResponseEntity<List<ResponsePerformance>> getAllPerformances() {
        return ResponseEntity.status(HttpStatus.OK).body(
                performanceService.getAllPerformances()
                        .stream()
                        .map(ResponsePerformance::fromDto)
                        .collect(Collectors.toList()));
    }

    // 공연 단건 조회
    @GetMapping("/{performanceId}")
    public ResponseEntity<ResponsePerformance> getPerformance(
            @PathVariable("performanceId") Long performanceId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                ResponsePerformance.fromDto(
                        performanceService.getPerformance(performanceId)));
    }

    // 공연 조회 - 카테고리
    @GetMapping("/search")
    public ResponseEntity<List<ResponsePerformance>> searchByCategory(
            @RequestParam("category") String category
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                performanceService.searchByCategory(category)
                .stream()
                .map(ResponsePerformance::fromDto)
                .collect(Collectors.toList())
        );
    }
}
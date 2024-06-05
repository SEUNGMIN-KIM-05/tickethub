package com.tickethub.backend.performance.service;

import com.tickethub.backend.performance.persist.PerformanceEntity;
import com.tickethub.backend.performance.dto.PerformanceDto;
import com.tickethub.backend.performance.persist.PerformanceRepository;
import com.tickethub.backend.performance.persist.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class PerformanceServiceImpl implements PerformanceService{

    private final PerformanceRepository performanceRepository;
    private final SeatRepository seatRepository;

    // 공연 전체 조회
    public List<PerformanceDto> getAllPerformances() {
        return performanceRepository.findAll()
                .stream()
                .map(PerformanceDto::from)
                .collect(Collectors.toList());
    }

    // 공연 단건 조회
    public PerformanceDto getPerformance(Long performanceId) {
        PerformanceEntity performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalArgumentException("Not Found Performance"));

        return PerformanceDto.from(performance);
    }

    // 공연 조회 - 카테고리
    public List<PerformanceDto> searchByCategory(String category) {
        return performanceRepository.findByCategory(category)
                .stream()
                .map(PerformanceDto::from)
                .collect(Collectors.toList());
    }
}
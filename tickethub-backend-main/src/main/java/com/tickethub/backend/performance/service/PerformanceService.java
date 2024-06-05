package com.tickethub.backend.performance.service;

import com.tickethub.backend.performance.dto.PerformanceDto;

import java.util.List;

public interface PerformanceService {
    List<PerformanceDto> getAllPerformances();
    PerformanceDto getPerformance(Long performanceId);
    List<PerformanceDto> searchByCategory(String category);
}

package com.tickethub.backend.performance.persist;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<SeatEntity, Long> {
    List<SeatEntity> findByPerformanceEntity_PerformanceId(Long performanceId);
}
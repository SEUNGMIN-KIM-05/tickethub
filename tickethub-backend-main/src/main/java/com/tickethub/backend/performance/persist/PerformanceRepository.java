package com.tickethub.backend.performance.persist;

import com.tickethub.backend.performance.persist.PerformanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceRepository extends JpaRepository<PerformanceEntity, Long> {
    List<PerformanceEntity> findByCategory(String category);
}
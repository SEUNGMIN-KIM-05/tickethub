package com.tickethub.backend.event.service;

import com.tickethub.backend.event.dto.EventPerformanceDto;

import java.util.List;

public interface EventService {

    List<EventPerformanceDto> getEventPerformances();
    EventPerformanceDto getEventPerformance(Long performanceId);
}

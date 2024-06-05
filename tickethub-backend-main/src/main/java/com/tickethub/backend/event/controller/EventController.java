package com.tickethub.backend.event.controller;

import com.tickethub.backend.event.dto.EventPerformanceDto;
import com.tickethub.backend.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/event/performances")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public List<EventPerformanceDto> getEventPerformances() {
        return eventService.getEventPerformances();
    }

    @GetMapping("/{performanceId}")
    public EventPerformanceDto getEventPerformance(@PathVariable("performanceId") Long performanceId) {
        return eventService.getEventPerformance(performanceId);
    }
}
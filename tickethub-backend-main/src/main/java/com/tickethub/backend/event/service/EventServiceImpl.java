package com.tickethub.backend.event.service;

import com.tickethub.backend.event.dto.EventPerformanceDto;
import com.tickethub.backend.performance.persist.PerformanceEntity;
import com.tickethub.backend.performance.persist.PerformanceRepository;
import com.tickethub.backend.performance.persist.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final PerformanceRepository performanceRepository;

    /**
     * 이벤트 공연 조회(category=event)
     */
    public List<EventPerformanceDto> getEventPerformances() {
        List<PerformanceEntity> eventPerformances = performanceRepository.findByCategory("event");

        List<Integer> discountPerList = new ArrayList<>(Arrays.asList(50, 40, 30));
        List<EventPerformanceDto> resultList = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            EventPerformanceDto event = EventPerformanceDto.from(eventPerformances.get(i), discountPerList.get(i));
            resultList.add(event);
        }
        return resultList;
    }

    /**
     * 이벤트 공연 상세 조회
     */
    public EventPerformanceDto getEventPerformance(Long performanceId) {
        PerformanceEntity performanceEntity = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new IllegalStateException("공연이 존재하지 않습니다"));

        Integer discountPer = 0;

        if(performanceEntity.getName().contains("하데스타운")) {
            discountPer = 50;
        }else if(performanceEntity.getName().contains("헤드윅")) {
            discountPer = 40;
        }else {
            discountPer = 30;
        }

        return EventPerformanceDto.from(performanceEntity, discountPer);
    }
}
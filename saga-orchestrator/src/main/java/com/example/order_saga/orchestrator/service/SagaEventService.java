package com.example.order_saga.orchestrator.service;

import com.example.order_saga.orchestrator.entity.SagaEvents;
import com.example.order_saga.orchestrator.repository.SagaEventsRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class SagaEventService {

  private final SagaEventsRepository sagaEventsRepository;
  private final ObjectMapper objectMapper;

  public void logEvent(Long sagaId, String eventType, Object payload) {
    SagaEvents events =
        SagaEvents.builder()
            .sagaId(sagaId)
            .eventType(eventType)
            .payload(objectMapper.writeValueAsString(payload))
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

    sagaEventsRepository.save(events);
  }
}

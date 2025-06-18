package com.reservationapi.infra.adapters.blockeddate.event;

import com.reservationapi.domain.blockeddate.event.BlockedDateCreatedEvent;
import com.reservationapi.domain.blockeddate.port.BlockedDateCreatedEventPort;
import com.reservationapi.infra.adapters.event.jpa.entity.DomainEvent;
import com.reservationapi.infra.adapters.event.jpa.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class BlockedDateCreatedEventAdapter implements BlockedDateCreatedEventPort {

    private final EventRepository eventRepository;

    @Override
    public void publish(BlockedDateCreatedEvent blockedDateCreatedEvent) {
        log.info(
            "Publishing blocked date created event for type: {}, placement: {}, priority: {}",
            blockedDateCreatedEvent.getType(),
            blockedDateCreatedEvent.getPlacement(),
            blockedDateCreatedEvent.getPriority()
        );

        DomainEvent domainEvent = DomainEvent
            .builder()
            .aggregateType("BlockedDate")
            .aggregateId(blockedDateCreatedEvent.getPartitionKey())
            .payload(blockedDateCreatedEvent)
            .build();

        eventRepository.save(domainEvent);
    }
}

package com.reservationapi.domain.blockeddate.port;

import com.reservationapi.domain.blockeddate.event.BlockedDateCreatedEvent;
import com.reservationapi.domain.common.event.EventPublisher;

public interface BlockedDateCreatedEventPort extends EventPublisher<BlockedDateCreatedEvent> {
    void publish(BlockedDateCreatedEvent blockedDateCreatedEvent);
}

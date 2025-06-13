package com.trendyol.reservationapi.domain.blockeddate.port;

import com.trendyol.reservationapi.domain.blockeddate.event.BlockedDateCreatedEvent;
import com.trendyol.reservationapi.domain.common.event.EventPublisher;

public interface BlockedDateCreatedEventPort extends EventPublisher<BlockedDateCreatedEvent> {
    void publish(BlockedDateCreatedEvent blockedDateCreatedEvent);
}

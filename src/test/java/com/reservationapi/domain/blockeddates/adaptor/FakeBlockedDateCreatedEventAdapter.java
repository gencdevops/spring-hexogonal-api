package com.reservationapi.domain.blockeddates.adaptor;

import com.reservationapi.domain.blockeddate.event.BlockedDateCreatedEvent;
import com.reservationapi.domain.blockeddate.port.BlockedDateCreatedEventPort;
import java.util.ArrayList;
import java.util.List;

public class FakeBlockedDateCreatedEventAdapter implements BlockedDateCreatedEventPort {

    private List<BlockedDateCreatedEvent> events = new ArrayList<>();

    @Override
    public void publish(BlockedDateCreatedEvent event) {
        events.add(event);
    }

    public List<BlockedDateCreatedEvent> getPublishedEvents() {
        return new ArrayList<>(events);
    }

    public void reset() {
        events.clear();
    }
}

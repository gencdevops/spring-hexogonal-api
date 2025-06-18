package com.reservationapi.domain.common.event;

public enum EventType {
    BLOCKED_DATE_CREATED("blocked-date-created");

    public final String aggregateType;

    EventType(String aggregateType) {
        this.aggregateType = aggregateType;
    }
}

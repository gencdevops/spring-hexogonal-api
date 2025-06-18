package com.reservationapi.domain.common.event;

public interface EventPublisher<T extends Event> {
    void publish(T event);
}

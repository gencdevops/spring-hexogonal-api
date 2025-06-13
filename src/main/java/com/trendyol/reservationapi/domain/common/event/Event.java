package com.trendyol.reservationapi.domain.common.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

public interface Event extends Serializable {
    @JsonIgnore
    String getPartitionKey();

    @JsonIgnore
    EventType getType();
}

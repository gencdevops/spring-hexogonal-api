package com.trendyol.reservationapi.domain.blockeddate.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import com.trendyol.reservationapi.domain.common.event.Event;
import com.trendyol.reservationapi.domain.common.event.EventType;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BlockedDateCreatedEvent implements Event {

    private Types type;
    private Placement placement;
    private Integer priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private List<LocalDate> blockedDates;

    @Override
    public String getPartitionKey() {
        return String.format("%s_%s_%d", type.name(), placement.name(), priority);
    }

    @Override
    public EventType getType() {
        return EventType.BLOCKED_DATE_CREATED;
    }

    public static BlockedDateCreatedEvent create(
        Types type,
        Placement placement,
        Integer priority,
        List<LocalDate> blockedDates
    ) {
        return BlockedDateCreatedEvent
            .builder()
            .type(type)
            .placement(placement)
            .priority(priority)
            .blockedDates(blockedDates)
            .build();
    }
}

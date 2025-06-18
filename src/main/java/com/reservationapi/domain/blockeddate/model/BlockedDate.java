package com.reservationapi.domain.blockeddate.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockedDate {

    private Long id;
    private LocalDate blockedDate;
    private Types type;
    private Placement placement;
    private Integer priority;

    public static BlockedDate create(Types type, Placement placement, Integer priority, LocalDate blockedDate) {
        return BlockedDate
            .builder()
            .type(type)
            .placement(placement)
            .priority(priority)
            .blockedDate(blockedDate)
            .build();
    }
}

package com.trendyol.reservationapi.domain.blockeddate.dto;

import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterBlockedDatesQuery {
    private LocalDate startDate;
    private LocalDate endDate;
    private Types type;
    private Placement placement;
    private Integer priority;
}

package com.reservationapi.domain.blockeddate.dto;

import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

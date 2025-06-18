package com.reservationapi.domain.blockeddate.usecase;

import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.domain.common.usecase.UseCase;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterBlockedDates implements UseCase {

    private LocalDate startDate;
    private LocalDate endDate;
    private Types type;
    private Placement placement;
    private Integer priority;
}

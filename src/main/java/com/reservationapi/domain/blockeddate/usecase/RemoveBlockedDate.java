package com.reservationapi.domain.blockeddate.usecase;

import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.domain.common.usecase.UseCase;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBlockedDate implements UseCase {

    private Types type;
    private Placement placement;
    private Integer priority;
    private List<LocalDate> blockedDates;
}

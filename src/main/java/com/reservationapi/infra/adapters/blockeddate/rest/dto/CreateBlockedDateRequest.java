package com.reservationapi.infra.adapters.blockeddate.rest.dto;

import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateBlockedDateRequest {

    private Types type;
    private Placement placement;
    private Integer priority;
    private List<LocalDate> blockedDates;
}

package com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto;

import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
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

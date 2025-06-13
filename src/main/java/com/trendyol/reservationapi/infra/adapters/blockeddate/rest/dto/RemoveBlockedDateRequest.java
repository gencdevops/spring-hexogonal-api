package com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto;

import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveBlockedDateRequest {

    private Types type;
    private Placement placement;
    private Integer priority;
    private List<LocalDate> dates;
}

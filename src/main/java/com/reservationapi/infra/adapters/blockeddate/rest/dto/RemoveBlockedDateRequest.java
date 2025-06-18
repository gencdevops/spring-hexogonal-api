package com.reservationapi.infra.adapters.blockeddate.rest.dto;

import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
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

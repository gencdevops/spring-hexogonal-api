package com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto;

import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockedDateResponse {

    private Long id;
    private Types type;
    private Placement placement;
    private LocalDate blockedDate;
}

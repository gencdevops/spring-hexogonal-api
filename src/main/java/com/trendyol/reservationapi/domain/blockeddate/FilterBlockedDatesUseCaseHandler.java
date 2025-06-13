package com.trendyol.reservationapi.domain.blockeddate;

import com.trendyol.reservationapi.domain.blockeddate.dto.FilterBlockedDatesQuery;
import com.trendyol.reservationapi.domain.blockeddate.model.BlockedDate;
import com.trendyol.reservationapi.domain.blockeddate.port.BlockedDatePort;
import com.trendyol.reservationapi.domain.blockeddate.usecase.FilterBlockedDates;
import com.trendyol.reservationapi.domain.common.annotation.DomainComponent;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@DomainComponent
@RequiredArgsConstructor
public class FilterBlockedDatesUseCaseHandler {

    private final BlockedDatePort blockedDatePort;

    public List<BlockedDate> handle(FilterBlockedDates useCase) {

        FilterBlockedDatesQuery query = FilterBlockedDatesQuery.builder()
                .startDate(useCase.getStartDate())
                .endDate(useCase.getEndDate())
                .type(useCase.getType())
                .placement(useCase.getPlacement())
                .priority(useCase.getPriority())
                .build();
        List<BlockedDate> blockedDates = blockedDatePort.filter(query);

        log.info("Retrieved {} blocked dates", blockedDates.size());

        return blockedDates;
    }
}

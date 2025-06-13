package com.trendyol.reservationapi.domain.blockeddate;

import static com.trendyol.reservationapi.infra.common.logger.Constants.AUDIT_MARKER;

import com.trendyol.reservationapi.domain.blockeddate.port.BlockedDatePort;
import com.trendyol.reservationapi.domain.blockeddate.usecase.RemoveBlockedDate;
import com.trendyol.reservationapi.domain.common.annotation.DomainComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@DomainComponent
@RequiredArgsConstructor
public class RemoveBlockedDateUseCaseHandler {

    private final BlockedDatePort blockedDatePort;

    @Transactional
    public void handle(RemoveBlockedDate useCase) {
        blockedDatePort.deleteBlockedDates(
            useCase.getType(),
            useCase.getPlacement(),
            useCase.getPriority(),
            useCase.getBlockedDates()
        );

        log.info(
            AUDIT_MARKER,
            "Successfully deleted {} blocked dates for parameters: {}-{}-{}",
            useCase.getBlockedDates().size(),
            useCase.getType(),
            useCase.getPlacement(),
            useCase.getPriority()
        );
    }
}

package com.trendyol.reservationapi.domain.blockeddate;

import static com.trendyol.reservationapi.infra.common.logger.Constants.AUDIT_MARKER;

import com.trendyol.reservationapi.domain.blockeddate.event.BlockedDateCreatedEvent;
import com.trendyol.reservationapi.domain.blockeddate.exception.*;
import com.trendyol.reservationapi.domain.blockeddate.model.BlockedDate;
import com.trendyol.reservationapi.domain.blockeddate.port.BlockedDateCreatedEventPort;
import com.trendyol.reservationapi.domain.blockeddate.port.BlockedDatePort;
import com.trendyol.reservationapi.domain.blockeddate.usecase.CreateBlockedDate;
import com.trendyol.reservationapi.domain.common.annotation.DomainComponent;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@DomainComponent
@RequiredArgsConstructor
public class CreateBlockedDateUseCaseHandler {

    private final BlockedDatePort blockedDatePort;
    private final BlockedDateCreatedEventPort blockedDateCreatedEventPort;

    @Transactional
    public void handle(CreateBlockedDate useCase) {
        validateUseCase(useCase);

        for (LocalDate blockedDate : useCase.getBlockedDates()) {
            if (
                blockedDatePort.existsByParametersAndBlockedDate(
                    useCase.getType(),
                    useCase.getPlacement(),
                    useCase.getPriority(),
                    blockedDate
                )
            ) {
                throw new BlockedDateAlreadyExistException();
            }
        }

        List<BlockedDate> blockedDates = useCase
            .getBlockedDates()
            .stream()
            .distinct()
            .map(blockedDate ->
                BlockedDate.create(useCase.getType(), useCase.getPlacement(), useCase.getPriority(), blockedDate)
            )
            .toList();
        blockedDatePort.saveBlockedDates(blockedDates);

        List<LocalDate> savedDates = blockedDates.stream().map(BlockedDate::getBlockedDate).toList();
        BlockedDateCreatedEvent event = BlockedDateCreatedEvent.create(
            useCase.getType(),
            useCase.getPlacement(),
            useCase.getPriority(),
            savedDates
        );
        blockedDateCreatedEventPort.publish(event);

        log.info(
            AUDIT_MARKER,
            "Successfully saved {} blocked dates for parameters: {}-{}-{}",
            blockedDates.size(),
            useCase.getType(),
            useCase.getPlacement(),
            useCase.getPriority()
        );
    }

    private void validateUseCase(CreateBlockedDate useCase) {
        if (useCase.getPriority() == null || useCase.getPriority() < 1 || useCase.getPriority() > 3) {
            throw new BlockedDateInvalidPriorityException();
        }
        if (useCase.getBlockedDates() == null || useCase.getBlockedDates().isEmpty()) {
            throw new BlockedDatesCannotBeNullException();
        }
    }
}

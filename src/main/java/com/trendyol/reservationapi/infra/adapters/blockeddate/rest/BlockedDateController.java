package com.trendyol.reservationapi.infra.adapters.blockeddate.rest;

import com.trendyol.reservationapi.domain.blockeddate.CreateBlockedDateUseCaseHandler;
import com.trendyol.reservationapi.domain.blockeddate.FilterBlockedDatesUseCaseHandler;
import com.trendyol.reservationapi.domain.blockeddate.RemoveBlockedDateUseCaseHandler;
import com.trendyol.reservationapi.domain.blockeddate.model.BlockedDate;
import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto.BlockedDateResponse;
import com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto.CreateBlockedDateRequest;
import com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto.RemoveBlockedDateRequest;
import com.trendyol.reservationapi.infra.adapters.blockeddate.rest.mapper.BlockedDateMapper;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/api/v1/blocked-dates")
@RequiredArgsConstructor
public class BlockedDateController {

    private final FilterBlockedDatesUseCaseHandler filterBlockedDatesUseCaseHandler;
    private final CreateBlockedDateUseCaseHandler createBlockedDateUseCaseHandler;
    private final RemoveBlockedDateUseCaseHandler removeBlockedDateUseCaseHandler;
    private final BlockedDateMapper mapper;

    @GetMapping
    public List<BlockedDateResponse> getBlockedDates(
        @RequestParam(required = false) LocalDate startDate,
        @RequestParam(required = false) LocalDate endDate,
        @RequestParam Types type,
        @RequestParam Placement placement,
        @RequestParam Integer priority
    ) {
        log.info(
            "Get blocked dates request is handled for startDate: {}, endDate: {}, type: {}, placement: {}, priority: {}",
            startDate,
            endDate,
            type,
            placement,
            priority
        );

        List<BlockedDate> blockedDates = filterBlockedDatesUseCaseHandler.handle(
            mapper.getBlockedDateUseCase(startDate, endDate, type, placement, priority)
        );

        return mapper.toResponse(blockedDates);
    }

    @PostMapping
    public ResponseEntity<Void> saveBlockedDates(@RequestBody CreateBlockedDateRequest request) {
        log.info(
            "Save blocked dates request is handled for type: {}, placement: {}, priority: {}, blockedDates: {}",
            request.getType(),
            request.getPlacement(),
            request.getPriority(),
            request.getBlockedDates()
        );

        createBlockedDateUseCaseHandler.handle(mapper.createBlockedDateUseCase(request));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteBlockedDates(@RequestBody RemoveBlockedDateRequest request) {
        log.info(
            "Delete blocked dates request is handled for parameters: {}-{}-{} and dates: {}",
            request.getType(),
            request.getPlacement(),
            request.getPriority(),
            request.getDates()
        );

        removeBlockedDateUseCaseHandler.handle(mapper.removeBlockedDateUseCase(request));

        return ResponseEntity.ok().build();
    }
}

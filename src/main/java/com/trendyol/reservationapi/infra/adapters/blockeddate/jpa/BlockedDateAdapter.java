package com.trendyol.reservationapi.infra.adapters.blockeddate.jpa;

import com.trendyol.reservationapi.domain.blockeddate.dto.FilterBlockedDatesQuery;
import com.trendyol.reservationapi.domain.blockeddate.model.BlockedDate;
import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import com.trendyol.reservationapi.domain.blockeddate.port.BlockedDatePort;
import com.trendyol.reservationapi.domain.blockeddate.usecase.FilterBlockedDates;
import com.trendyol.reservationapi.infra.adapters.blockeddate.jpa.entity.BlockedDateEntity;
import com.trendyol.reservationapi.infra.adapters.blockeddate.jpa.mapper.BlockedDateMapper;
import com.trendyol.reservationapi.infra.adapters.blockeddate.jpa.repository.BlockedDatesRepository;
import com.trendyol.reservationapi.infra.adapters.blockeddate.jpa.specification.BlockedDatesSpecification;

import java.time.LocalDate;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class BlockedDateAdapter implements BlockedDatePort {

    private final BlockedDatesRepository blockedDatesRepository;
    private final BlockedDatesSpecification blockedDatesSpecification;
    private final BlockedDateMapper blockedDateMapper;

    @Override
    public void saveBlockedDates(List<BlockedDate> blockedDates) {
        List<BlockedDateEntity> entities = blockedDateMapper.mapToEntityList(blockedDates);

        blockedDatesRepository.saveAll(entities);
        log.info("Saved {} new blocked dates to database", entities.size());
    }

    @Override
    public boolean existsByParametersAndBlockedDate(
        Types type,
        Placement placement,
        Integer priority,
        LocalDate blockedDate
    ) {
        return blockedDatesRepository.existsByParametersAndBlockedDate(type, placement, priority, blockedDate);
    }

    @Override
    public void deleteBlockedDates(Types type, Placement placement, Integer priority, List<LocalDate> blockedDates) {
        blockedDatesRepository.softDeleteByParametersAndDates(type, placement, priority, blockedDates);
    }

    @Override
    public List<BlockedDate> filter(FilterBlockedDatesQuery query) {
        Specification<BlockedDateEntity> spec = blockedDatesSpecification.get(query);

        Pageable pageable = PageRequest.of(0, 31, Sort.by("blockedDate").ascending());
        List<BlockedDateEntity> entities = blockedDatesRepository.findAll(spec, pageable).getContent();

        return blockedDateMapper.mapToDomainList(entities);
    }
}
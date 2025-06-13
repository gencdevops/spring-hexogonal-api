package com.trendyol.reservationapi.infra.adapters.blockeddate.rest.mapper;

import com.trendyol.reservationapi.domain.blockeddate.model.BlockedDate;
import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import com.trendyol.reservationapi.domain.blockeddate.usecase.CreateBlockedDate;
import com.trendyol.reservationapi.domain.blockeddate.usecase.FilterBlockedDates;
import com.trendyol.reservationapi.domain.blockeddate.usecase.RemoveBlockedDate;
import com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto.BlockedDateResponse;
import com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto.CreateBlockedDateRequest;
import com.trendyol.reservationapi.infra.adapters.blockeddate.rest.dto.RemoveBlockedDateRequest;
import java.time.LocalDate;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    implementationName = "BlockedDateRestMapperImpl",
    builder = @Builder(disableBuilder = true)
)
public interface BlockedDateMapper {
    @Mapping(target = "type", source = "type")
    @Mapping(target = "placement", source = "placement")
    CreateBlockedDate createBlockedDateUseCase(CreateBlockedDateRequest request);

    List<BlockedDateResponse> toResponse(List<BlockedDate> blockedDate);

    @Mapping(target = "type", source = "type")
    @Mapping(target = "placement", source = "placement")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "blockedDates", source = "dates")
    RemoveBlockedDate removeBlockedDateUseCase(RemoveBlockedDateRequest request);

    default FilterBlockedDates getBlockedDateUseCase(
        LocalDate startDate,
        LocalDate endDate,
        Types type,
        Placement placement,
        Integer priority
    ) {
        return FilterBlockedDates
            .builder()
            .startDate(startDate)
            .endDate(endDate)
            .type(type)
            .placement(placement)
            .priority(priority)
            .build();
    }
}

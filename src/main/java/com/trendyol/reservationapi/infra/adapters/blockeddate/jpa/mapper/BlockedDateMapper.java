package com.trendyol.reservationapi.infra.adapters.blockeddate.jpa.mapper;

import com.trendyol.reservationapi.domain.blockeddate.model.BlockedDate;
import com.trendyol.reservationapi.infra.adapters.blockeddate.jpa.entity.BlockedDateEntity;
import java.util.List;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    implementationName = "BlockedDateJpaMapperImpl",
    builder = @Builder(disableBuilder = true)
)
public interface BlockedDateMapper {
    @Mapping(target = "id", expression = "java((Long) entity.getId())")
    BlockedDate mapToDomain(BlockedDateEntity entity);

    List<BlockedDate> mapToDomainList(List<BlockedDateEntity> entities);

    List<BlockedDateEntity> mapToEntityList(List<BlockedDate> blockedDates);
}

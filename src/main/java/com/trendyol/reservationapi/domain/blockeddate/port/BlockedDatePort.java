package com.trendyol.reservationapi.domain.blockeddate.port;

import com.trendyol.reservationapi.domain.blockeddate.dto.FilterBlockedDatesQuery;
import com.trendyol.reservationapi.domain.blockeddate.model.BlockedDate;
import com.trendyol.reservationapi.domain.blockeddate.model.Placement;
import com.trendyol.reservationapi.domain.blockeddate.model.Types;
import java.time.LocalDate;
import java.util.List;

public interface BlockedDatePort {
    List<BlockedDate> filter(FilterBlockedDatesQuery query);

    void saveBlockedDates(List<BlockedDate> blockedDates);

    void deleteBlockedDates(Types type, Placement placement, Integer priority, List<LocalDate> blockedDates);

    boolean existsByParametersAndBlockedDate(Types type, Placement placement, Integer priority, LocalDate blockedDate);
}

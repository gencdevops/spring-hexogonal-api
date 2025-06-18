package com.reservationapi.domain.blockeddate.port;

import com.reservationapi.domain.blockeddate.dto.FilterBlockedDatesQuery;
import com.reservationapi.domain.blockeddate.model.BlockedDate;
import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import java.time.LocalDate;
import java.util.List;

public interface BlockedDatePort {
    List<BlockedDate> filter(FilterBlockedDatesQuery query);

    void saveBlockedDates(List<BlockedDate> blockedDates);

    void deleteBlockedDates(Types type, Placement placement, Integer priority, List<LocalDate> blockedDates);

    boolean existsByParametersAndBlockedDate(Types type, Placement placement, Integer priority, LocalDate blockedDate);
}

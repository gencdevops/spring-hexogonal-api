package com.reservationapi.domain.blockeddates.adaptor;

import com.reservationapi.domain.blockeddate.dto.FilterBlockedDatesQuery;
import com.reservationapi.domain.blockeddate.model.BlockedDate;
import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.domain.blockeddate.port.BlockedDatePort;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FakeBlockedDateAdapter implements BlockedDatePort {

    private final List<BlockedDate> blockedDates = new ArrayList<>();

    @Override
    public List<BlockedDate> filter(FilterBlockedDatesQuery query) {
        return blockedDates
            .stream()
            .filter(date -> {
                if (query.getStartDate() != null && date.getBlockedDate().isBefore(query.getStartDate())) {
                    return false;
                }
                if (query.getEndDate() != null && date.getBlockedDate().isAfter(query.getEndDate())) {
                    return false;
                }
                if (query.getType() != null && !query.getType().equals(date.getType())) {
                    return false;
                }
                if (query.getPlacement() != null && !query.getPlacement().equals(date.getPlacement())) {
                    return false;
                }
                if (query.getPriority() != null && !query.getPriority().equals(date.getPriority())) {
                    return false;
                }
                return true;
            })
            .toList();
    }

    @Override
    public void saveBlockedDates(List<BlockedDate> blockedDates) {
        this.blockedDates.addAll(blockedDates);
    }

    @Override
    public void deleteBlockedDates(Types type, Placement placement, Integer priority, List<LocalDate> blockedDates) {
        this.blockedDates.removeIf(date ->
                date.getType().equals(type) &&
                date.getPlacement().equals(placement) &&
                date.getPriority().equals(priority) &&
                blockedDates.contains(date.getBlockedDate())
            );
    }

    @Override
    public boolean existsByParametersAndBlockedDate(
        Types type,
        Placement placement,
        Integer priority,
        LocalDate blockedDate
    ) {
        return blockedDates
            .stream()
            .anyMatch(date ->
                date.getType().equals(type) &&
                date.getPlacement().equals(placement) &&
                date.getPriority().equals(priority) &&
                date.getBlockedDate().equals(blockedDate)
            );
    }

    public void resetData() {
        blockedDates.clear();
    }

    public int getBlockedDatesCount() {
        return blockedDates.size();
    }

    public List<BlockedDate> getStoredBlockedDates() {
        return new ArrayList<>(blockedDates);
    }

    public void addBlockedDate(BlockedDate blockedDate) {
        blockedDates.add(blockedDate);
    }
}

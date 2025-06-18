package com.reservationapi.domain.blockeddates;

import static org.junit.jupiter.api.Assertions.*;

import com.reservationapi.domain.blockeddate.FilterBlockedDatesUseCaseHandler;
import com.reservationapi.domain.blockeddate.model.BlockedDate;
import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.domain.blockeddate.usecase.FilterBlockedDates;
import com.reservationapi.domain.blockeddates.adaptor.FakeBlockedDateAdapter;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterBlockedDatesUseCaseHandlerTest {

    FilterBlockedDatesUseCaseHandler filterBlockedDatesUseCaseHandler;
    FakeBlockedDateAdapter fakeBlockedDatesAdapter = new FakeBlockedDateAdapter();

    @BeforeEach
    void setUp() {
        filterBlockedDatesUseCaseHandler = new FilterBlockedDatesUseCaseHandler(fakeBlockedDatesAdapter);
        fakeBlockedDatesAdapter.resetData();
    }

    @Test
    void should_get_blocked_dates_with_all_filters() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 15))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 16))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.KITCHEN_ICON, Placement.HOMEPAGE, 2, LocalDate.of(2025, 6, 20))
        );

        FilterBlockedDates useCase = FilterBlockedDates
            .builder()
            .startDate(LocalDate.of(2025, 6, 1))
            .endDate(LocalDate.of(2025, 6, 30))
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .build();

        List<BlockedDate> result = filterBlockedDatesUseCaseHandler.handle(useCase);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(date -> date.getType() == Types.BANNER));
        assertTrue(result.stream().allMatch(date -> date.getPlacement() == Placement.SEARCH));
    }

    @Test
    void should_get_empty_list_when_no_blocked_dates_exist() {
        FilterBlockedDates useCase = FilterBlockedDates
            .builder()
            .startDate(LocalDate.of(2025, 12, 1))
            .endDate(LocalDate.of(2025, 12, 31))
            .type(Types.KITCHEN_ICON)
            .placement(Placement.HOMEPAGE)
            .priority(2)
            .build();

        List<BlockedDate> result = filterBlockedDatesUseCaseHandler.handle(useCase);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void should_get_blocked_dates_with_partial_filters() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 15))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.KITCHEN_ICON, Placement.HOMEPAGE, 2, LocalDate.of(2025, 6, 20))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.CHANNEL, 3, LocalDate.of(2025, 7, 10))
        );

        FilterBlockedDates useCase = FilterBlockedDates
            .builder()
            .startDate(LocalDate.of(2025, 6, 1))
            .endDate(LocalDate.of(2025, 6, 30))
            .build();

        List<BlockedDate> result = filterBlockedDatesUseCaseHandler.handle(useCase);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(
            result
                .stream()
                .allMatch(date ->
                    date.getBlockedDate().isAfter(LocalDate.of(2025, 5, 31)) &&
                    date.getBlockedDate().isBefore(LocalDate.of(2025, 7, 1))
                )
        );
    }

    @Test
    void should_get_blocked_dates_filtered_by_type_only() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 15))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.CHANNEL, 3, LocalDate.of(2025, 7, 10))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.KITCHEN_ICON, Placement.HOMEPAGE, 2, LocalDate.of(2025, 6, 20))
        );

        FilterBlockedDates useCase = FilterBlockedDates.builder().type(Types.BANNER).build();

        List<BlockedDate> result = filterBlockedDatesUseCaseHandler.handle(useCase);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(date -> date.getType() == Types.BANNER));
    }

    @Test
    void should_get_all_blocked_dates_when_no_filters_applied() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 15))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.KITCHEN_ICON, Placement.HOMEPAGE, 2, LocalDate.of(2025, 6, 20))
        );

        FilterBlockedDates useCase = FilterBlockedDates.builder().build();

        List<BlockedDate> result = filterBlockedDatesUseCaseHandler.handle(useCase);

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}

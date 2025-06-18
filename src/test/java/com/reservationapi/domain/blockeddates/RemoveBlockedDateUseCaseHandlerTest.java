package com.reservationapi.domain.blockeddates;

import static org.junit.jupiter.api.Assertions.*;

import com.reservationapi.domain.blockeddate.RemoveBlockedDateUseCaseHandler;
import com.reservationapi.domain.blockeddate.model.BlockedDate;
import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.domain.blockeddate.usecase.RemoveBlockedDate;
import com.reservationapi.domain.blockeddates.adaptor.FakeBlockedDateAdapter;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RemoveBlockedDateUseCaseHandlerTest {

    RemoveBlockedDateUseCaseHandler removeBlockedDateUseCaseHandler;
    FakeBlockedDateAdapter fakeBlockedDatesAdapter = new FakeBlockedDateAdapter();

    @BeforeEach
    void setUp() {
        removeBlockedDateUseCaseHandler = new RemoveBlockedDateUseCaseHandler(fakeBlockedDatesAdapter);
        fakeBlockedDatesAdapter.resetData();
    }

    @Test
    void should_remove_blocked_dates_successfully() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 15))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 16))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 17))
        );

        RemoveBlockedDate useCase = RemoveBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(List.of(LocalDate.of(2025, 6, 15), LocalDate.of(2025, 6, 16), LocalDate.of(2025, 6, 17)))
            .build();

        removeBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(0, fakeBlockedDatesAdapter.getBlockedDatesCount());
    }

    @Test
    void should_remove_single_blocked_date() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.KITCHEN_ICON, Placement.HOMEPAGE, 2, LocalDate.of(2025, 6, 20))
        );

        RemoveBlockedDate useCase = RemoveBlockedDate
            .builder()
            .type(Types.KITCHEN_ICON)
            .placement(Placement.HOMEPAGE)
            .priority(2)
            .blockedDates(List.of(LocalDate.of(2025, 6, 20)))
            .build();

        removeBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(0, fakeBlockedDatesAdapter.getBlockedDatesCount());
    }

    @Test
    void should_remove_multiple_blocked_dates() {
        List<LocalDate> dates = List.of(
            LocalDate.of(2025, 12, 1),
            LocalDate.of(2025, 12, 2),
            LocalDate.of(2025, 12, 3),
            LocalDate.of(2025, 12, 25),
            LocalDate.of(2025, 12, 26),
            LocalDate.of(2025, 12, 27),
            LocalDate.of(2025, 12, 28),
            LocalDate.of(2025, 12, 29),
            LocalDate.of(2025, 12, 30),
            LocalDate.of(2025, 12, 31)
        );

        for (LocalDate date : dates) {
            fakeBlockedDatesAdapter.addBlockedDate(BlockedDate.create(Types.BANNER, Placement.CHANNEL, 3, date));
        }

        RemoveBlockedDate useCase = RemoveBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.CHANNEL)
            .priority(3)
            .blockedDates(dates)
            .build();

        removeBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(0, fakeBlockedDatesAdapter.getBlockedDatesCount());
    }

    @Test
    void should_remove_only_matching_blocked_dates() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 15))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.KITCHEN_ICON, Placement.HOMEPAGE, 2, LocalDate.of(2025, 6, 15))
        );
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 16))
        );

        RemoveBlockedDate useCase = RemoveBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(List.of(LocalDate.of(2025, 6, 15)))
            .build();

        removeBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(2, fakeBlockedDatesAdapter.getBlockedDatesCount());
        List<BlockedDate> remaining = fakeBlockedDatesAdapter.getStoredBlockedDates();
        assertTrue(
            remaining
                .stream()
                .anyMatch(date ->
                    date.getType() == Types.KITCHEN_ICON && date.getBlockedDate().equals(LocalDate.of(2025, 6, 15))
                )
        );
        assertTrue(
            remaining
                .stream()
                .anyMatch(date ->
                    date.getType() == Types.BANNER && date.getBlockedDate().equals(LocalDate.of(2025, 6, 16))
                )
        );
    }

    @Test
    void should_remove_dates_from_february() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 2, 28))
        );

        RemoveBlockedDate useCase = RemoveBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(List.of(LocalDate.of(2025, 2, 28)))
            .build();

        removeBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(0, fakeBlockedDatesAdapter.getBlockedDatesCount());
    }

    @Test
    void should_remove_dates_from_leap_year_february() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.CHANNEL, 2, LocalDate.of(2024, 2, 29))
        );

        RemoveBlockedDate useCase = RemoveBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.CHANNEL)
            .priority(2)
            .blockedDates(List.of(LocalDate.of(2024, 2, 29)))
            .build();

        removeBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(0, fakeBlockedDatesAdapter.getBlockedDatesCount());
    }

    @Test
    void should_handle_remove_when_no_dates_exist() {
        RemoveBlockedDate useCase = RemoveBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(List.of(LocalDate.of(2025, 6, 15)))
            .build();

        removeBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(0, fakeBlockedDatesAdapter.getBlockedDatesCount());
    }
}

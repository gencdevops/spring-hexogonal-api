package com.reservationapi.domain.blockeddates;

import static org.junit.jupiter.api.Assertions.*;

import com.reservationapi.domain.blockeddate.CreateBlockedDateUseCaseHandler;
import com.reservationapi.domain.blockeddate.exception.BlockedDateAlreadyExistException;
import com.reservationapi.domain.blockeddate.exception.BlockedDateInvalidPriorityException;
import com.reservationapi.domain.blockeddate.exception.BlockedDatesCannotBeNullException;
import com.reservationapi.domain.blockeddate.model.BlockedDate;
import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.domain.blockeddate.usecase.CreateBlockedDate;
import com.reservationapi.domain.blockeddates.adaptor.FakeBlockedDateAdapter;
import com.reservationapi.domain.blockeddates.adaptor.FakeBlockedDateCreatedEventAdapter;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateBlockedDateUseCaseHandlerTest {

    CreateBlockedDateUseCaseHandler createBlockedDateUseCaseHandler;
    FakeBlockedDateAdapter fakeBlockedDatesAdapter = new FakeBlockedDateAdapter();
    FakeBlockedDateCreatedEventAdapter fakeBlockedDateCreatedEventAdapter = new FakeBlockedDateCreatedEventAdapter();

    @BeforeEach
    void setUp() {
        createBlockedDateUseCaseHandler =
            new CreateBlockedDateUseCaseHandler(fakeBlockedDatesAdapter, fakeBlockedDateCreatedEventAdapter);
        fakeBlockedDatesAdapter.resetData();
        fakeBlockedDateCreatedEventAdapter.reset();
    }

    @Test
    void should_create_blocked_dates_successfully() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(List.of(LocalDate.of(2025, 6, 15), LocalDate.of(2025, 6, 16), LocalDate.of(2025, 6, 17)))
            .build();

        createBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(3, fakeBlockedDatesAdapter.getBlockedDatesCount());
        List<BlockedDate> savedDates = fakeBlockedDatesAdapter.getStoredBlockedDates();
        assertEquals(Types.BANNER, savedDates.get(0).getType());
        assertEquals(Placement.SEARCH, savedDates.get(0).getPlacement());
        assertEquals(1, savedDates.get(0).getPriority());
        assertEquals(1, fakeBlockedDateCreatedEventAdapter.getPublishedEvents().size());
    }

    @Test
    void should_create_single_blocked_date() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.KITCHEN_ICON)
            .placement(Placement.HOMEPAGE)
            .priority(2)
            .blockedDates(List.of(LocalDate.of(2025, 6, 20)))
            .build();

        createBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(1, fakeBlockedDatesAdapter.getBlockedDatesCount());
        List<BlockedDate> savedDates = fakeBlockedDatesAdapter.getStoredBlockedDates();
        assertEquals(LocalDate.of(2025, 6, 20), savedDates.get(0).getBlockedDate());
        assertEquals(1, fakeBlockedDateCreatedEventAdapter.getPublishedEvents().size());
    }

    @Test
    void should_remove_duplicates_when_creating_dates() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(
                List.of(
                    LocalDate.of(2025, 6, 15),
                    LocalDate.of(2025, 6, 15),
                    LocalDate.of(2025, 6, 16),
                    LocalDate.of(2025, 6, 16),
                    LocalDate.of(2025, 6, 17)
                )
            )
            .build();

        createBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(3, fakeBlockedDatesAdapter.getBlockedDatesCount());
        assertEquals(1, fakeBlockedDateCreatedEventAdapter.getPublishedEvents().size());
    }

    @Test
    void should_throw_exception_when_blocked_date_already_exists() {
        fakeBlockedDatesAdapter.addBlockedDate(
            BlockedDate.create(Types.BANNER, Placement.SEARCH, 1, LocalDate.of(2025, 6, 15))
        );

        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(List.of(LocalDate.of(2025, 6, 15)))
            .build();

        assertThrows(BlockedDateAlreadyExistException.class, () -> createBlockedDateUseCaseHandler.handle(useCase));

        assertEquals(1, fakeBlockedDatesAdapter.getBlockedDatesCount());
        assertEquals(0, fakeBlockedDateCreatedEventAdapter.getPublishedEvents().size());
    }

    @Test
    void should_throw_exception_when_priority_is_null() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(null)
            .blockedDates(List.of(LocalDate.of(2025, 6, 15)))
            .build();

        assertThrows(BlockedDateInvalidPriorityException.class, () -> createBlockedDateUseCaseHandler.handle(useCase));

        assertEquals(0, fakeBlockedDatesAdapter.getBlockedDatesCount());
        assertEquals(0, fakeBlockedDateCreatedEventAdapter.getPublishedEvents().size());
    }

    @Test
    void should_throw_exception_when_priority_is_invalid() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(4)
            .blockedDates(List.of(LocalDate.of(2025, 6, 15)))
            .build();

        assertThrows(BlockedDateInvalidPriorityException.class, () -> createBlockedDateUseCaseHandler.handle(useCase));
    }

    @Test
    void should_throw_exception_when_blocked_dates_is_null() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(null)
            .build();

        assertThrows(BlockedDatesCannotBeNullException.class, () -> createBlockedDateUseCaseHandler.handle(useCase));
    }

    @Test
    void should_throw_exception_when_blocked_dates_is_empty() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.SEARCH)
            .priority(1)
            .blockedDates(List.of())
            .build();

        assertThrows(BlockedDatesCannotBeNullException.class, () -> createBlockedDateUseCaseHandler.handle(useCase));
    }

    @Test
    void should_create_multiple_blocked_dates_for_different_months() {
        CreateBlockedDate useCase = CreateBlockedDate
            .builder()
            .type(Types.BANNER)
            .placement(Placement.CHANNEL)
            .priority(3)
            .blockedDates(
                List.of(
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
                )
            )
            .build();

        createBlockedDateUseCaseHandler.handle(useCase);

        assertEquals(10, fakeBlockedDatesAdapter.getBlockedDatesCount());
        List<BlockedDate> savedDates = fakeBlockedDatesAdapter.getStoredBlockedDates();
        assertEquals(Types.BANNER, savedDates.get(0).getType());
        assertEquals(Placement.CHANNEL, savedDates.get(0).getPlacement());
        assertEquals(3, savedDates.get(0).getPriority());
    }
}

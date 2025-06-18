package com.reservationapi.infra.adapters.blockeddate.jpa.repository;

import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.infra.adapters.blockeddate.jpa.entity.BlockedDateEntity;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BlockedDatesRepository
    extends JpaRepository<BlockedDateEntity, Long>, JpaSpecificationExecutor<BlockedDateEntity> {
    @Modifying
    @Query(
        """
            UPDATE BlockedDateEntity bd 
            SET bd.isDeleted = true, bd.updatedAt = CURRENT_TIMESTAMP
            WHERE bd.type = :type
            AND bd.placement = :placement 
            AND bd.priority = :priority
            AND bd.blockedDate IN :blockedDates
            """
    )
    void softDeleteByParametersAndDates(
        @Param("type") Types type,
        @Param("placement") Placement placement,
        @Param("priority") Integer priority,
        @Param("blockedDates") List<LocalDate> blockedDates
    );

    @Query(
        """
            SELECT EXISTS(
                SELECT 1 FROM BlockedDateEntity bd
                WHERE bd.type = :type 
                AND bd.placement = :placement 
                AND bd.priority = :priority
                AND bd.blockedDate = :blockedDate
            )
            """
    )
    boolean existsByParametersAndBlockedDate(
        @Param("type") Types type,
        @Param("placement") Placement placement,
        @Param("priority") Integer priority,
        @Param("blockedDate") LocalDate blockedDate
    );
}

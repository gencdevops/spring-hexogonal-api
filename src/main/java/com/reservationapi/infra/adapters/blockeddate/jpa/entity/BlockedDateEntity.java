package com.reservationapi.infra.adapters.blockeddate.jpa.entity;

import com.reservationapi.domain.blockeddate.model.Placement;
import com.reservationapi.domain.blockeddate.model.Types;
import com.reservationapi.infra.common.entity.AuditingEntity;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "blocked_dates")
@SQLRestriction("is_deleted = false")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlockedDateEntity extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "reservation_id_seq")
    @SequenceGenerator(name = "reservation_id_seq", sequenceName = "reservation_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "blocked_date", nullable = false, columnDefinition = "DATE")
    private LocalDate blockedDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private Types type;

    @Enumerated(EnumType.STRING)
    @Column(name = "placement", nullable = false)
    private Placement placement;

    @Column(name = "priority", nullable = false)
    private Integer priority;

    @Override
    public Serializable getId() {
        return id;
    }
}

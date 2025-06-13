package com.trendyol.reservationapi.infra.adapters.event.jpa.entity;

import com.trendyol.reservationapi.domain.common.event.Event;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Table(name = "domain_events")
@Immutable
@SequenceGenerator(name = "domain_events_id_seq", sequenceName = "domain_events_id_seq")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners({ AuditingEntityListener.class })
public class DomainEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "domain_events_id_seq")
    @SequenceGenerator(name = "domain_events_id_seq", sequenceName = "domain_events_id_seq", allocationSize = 1)
    private long id;

    @Column(name = "aggregatetype")
    private String aggregateType;

    @Column(name = "aggregateid")
    private String aggregateId;

    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb", name = "payload")
    private Event payload;

    @CreatedDate
    @Column(name = "timestamp")
    private long timestamp;

    @Column(name = "correlation_id")
    private String correlationId;
}

package com.reservationapi.infra.adapters.event.jpa.repository;

import com.reservationapi.infra.adapters.event.jpa.entity.DomainEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface EventRepository extends CrudRepository<DomainEvent, Long> {}

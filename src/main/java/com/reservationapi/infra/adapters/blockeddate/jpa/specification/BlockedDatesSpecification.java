package com.reservationapi.infra.adapters.blockeddate.jpa.specification;

import com.reservationapi.domain.blockeddate.dto.FilterBlockedDatesQuery;
import com.reservationapi.infra.adapters.blockeddate.jpa.entity.BlockedDateEntity;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BlockedDatesSpecification {

    private static final String TYPE = "type";
    private static final String PLACEMENT = "placement";
    private static final String PRIORITY = "priority";
    private static final String BLOCKED_DATE = "blockedDate";

    public Specification<BlockedDateEntity> get(FilterBlockedDatesQuery query) {
        return (root, q, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.nonNull(query.getStartDate()) && Objects.nonNull(query.getEndDate())) {
                predicates.add(cb.between(root.get(BLOCKED_DATE), query.getStartDate(), query.getEndDate()));
            } else if (Objects.nonNull(query.getStartDate())) {
                predicates.add(cb.greaterThanOrEqualTo(root.get(BLOCKED_DATE), query.getStartDate()));
            } else if (Objects.nonNull(query.getEndDate())) {
                predicates.add(cb.lessThanOrEqualTo(root.get(BLOCKED_DATE), query.getEndDate()));
            }

            if (Objects.nonNull(query.getType())) {
                predicates.add(cb.equal(root.get(TYPE), query.getType()));
            }

            if (Objects.nonNull(query.getPlacement())) {
                predicates.add(cb.equal(root.get(PLACEMENT), query.getPlacement()));
            }

            if (Objects.nonNull(query.getPriority())) {
                predicates.add(cb.equal(root.get(PRIORITY), query.getPriority()));
            }

            q.orderBy(cb.asc(root.get(BLOCKED_DATE)));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

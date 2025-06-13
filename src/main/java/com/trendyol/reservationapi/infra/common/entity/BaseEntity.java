package com.trendyol.reservationapi.infra.common.entity;

import java.io.Serializable;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 4969381288071476820L;

    public BaseEntity() {}

    public abstract Serializable getId();
}

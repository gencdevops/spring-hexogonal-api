package com.trendyol.reservationapi.infra.common.logger;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class Constants {

    private static final String AUDIT = "audit";

    public static final Marker AUDIT_MARKER = MarkerManager.getMarker(AUDIT);
}

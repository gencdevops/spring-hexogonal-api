package com.trendyol.reservationapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationApiApplicationTests {

    @Test
    void contextLoads() {
        assertThat(ReservationApiApplicationTests.class).isNotNull();
    }
}

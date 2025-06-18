package com.reservationapi;

import com.reservationapi.domain.common.annotation.DomainComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@ServletComponentScan
@SpringBootApplication
@EnableFeignClients
@EnableAsync
@ImportAutoConfiguration({ FeignAutoConfiguration.class })
@ComponentScan(
    includeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION, value = { DomainComponent.class }) }
)
public class ReservationApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReservationApiApplication.class, args);
    }
}

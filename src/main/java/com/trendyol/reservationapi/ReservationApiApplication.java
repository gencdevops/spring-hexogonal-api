package com.trendyol.reservationapi;

import com.trendyol.dynamic.configuration.processor.DynamicConfigurationProperties;
import com.trendyol.dynamic.configuration.processor.EnableDynamicConfiguration;
import com.trendyol.mpc.consul.configuration.processor.EnableConsulProcessor;
import com.trendyol.reservationapi.domain.common.annotation.DomainComponent;
import com.trendyol.reservationapi.infra.common.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableDynamicConfiguration
@EnableConfigurationProperties({ ApplicationConfig.class })
@DynamicConfigurationProperties("./app/config/configs.json")
@EnableConsulProcessor({ "./app/config/configs.json", "./app/config/secrets.json" })
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

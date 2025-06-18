package com.reservationapi.infra.common.configuration;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "business")
@EnableConfigurationProperties({ ApplicationConfig.class })
public class ApplicationConfig {}

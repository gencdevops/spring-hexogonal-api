package com.trendyol.reservationapi.infra.common.configuration;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties("security.api")
@EnableConfigurationProperties(BasicAuthConfig.class)
@Configuration
public class BasicAuthConfig {

    @Getter
    public enum TokenType {
        INTERNAL("localcommerce-ads-internal");

        private final String type;

        TokenType(String type) {
            this.type = type;
        }
    }

    private Map<String, String> tokens;
}

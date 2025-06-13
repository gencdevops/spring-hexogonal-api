package com.trendyol.reservationapi.infra.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties("security.slack")
@EnableConfigurationProperties(SlackConfig.class)
@Configuration
public class SlackConfig {

    private String webhookUrl;
    private String successChannelToken;
    private String failedChannelToken;

    public String getChannelToken(boolean isSucceeded) {
        if (isSucceeded) return successChannelToken;

        return failedChannelToken;
    }
}

package com.reservationapi.infra.common.configuration;

import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import java.util.List;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private final List<Parameter> parameters = List.of(new HeaderParameter().name("x-correlationId").required(true));

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi
            .builder()
            .group("custom-headers")
            .addOperationCustomizer((operation, handlerMethod) -> {
                if (operation.getParameters() == null) {
                    operation.parameters(parameters);
                } else {
                    operation.getParameters().addAll(parameters);
                }

                return operation;
            })
            .build();
    }
}

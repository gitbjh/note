package com.example.spring.orderservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfig {

    @Bean // Spring Container의 관리대상으로 지정
    WebClient webClient(
            ClientProperties clientProperties,
            WebClient.Builder webClientBuilder
    ) {
        return webClientBuilder
                .baseUrl(String.valueOf(clientProperties.catalogServiceUri())) // @NotNull을 썻기 때문에 toString()사용해도 됨
                .build();
    }

}

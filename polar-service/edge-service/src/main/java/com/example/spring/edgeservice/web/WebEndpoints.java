package com.example.spring.edgeservice.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@Configuration
public class WebEndpoints {

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route()
                // 조회하는 부분은 특수하게 빈 값을 주더라도 치명적인 상황이 없음
                // PUT의 경우 POST에 비해 덜 치명적이라고 생각해 임시로 GET처럼 처리 > *상황에 따라 다르게 처리할 것
                .GET(
                        "/service-fallback",
                        request -> ServerResponse.ok().body(Mono.just(""),String.class)
                )
                .POST(
                        "/service-fallback",
                        request -> ServerResponse.status(SERVICE_UNAVAILABLE).build()
                )
                .PUT(
                        "/service-fallback",
                        request -> ServerResponse.ok().body(Mono.just(""),String.class)
                )
                .DELETE(
                        "/service-fallback",
                        request -> ServerResponse.status(SERVICE_UNAVAILABLE).build()
                )
                .build();

    }

}

package com.example.spring.dispatcherservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DispatchingFunctions {

    private static final Logger log = LoggerFactory.getLogger(DispatchingFunctions.class);

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {
//        return new Function<OrderAcceptedMessage, Long>() {
//            @Override
//            public Long apply(OrderAcceptedMessage orderAcceptedMessage) {
//                log.info("The order with id {} is packed." + orderAcceptedMessage.orderId());
//                return orderAcceptedMessage.orderId();
//            }
//        }; 아래 코드와 동일
        return orderAcceptedMessage -> {
            log.info("The order with id {} is packed.", orderAcceptedMessage.orderId());
            return orderAcceptedMessage.orderId();
        };
    }

    @Bean // stream 단위로 움직이기 때문에 flux 사용 > 한 번에 여러 data 들어오고 나갈 수 있다.
    public Function<Flux<Long>, Flux<OrderAcceptedMessage>> label() {
        return orderFlux -> orderFlux.map( orderId -> {
            log.info("The order with id {} is labeled.", orderId);
            return new OrderAcceptedMessage(orderId);
        });
    }

}

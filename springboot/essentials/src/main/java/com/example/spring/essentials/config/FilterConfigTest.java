package com.example.spring.essentials.config;

import com.example.spring.essentials.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfigTest {

    @Bean
    public FilterRegistrationBean<LoggingFilter> filterRegistrationBeanTest() {
        FilterRegistrationBean<LoggingFilter> filterRegistration = new FilterRegistrationBean<>();

        filterRegistration.setFilter(new LoggingFilter());
        filterRegistration.addUrlPatterns("/api/*");
        filterRegistration.setOrder(1);

        return filterRegistration;
    }
}

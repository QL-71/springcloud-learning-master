package com.example.architecturemaster.common.config.CrossOriginHandler;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class AppConfig {
    @Resource
    private CorsFilter corsFilter;

    @Bean
    public FilterRegistrationBean<CorsFilter> filterRegistrationBean() {
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(corsFilter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}

package com.example.architecturemaster.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Description:
 *
 */
@Configuration
public class FeignConfig {

    @Bean
    Logger.Level level() {
        return Logger.Level.FULL;
    }

}

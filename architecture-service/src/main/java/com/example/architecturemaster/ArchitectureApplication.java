package com.example.architecturemaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ArchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchitectureApplication.class, args);
    }

}

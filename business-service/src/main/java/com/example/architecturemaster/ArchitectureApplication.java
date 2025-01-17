package com.example.architecturemaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Description:
 * 
 * @date 2019/12/18 14:39
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ArchitectureApplication {

    public static void main(String[] args) {
        SpringApplication.run(ArchitectureApplication.class, args);
    }

}

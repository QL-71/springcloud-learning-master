package com.example.architecturemaster.common.system;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    private License license(){
        return new License().name("MIT").url("https://opensource.org/licenses/MIT");
    }
    private Info info(){
        return new Info().title("软件设计与体系结构课程项目").description("分层架构模型设计").version("v3.1.0").license(this.license());
    }

    private ExternalDocumentation externalDocumentation(){
        return new ExternalDocumentation().description("xxx").url("abc");
    }

    @Bean
    public OpenAPI creareOpenAPI(){
        return new OpenAPI().info(info()).externalDocs(externalDocumentation());
    }
}

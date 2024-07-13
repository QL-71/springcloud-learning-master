package com.example.architecturemaster.common.config.authConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/Img/**").addResourceLocations("file:D:/software engineering/united/architecture/architecture-master/static/Img/");
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(jwtInterceptor()).addPathPatterns("/**").excludePathPatterns("/Img/**"); //拦截路径
        super.addInterceptors(registry);
    }

    @Bean
    public JwtInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}



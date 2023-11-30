package com.fciencias.freshbowl.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fciencias.freshbowl.services.validator.AuthValidator;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private AuthValidator authValidator;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authValidator);
    }

}

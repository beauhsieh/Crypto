package com.crypto.crypto.configurations;

import com.crypto.crypto.configurations.interceptor.LoggerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@PropertySources({@PropertySource("classpath:application.properties"),
        @PropertySource(value = "file:./application-prod.properties", ignoreResourceNotFound = true)})
public class WebMvcConfig implements WebMvcConfigurer {

    private final String FILE_PREFIX = "file:///";

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/").addResourceLocations("/**");


    }
    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(new LoggerInterceptor()).order(5);
    }

    @Override
    public void addCorsMappings(final CorsRegistry registry) {
    }

}
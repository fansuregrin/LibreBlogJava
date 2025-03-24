package org.fansuregrin.config;

import org.fansuregrin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public InterceptorConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns(List.of(
                "/category/all", "/category/id/{id:[0-9]+}", "/category/slug/{slug}"))
            .excludePathPatterns(List.of(
                "/tag/all", "/tag/id/{id:[0-9]+}", "/tag/slug/{slug}", "/tag/list"))
            .excludePathPatterns(List.of(
                "/user/login", "/user/register", "/user/{id:[0-9]+}"))
            .excludePathPatterns("/article/list");
    }

}

package com.kapas.user.config;

import com.kapas.user.interceptors.ApiInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Component
public class InterceptorConfig implements WebMvcConfigurer {

    private final ApiInterceptor apiInterceptor;
    private final String[] nonUserPathPatterns = new String[]{"/login", "/register"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor).addPathPatterns("/**").excludePathPatterns(nonUserPathPatterns);
    }
}

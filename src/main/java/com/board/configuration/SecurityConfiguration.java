package com.board.configuration;

import com.board.interceptor.HttpInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Kyudong on 2017. 11. 17..
 */

@Configuration
public class SecurityConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    HttpInterceptor httpInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor)
                .addPathPatterns("/auth/login")
//                .addPathPatterns("/board/create")
//                .addPathPatterns("/board/getRegionList")
                .excludePathPatterns("/user/create");

    }
}

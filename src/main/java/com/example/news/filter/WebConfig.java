package com.example.news.filter;

import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<Filter> loginFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();

        filterRegistrationBean.setFilter(new LoginFilter()); // 필터 등록
        filterRegistrationBean.setOrder(1); //순서1
        filterRegistrationBean.addUrlPatterns("/*"); // 전체 URL에 필터 적용

        return filterRegistrationBean;
    }

}

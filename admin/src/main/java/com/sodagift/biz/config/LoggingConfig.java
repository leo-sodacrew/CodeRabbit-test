package com.sodagift.biz.config;

import ch.qos.logback.classic.helpers.MDCInsertingServletFilter;
import com.sodagift.common.log.UserLoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggingConfig {

    @Bean
    public FilterRegistrationBean<MDCInsertingServletFilter> mdcInsertingFilter() {
        FilterRegistrationBean<MDCInsertingServletFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MDCInsertingServletFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<UserLoggingFilter> userLoggingFilter() {
        FilterRegistrationBean<UserLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new UserLoggingFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(2);
        return registrationBean;
    }
}

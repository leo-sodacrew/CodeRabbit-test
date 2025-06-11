package com.sodagift.biz.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableWebSecurity
@RestController
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final OAuth2Service oAuth2Service;
    @Value("${app.front-url}")
    private String redirectHost;

    public SecurityConfiguration(OAuth2Service oAuth2Service) {
        this.oAuth2Service = oAuth2Service;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer -> headersConfigurer.frameOptions().disable())
                .authorizeRequests(requests -> requests
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers(HttpMethod.GET, "/management/health", "/doc", "/oauth2/**", "/").permitAll()
                        .antMatchers(HttpMethod.GET, "/auth/user").permitAll()
                        .antMatchers(HttpMethod.GET, "/biz/kyc").permitAll()
                        .antMatchers("/static/**", "/favicon.ico", "/manifest.json").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .loginProcessingUrl("/auth/oauth2/login/**")
                        .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2Service))
                        .defaultSuccessUrl(redirectHost + "/login-success")
                        .failureUrl(redirectHost + "/login")
                );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

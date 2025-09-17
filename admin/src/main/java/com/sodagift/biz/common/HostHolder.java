package com.sodagift.biz.common;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HostHolder {

    public static String HOST;

    @Value("${app.base-url}")
    private String value;

    @PostConstruct
    public void init() {
        HOST = value;
    }
}

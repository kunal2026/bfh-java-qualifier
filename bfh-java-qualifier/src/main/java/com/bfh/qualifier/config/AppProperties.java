package com.bfh.qualifier.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Value("${app.name}")
    private String name;

    @Value("${app.regNo}")
    private String regNo;

    @Value("${app.email}")
    private String email;

    @Value("${app.generateWebhookUrl}")
    private String generateWebhookUrl;

    public String getName() { return name; }
    public String getRegNo() { return regNo; }
    public String getEmail() { return email; }
    public String getGenerateWebhookUrl() { return generateWebhookUrl; }
}

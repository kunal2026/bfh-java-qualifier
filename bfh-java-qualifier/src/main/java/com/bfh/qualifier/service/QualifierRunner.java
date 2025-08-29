package com.bfh.qualifier.service;

import com.bfh.qualifier.config.AppProperties;
import com.bfh.qualifier.model.GenerateWebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class QualifierRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(QualifierRunner.class);

    @Autowired
    private AppProperties props;

    private final SQLSolverService sqlSolver = new SQLSolverService();
    private final WebhookClientService client = new WebhookClientService();

    @Override
    public void run(String... args) {
        String name  = envOr("APP_NAME",  props.getName());
        String regNo = envOr("APP_REGNO", props.getRegNo());
        String email = envOr("APP_EMAIL", props.getEmail());

        log.info("Starting Qualifier flow for regNo: {}", regNo);

        // Step 1: Generate webhook
        GenerateWebhookResponse resp = client.generateWebhook(
                props.getGenerateWebhookUrl(), name, regNo, email);

        if (resp == null || resp.getWebhook() == null || resp.getAccessToken() == null) {
            log.error("Missing webhook or accessToken. Exiting.");
            return;
        }

        String webhook = resp.getWebhook();
        String token = resp.getAccessToken();
        log.info("Received webhook URL and token.");

        // Step 2: Decide question by last two of regNo
        int lastTwoDigits = extractLastTwoDigits(regNo);
        boolean isOdd = (lastTwoDigits % 2 == 1);
        String finalQuery = isOdd ? sqlSolver.solveQuestion1() : sqlSolver.solveQuestion2();

        log.info("Determined question: {} ({})", isOdd ? "Q1" : "Q2", lastTwoDigits);

        // Step 3: Submit final query
        boolean ok = client.submitFinalQuery(webhook, token, finalQuery);
        if (ok) {
            log.info("Flow completed successfully.");
        } else {
            log.error("Flow finished with errors.");
        }
    }

    private String envOr(String key, String fallback) {
        String v = System.getenv(key);
        return (v == null || v.isBlank()) ? fallback : v;
    }

    private int extractLastTwoDigits(String regNo) {
        StringBuilder digits = new StringBuilder();
        for (int i = regNo.length() - 1; i >= 0 && digits.length() < 2; i--) {
            char c = regNo.charAt(i);
            if (Character.isDigit(c)) digits.insert(0, c);
        }
        String s = digits.toString();
        if (s.isEmpty()) return 0;
        return Integer.parseInt(s);
    }
}

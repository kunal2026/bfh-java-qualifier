package com.bfh.qualifier.service;

import com.bfh.qualifier.model.GenerateWebhookRequest;
import com.bfh.qualifier.model.GenerateWebhookResponse;
import com.bfh.qualifier.model.SubmitRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class WebhookClientService {

    private static final Logger log = LoggerFactory.getLogger(WebhookClientService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public GenerateWebhookResponse generateWebhook(String url, String name, String regNo, String email) {
        try {
            GenerateWebhookRequest req = new GenerateWebhookRequest(name, regNo, email);
            ResponseEntity<GenerateWebhookResponse> resp =
                    restTemplate.postForEntity(url, req, GenerateWebhookResponse.class);

            if (resp.getStatusCode().is2xxSuccessful() && resp.getBody() != null) {
                log.info("Generated webhook successfully.");
                return resp.getBody();
            } else {
                log.error("Failed generating webhook. Status: {}", resp.getStatusCode());
            }
        } catch (RestClientException ex) {
            log.error("Error generating webhook: {}", ex.getMessage());
        }
        return null;
    }

    public boolean submitFinalQuery(String webhookUrl, String token, String finalQuery) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", token);

            SubmitRequest body = new SubmitRequest(finalQuery);
            HttpEntity<SubmitRequest> req = new HttpEntity<>(body, headers);

            ResponseEntity<String> resp = restTemplate.postForEntity(webhookUrl, req, String.class);

            if (resp.getStatusCode().is2xxSuccessful()) {
                log.info("Submitted finalQuery successfully. Response: {}", resp.getBody());
                return true;
            } else {
                log.error("Submission failed. Status: {}, Response: {}", resp.getStatusCode(), resp.getBody());
                return false;
            }
        } catch (RestClientException ex) {
            log.error("Error submitting finalQuery: {}", ex.getMessage());
            return false;
        }
    }
}

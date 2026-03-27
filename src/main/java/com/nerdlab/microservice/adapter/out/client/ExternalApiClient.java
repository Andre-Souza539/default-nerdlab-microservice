package com.nerdlab.microservice.adapter.out.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stub for external API client (Feign/WebClient).
 * Replace this implementation with actual HTTP client when needed.
 */
@Component
public class ExternalApiClient {

    private static final Logger log = LoggerFactory.getLogger(ExternalApiClient.class);

    /**
     * Makes a call to an external API.
     *
     * @param endpoint the target endpoint
     * @return the response body as a string
     */
    public String call(String endpoint) {
        log.info("Calling external API: endpoint={}", endpoint);
        // TODO: Implement actual external API call (Feign/WebClient)
        return "{}";
    }
}

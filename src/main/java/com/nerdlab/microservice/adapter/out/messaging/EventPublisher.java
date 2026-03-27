package com.nerdlab.microservice.adapter.out.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Stub for event publishing (Kafka/RabbitMQ).
 * Replace this implementation with actual messaging client when needed.
 */
@Component
public class EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(EventPublisher.class);

    /**
     * Publishes a domain event.
     *
     * @param eventType the type of event (e.g., "USER_CREATED")
     * @param payload   the event payload
     */
    public void publish(String eventType, Object payload) {
        log.info("Publishing event: type={}, payload={}", eventType, payload);
        // TODO: Implement actual messaging (Kafka/RabbitMQ) integration
    }
}

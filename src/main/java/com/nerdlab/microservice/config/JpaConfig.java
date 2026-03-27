package com.nerdlab.microservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * JPA configuration enabling auditing support for @CreatedDate and @LastModifiedDate.
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {
}

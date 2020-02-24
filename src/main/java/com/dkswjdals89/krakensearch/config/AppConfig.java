package com.dkswjdals89.krakensearch.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@EnableJpaAuditing
@Configuration
public class AppConfig {
}

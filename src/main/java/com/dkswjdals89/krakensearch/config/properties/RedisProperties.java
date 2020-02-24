package com.dkswjdals89.krakensearch.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("kraken.redis")
public class RedisProperties {
    private String host;
    private int port;
}

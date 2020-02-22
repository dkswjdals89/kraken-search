package com.dkswjdals89.krakensearch.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("book")
public class KrakenSearchProperties {
}

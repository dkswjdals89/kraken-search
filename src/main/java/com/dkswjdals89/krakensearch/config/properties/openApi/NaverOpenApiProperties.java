package com.dkswjdals89.krakensearch.config.properties.openApi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("naver-open-api")
public class NaverOpenApiProperties {
    private String url;
    private String clientId;
    private String clientSecret;
}

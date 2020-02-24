package com.dkswjdals89.krakensearch.config.properties.openApi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("kakao-open-api")
public class KakaoOpenApiProperties {
    private String url;
    private String key;
}

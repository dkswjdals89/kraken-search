package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.config.BookSearchConfig;
import com.dkswjdals89.krakensearch.config.openApi.KakaoOpenApiConfig;
import com.dkswjdals89.krakensearch.service.openApi.impl.KakaoSearchOpenApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AccountController {
    private final KakaoOpenApiConfig kakaoOpenApiConfig;
    private final BookSearchConfig bookSearchConfig;
    private final KakaoSearchOpenApiService kakaoOpenApiService;

    @GetMapping("/account")
    public String Test() {
        return kakaoOpenApiConfig.toString();
    }
}

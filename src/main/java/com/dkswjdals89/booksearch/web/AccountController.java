package com.dkswjdals89.booksearch.web;

import com.dkswjdals89.booksearch.config.BookSearchConfig;
import com.dkswjdals89.booksearch.config.openApi.KakaoOpenApiConfig;
import com.dkswjdals89.booksearch.service.openApi.impl.KakaoSearchOpenApiService;
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

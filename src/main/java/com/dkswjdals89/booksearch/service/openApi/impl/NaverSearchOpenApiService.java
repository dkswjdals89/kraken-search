package com.dkswjdals89.booksearch.service.openApi.impl;

import com.dkswjdals89.booksearch.config.openApi.NaverOpenApiConfig;
import com.dkswjdals89.booksearch.constant.OpenApiType;
import com.dkswjdals89.booksearch.service.openApi.SearchOpenApiService;
import com.dkswjdals89.booksearch.web.dto.NaverSearchBookResponseDto;
import com.dkswjdals89.booksearch.web.dto.SearchBookRequestDto;
import com.dkswjdals89.booksearch.web.dto.SearchBookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service(OpenApiType.NAVER)
public class NaverSearchOpenApiService implements SearchOpenApiService {
    private final RestTemplate restTemplate;
    private final NaverOpenApiConfig naverOpenApiConfig;

    @Override
    public SearchBookResponseDto searchBook(SearchBookRequestDto requestDto) {
        String uri = naverOpenApiConfig.getUrl() + "/v1/search/book.json";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", naverOpenApiConfig.getClientId());
        headers.add("X-Naver-Client-Secret", naverOpenApiConfig.getClientSecret());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("query",requestDto.getKeyword())
                .build();
        ResponseEntity<NaverSearchBookResponseDto> temp = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, NaverSearchBookResponseDto.class);
        return null;
    }
}

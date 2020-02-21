package com.dkswjdals89.krakensearch.service.openApi.impl;

import com.dkswjdals89.krakensearch.config.openApi.KakaoOpenApiConfig;
import com.dkswjdals89.krakensearch.constant.OpenApiType;
import com.dkswjdals89.krakensearch.service.openApi.SearchOpenApiService;
import com.dkswjdals89.krakensearch.web.dto.KakaoSearchBookResponseDto;
import com.dkswjdals89.krakensearch.web.dto.SearchBookRequestDto;
import com.dkswjdals89.krakensearch.web.dto.SearchBookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Service(OpenApiType.KAKAO)
public class KakaoSearchOpenApiService implements SearchOpenApiService {
    private final RestTemplate restTemplate;
    private final KakaoOpenApiConfig kakaoOpenApiConfig;

    @Override
    public SearchBookResponseDto searchBook(SearchBookRequestDto requestDto) {
        String uri = kakaoOpenApiConfig.getUrl() + "/v3/search/book";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "KakaoAK " + kakaoOpenApiConfig.getKey());
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("query",requestDto.getKeyword())
                .build();

        ResponseEntity<KakaoSearchBookResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, KakaoSearchBookResponseDto.class);
        return responseEntity.getBody().convertSearchBookResponse();
    }
}

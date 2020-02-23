package com.dkswjdals89.krakensearch.service.openApi.impl;

import com.dkswjdals89.krakensearch.config.properties.openApi.KakaoOpenApiConfig;
import com.dkswjdals89.krakensearch.constant.OpenApiType;
import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.BookDto;
import com.dkswjdals89.krakensearch.dto.KakaoSearchBookResponseDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.openApi.SearchOpenApiService;
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
    public BasePagingListResponseDto<BookDto> searchBook(SearchRequestDto requestDto) {
        String uri = kakaoOpenApiConfig.getUrl() + "/v3/search/book";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "KakaoAK " + kakaoOpenApiConfig.getKey());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("query", requestDto.getKeyword())
                .queryParam("page", requestDto.getPage())
                .queryParam("size", requestDto.getSize())
                .build();

        ResponseEntity<KakaoSearchBookResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, KakaoSearchBookResponseDto.class);
        return responseEntity.getBody().convertSearchBookResponse(requestDto);
    }
}

package com.dkswjdals89.krakensearch.service.openApi.impl;

import com.dkswjdals89.krakensearch.config.properties.openApi.NaverOpenApiConfig;
import com.dkswjdals89.krakensearch.constant.NaverSearchApiPath;
import com.dkswjdals89.krakensearch.constant.OpenApiType;
import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.BookDto;
import com.dkswjdals89.krakensearch.dto.NaverSearchBookResponseDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.openApi.SearchOpenApiService;
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
    public BasePagingListResponseDto<BookDto> searchBook(SearchRequestDto requestDto) {
        String uri = naverOpenApiConfig.getUrl() + NaverSearchApiPath.BOOK_SEARCH;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-Naver-Client-Id", naverOpenApiConfig.getClientId());
        headers.add("X-Naver-Client-Secret", naverOpenApiConfig.getClientSecret());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(uri)
                .queryParam("query", requestDto.getKeyword())
                .queryParam("start", requestDto.getPage())
                .queryParam("display", requestDto.getSize())
                .build();

        ResponseEntity<NaverSearchBookResponseDto> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, NaverSearchBookResponseDto.class);
        return responseEntity.getBody().convertSearchBookResponse(requestDto);
    }
}

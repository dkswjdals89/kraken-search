package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.config.properties.openApi.KakaoOpenApiProperties;
import com.dkswjdals89.krakensearch.constant.KakaoSearchApiPath;
import com.dkswjdals89.krakensearch.dto.search.KakaoSearchBookResponseDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.openApi.impl.KakaoSearchOpenApiService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KakaoSearchOpenApiServiceTest {
    @Autowired
    KakaoSearchOpenApiService kakaoOpenApiService;

    @Autowired
    KakaoOpenApiProperties kakaoOpenApiProperties;

    @MockBean
    RestTemplate restTemplate;

    KakaoSearchBookResponseDto dummyResponseData;

    SearchRequestDto requestDto;

    @BeforeEach
    public void initKakaoResponseDummy() {
        KakaoSearchBookResponseDto.Meta meta = KakaoSearchBookResponseDto.Meta.builder()
                .totalCount(1)
                .pageableCount(1)
                .isEnd(false)
                .build();
        List<KakaoSearchBookResponseDto.Documents> documents = Arrays.asList(
                KakaoSearchBookResponseDto.Documents.builder()
                        .title("스프링")
                        .datetime(LocalDate.now())
                        .price(30000)
                .build()
        );
        dummyResponseData = KakaoSearchBookResponseDto.builder()
                .meta(meta)
                .documents(documents)
                .build();
    }

    @BeforeEach
    public void initDummyRequestData() {
        requestDto = SearchRequestDto.builder()
                .keyword("어린왕자")
                .page(1)
                .size(10)
                .build();
    }

    @AfterEach
    public void afterMockReset() {
        reset(restTemplate);
    }

    @Test
    @DisplayName("카카오 openAPI 도서 검색 API 주소로 호출 해야 한다.")
    public void checkSearchBookApiPath() {
        String expectedApiPath = kakaoOpenApiProperties.getUrl() + KakaoSearchApiPath.BOOK_SEARCH;

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        when(restTemplate.exchange(argument.capture(), any(), any(), eq(KakaoSearchBookResponseDto.class)))
                .thenReturn(new ResponseEntity<>(dummyResponseData, HttpStatus.OK));

        kakaoOpenApiService.searchBook(requestDto);

        assertThat(argument.getValue(), startsWith(expectedApiPath));
    }

    @Test
    @DisplayName("요청 파라미터를 포함하여 호출해야 한다.")
    public void checkSearchBookParameter() {
        String apiPath = kakaoOpenApiProperties.getUrl() + KakaoSearchApiPath.BOOK_SEARCH;
        String expectedApiPathWithParam = UriComponentsBuilder.fromHttpUrl(apiPath)
                .queryParam("query", requestDto.getKeyword())
                .queryParam("page", requestDto.getPage())
                .queryParam("size", requestDto.getSize())
                .build().toUriString();

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        when(restTemplate.exchange(argument.capture(), any(), any(), eq(KakaoSearchBookResponseDto.class)))
                .thenReturn(new ResponseEntity<>(dummyResponseData, HttpStatus.OK));
        kakaoOpenApiService.searchBook(requestDto);

        assertThat(argument.getValue(), startsWith(expectedApiPathWithParam));
    }

    @Test
    @DisplayName("카카오 API 인증키를 해더에 포함하여 요청해야 한다.")
    public void addAuthorizationKeyToHeader() {
        String expectedAuth = "KakaoAK " + kakaoOpenApiProperties.getKey();
        ArgumentCaptor<HttpEntity> argument = ArgumentCaptor.forClass(HttpEntity.class);
        when(restTemplate.exchange(anyString(), any(), argument.capture(), eq(KakaoSearchBookResponseDto.class)))
                .thenReturn(new ResponseEntity<>(dummyResponseData, HttpStatus.OK));
        kakaoOpenApiService.searchBook(requestDto);

        String requestAuth = argument.getValue().getHeaders().get("Authorization").get(0);
        assertThat(requestAuth, is(equalTo(expectedAuth)));
    }
}

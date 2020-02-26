package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.config.properties.openApi.NaverOpenApiProperties;
import com.dkswjdals89.krakensearch.constant.NaverSearchApiPath;
import com.dkswjdals89.krakensearch.dto.search.NaverSearchBookResponseDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.openApi.impl.NaverSearchOpenApiService;
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
public class NaverSearchOpenApiServiceTest {
    @Autowired
    NaverSearchOpenApiService naverOpenApiService;

    @Autowired
    NaverOpenApiProperties naverOpenApiProperties;

    @MockBean
    RestTemplate restTemplate;

    NaverSearchBookResponseDto dummyResponseData;

    SearchRequestDto requestDto;

    @BeforeEach
    public void initNaverResponseDummy() {
        List<NaverSearchBookResponseDto.Items> items = Arrays.asList(
                NaverSearchBookResponseDto.Items.builder()
                        .title("자바 이펙티브")
                        .pubdate(LocalDate.now())
                        .price(30000)
                        .build()
        );
        dummyResponseData = NaverSearchBookResponseDto.builder()
                .start(0)
                .display(10)
                .total(1)
                .items(items)
                .build();
    }

    @BeforeEach
    public void initDummyRequestData() {
        requestDto = SearchRequestDto.builder()
                .keyword("자바")
                .page(1)
                .size(10)
                .build();
    }

    @AfterEach
    public void afterMockReset() {
        reset(restTemplate);
    }

    @Test
    @DisplayName("네이버 openAPI 도서 검색 API 주소로 호출 해야 한다.")
    public void checkSearchBookApiPath() {
        String expectedApiPath = naverOpenApiProperties.getUrl() + NaverSearchApiPath.BOOK_SEARCH;

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        when(restTemplate.exchange(argument.capture(), any(), any(), eq(NaverSearchBookResponseDto.class)))
                .thenReturn(new ResponseEntity<>(dummyResponseData, HttpStatus.OK));

        naverOpenApiService.searchBook(requestDto);

        assertThat(argument.getValue(), startsWith(expectedApiPath));
    }

    @Test
    @DisplayName("요청 파라미터를 포함하여 호출해야 한다.")
    public void checkSearchBookParameter() {
        String apiPath = naverOpenApiProperties.getUrl() + NaverSearchApiPath.BOOK_SEARCH;
        String expectedApiPathWithParam = UriComponentsBuilder.fromHttpUrl(apiPath)
                .queryParam("query", requestDto.getKeyword())
                .queryParam("start", requestDto.getPage())
                .queryParam("display", requestDto.getSize())
                .build().toUriString();

        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        when(restTemplate.exchange(argument.capture(), any(), any(), eq(NaverSearchBookResponseDto.class)))
                .thenReturn(new ResponseEntity<>(dummyResponseData, HttpStatus.OK));
        naverOpenApiService.searchBook(requestDto);

        assertThat(argument.getValue(), startsWith(expectedApiPathWithParam));
    }

    @Test
    @DisplayName("Naver Client ID와 Secret을 해더에 포함하여 요청해야 한다.")
    public void addAuthorizationKeyToHeader() {
        ArgumentCaptor<HttpEntity> argument = ArgumentCaptor.forClass(HttpEntity.class);
        String expectedClientId = naverOpenApiProperties.getClientId();
        String expectedSecret = naverOpenApiProperties.getClientSecret();

        when(restTemplate.exchange(anyString(), any(), argument.capture(), eq(NaverSearchBookResponseDto.class)))
                .thenReturn(new ResponseEntity<>(dummyResponseData, HttpStatus.OK));
        naverOpenApiService.searchBook(requestDto);

        String requestClientId = argument.getValue().getHeaders().get("X-Naver-Client-Id").get(0);
        String requestClientSecret = argument.getValue().getHeaders().get("X-Naver-Client-Secret").get(0);
        assertThat(requestClientId, is(equalTo(expectedClientId)));
        assertThat(requestClientSecret, is(equalTo(expectedSecret)));
    }
}

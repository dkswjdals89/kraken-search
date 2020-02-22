package com.dkswjdals89.krakensearch.service.search;

import com.dkswjdals89.krakensearch.service.openApi.impl.KakaoSearchOpenApiService;
import com.dkswjdals89.krakensearch.service.openApi.impl.NaverSearchOpenApiService;
import com.dkswjdals89.krakensearch.dto.search.SearchBookRequestDto;
import com.dkswjdals89.krakensearch.dto.search.SearchBookResponseDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {
    @Autowired
    SearchService searchService;

    @MockBean
    KakaoSearchOpenApiService kakaoSearchOpenApiService;

    @MockBean
    NaverSearchOpenApiService naverSearchOpenApiService;

    @Test
    public void searchBookCallKakao() {
        // Given
        SearchBookRequestDto requestDto = SearchBookRequestDto.builder()
                .keyword("토비의 스프링")
                .page(1)
                .size(10)
                .build();

        // When
        when(kakaoSearchOpenApiService.searchBook(requestDto))
                .thenReturn(new SearchBookResponseDto());

        searchService.searchBook(requestDto);

        verify(kakaoSearchOpenApiService, times(1))
                .searchBook(requestDto);
    }

    @Test
    public void callNaverApiOfKakaoApiError() {
        // Given
        SearchBookRequestDto requestDto = SearchBookRequestDto.builder()
                .keyword("스프링 인 액션")
                .page(1)
                .size(10)
                .build();
        // When
        when(kakaoSearchOpenApiService.searchBook(requestDto))
                .thenThrow(new RuntimeException("서버 응답이 없어용"));
        searchService.searchBook(requestDto);

        verify(naverSearchOpenApiService)
                .searchBook(requestDto);
    }
}

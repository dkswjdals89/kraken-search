package com.dkswjdals89.krakensearch.service.search;

import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.history.SearchHistoryService;
import com.dkswjdals89.krakensearch.service.openApi.impl.KakaoSearchOpenApiService;
import com.dkswjdals89.krakensearch.service.openApi.impl.NaverSearchOpenApiService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SearchServiceTest {
    @Autowired
    SearchService searchService;

    @MockBean
    KakaoSearchOpenApiService kakaoSearchOpenApiService;

    @MockBean
    NaverSearchOpenApiService naverSearchOpenApiService;

    @MockBean
    SearchHistoryService searchHistoryService;

    @Test
    @DisplayName("검색 요청 시 카카오 Open API를 사용하여 검색해야 한다.")
    public void searchBookCallKakao() {
        // Given
        SearchRequestDto requestDto = SearchRequestDto.builder()
                .keyword("토비의 스프링")
                .page(1)
                .size(10)
                .build();

        // When
        when(kakaoSearchOpenApiService.searchBook(requestDto))
                .thenReturn(new BasePagingListResponseDto<>());

        searchService.searchBook(requestDto);

        verify(kakaoSearchOpenApiService, times(1))
                .searchBook(requestDto);
    }

    @Test
    @DisplayName("검색 요청 시 카카오 Open API에 오류가 발생한 경우, 네이버 Open API로 검색해야 한다.")
    public void callNaverApiOfKakaoApiError() {
        // Given
        SearchRequestDto requestDto = SearchRequestDto.builder()
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

package com.dkswjdals89.krakensearch.service.search;

import com.dkswjdals89.krakensearch.service.openApi.impl.KakaoSearchOpenApiService;
import com.dkswjdals89.krakensearch.service.openApi.impl.NaverSearchOpenApiService;
import com.dkswjdals89.krakensearch.web.dto.SearchBookRequestDto;
import com.dkswjdals89.krakensearch.web.dto.SearchBookResponseDto;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {
    @Mock
    KakaoSearchOpenApiService kakaoSearchOpenApiService;

    @Autowired
    NaverSearchOpenApiService naverSearchOpenApiService;

    @Autowired
    SearchService searchService;

    @BeforeEach
    public void initData() {

    }

    @Test
    public void searchBookCallKakao() {
        // Given
        SearchBookRequestDto requestDto = SearchBookRequestDto.builder()
                .keyword("토비의 스프링")
                .page(1)
                .size(10)
                .build();

        // When
        when(kakaoSearchOpenApiService.searchBook(requestDto)).thenReturn(new SearchBookResponseDto());

        searchService.searchBook(requestDto);

        verify(kakaoSearchOpenApiService, times(1)).searchBook(requestDto);
    }
}

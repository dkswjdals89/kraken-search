package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.service.openApi.impl.KakaoSearchOpenApiService;
import com.dkswjdals89.krakensearch.web.dto.search.SearchBookRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KakaoSearchOpenApiServiceTest {
    @Autowired
    KakaoSearchOpenApiService kakaoOpenApiService;

    @Test
    public void SearchBookTest() {
        SearchBookRequestDto requestDto = SearchBookRequestDto.builder()
                .keyword("어린왕자")
                .page(1)
                .size(10)
                .build();
        kakaoOpenApiService.searchBook(requestDto);
    }
}

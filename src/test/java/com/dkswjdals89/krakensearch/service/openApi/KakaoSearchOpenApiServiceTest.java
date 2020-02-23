package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.openApi.impl.KakaoSearchOpenApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class KakaoSearchOpenApiServiceTest {
    @Autowired
    KakaoSearchOpenApiService kakaoOpenApiService;

    @Test
    public void SearchBookTest() {
        SearchRequestDto requestDto = SearchRequestDto.builder()
                .keyword("어린왕자")
                .page(1)
                .size(10)
                .build();
        kakaoOpenApiService.searchBook(requestDto);
    }
}

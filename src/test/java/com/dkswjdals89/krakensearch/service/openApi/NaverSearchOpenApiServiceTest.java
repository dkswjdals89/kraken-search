package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.service.openApi.impl.NaverSearchOpenApiService;
import com.dkswjdals89.krakensearch.web.dto.SearchBookRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NaverSearchOpenApiServiceTest {
    @Autowired
    NaverSearchOpenApiService naverOpenApiService;

    @Test
    public void SearchBookTest() {
        // Given
        SearchBookRequestDto requestDto = SearchBookRequestDto.builder()
                .keyword("어린왕자")
                .page(1)
                .size(10)
                .build();

        // Then
        naverOpenApiService.searchBook(requestDto);
    }
}

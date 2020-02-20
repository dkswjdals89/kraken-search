package com.dkswjdals89.booksearch.service.openApi;

import com.dkswjdals89.booksearch.service.openApi.impl.NaverSearchOpenApiService;
import com.dkswjdals89.booksearch.web.dto.SearchBookRequestDto;
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
        SearchBookRequestDto requestDto = SearchBookRequestDto.builder()
                .keyword("어린왕자")
                .page(1)
                .size(10)
                .build();
        naverOpenApiService.searchBook(requestDto);
    }
}

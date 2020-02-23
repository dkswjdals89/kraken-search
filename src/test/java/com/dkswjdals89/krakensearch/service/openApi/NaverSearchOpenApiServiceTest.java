package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.openApi.impl.NaverSearchOpenApiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class NaverSearchOpenApiServiceTest {
    @Autowired
    NaverSearchOpenApiService naverOpenApiService;

    @Test
    public void SearchBookTest() {
        // Given
        SearchRequestDto requestDto = SearchRequestDto.builder()
                .keyword("어린왕자")
                .page(1)
                .size(10)
                .build();

        // Then
        naverOpenApiService.searchBook(requestDto);
        System.out.println("TEST");
    }
}

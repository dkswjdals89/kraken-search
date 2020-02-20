package com.dkswjdals89.booksearch.service.openApi;

import com.dkswjdals89.booksearch.web.dto.SearchBookRequestDto;
import com.dkswjdals89.booksearch.web.dto.SearchBookResponseDto;

/**
 * Search Open API Interface
 */
public interface SearchOpenApiService {
    /**
     * 도서 데이터 검색
     * @param requestDto
     * @return
     */
    SearchBookResponseDto searchBook(SearchBookRequestDto requestDto);
}

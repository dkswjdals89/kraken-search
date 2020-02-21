package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.web.dto.SearchBookRequestDto;
import com.dkswjdals89.krakensearch.web.dto.SearchBookResponseDto;

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

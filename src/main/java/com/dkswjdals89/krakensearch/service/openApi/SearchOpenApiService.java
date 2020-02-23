package com.dkswjdals89.krakensearch.service.openApi;

import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.BookDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;

/**
 * Search Open API Interface
 */
public interface SearchOpenApiService {
    /**
     * 도서 데이터 검색
     * @param requestDto
     * @return
     */
    BasePagingListResponseDto<BookDto> searchBook(SearchRequestDto requestDto);
}

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
     *
     * @param requestDto 요청 Query Data
     * @return 기본 페이징 리스트 데이터 형식으로 반환
     */
    BasePagingListResponseDto<BookDto> searchBook(SearchRequestDto requestDto);
}

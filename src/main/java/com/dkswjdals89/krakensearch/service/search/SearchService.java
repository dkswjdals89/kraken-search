package com.dkswjdals89.krakensearch.service.search;

import com.dkswjdals89.krakensearch.annotation.SearchHistoryType;
import com.dkswjdals89.krakensearch.constant.OpenApiType;
import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.BookDto;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.service.openApi.SearchOpenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchService {
    private final Map<String, SearchOpenApiService> openApiServiceMap;

    /**
     * 책 검색
     * - Kakao 검색 API를 이용하여 도서 정보를 검색한다.
     * - Kakao 검색 API 오류 발생 시 Naver 검색 API를 사용한다.
     *
     * @param requestDto - 요청 데이터
     * @return SearchBookResponseDto
     */
    @SearchHistoryType(SearchType.BOOK)
    @Retryable(maxAttempts = 1)
    public BasePagingListResponseDto<BookDto> searchBook(SearchRequestDto requestDto) {
        return openApiServiceMap.get(OpenApiType.KAKAO)
                .searchBook(requestDto);
    }

    @Recover
    public BasePagingListResponseDto<BookDto> searchBookRecover(RuntimeException t, SearchRequestDto requestDto) {
        return openApiServiceMap.get(OpenApiType.NAVER)
                .searchBook(requestDto);
    }
}

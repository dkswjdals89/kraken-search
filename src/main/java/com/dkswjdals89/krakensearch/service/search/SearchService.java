package com.dkswjdals89.krakensearch.service.search;

import com.dkswjdals89.krakensearch.constant.OpenApiType;
import com.dkswjdals89.krakensearch.service.openApi.SearchOpenApiService;
import com.dkswjdals89.krakensearch.dto.search.SearchBookRequestDto;
import com.dkswjdals89.krakensearch.dto.search.SearchBookResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    public SearchBookResponseDto searchBook(SearchBookRequestDto requestDto) {
        try {
            return openApiServiceMap.get(OpenApiType.KAKAO)
                    .searchBook(requestDto);
        } catch (Exception error) {
            return openApiServiceMap.get(OpenApiType.NAVER)
                    .searchBook(requestDto);
        }
    }
}

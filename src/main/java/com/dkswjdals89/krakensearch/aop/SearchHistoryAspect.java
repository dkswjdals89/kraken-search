package com.dkswjdals89.krakensearch.aop;

import com.dkswjdals89.krakensearch.component.SearchHistoryType;
import com.dkswjdals89.krakensearch.dto.search.SearchRequestDto;
import com.dkswjdals89.krakensearch.dto.searchHistory.CreateSearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.service.history.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class SearchHistoryAspect {
    private final SearchHistoryService searchHistoryService;

    /**
     * 검색 서비스에 등록된 함수 호출 후, 검색 히스토리 기록
     *
     * - SearchHistoryType 어노테이션을 사용한 녀석들만 수행한다.
     * - 메소드 인자 값 중 SearchRequestDto 가 존재하지 않는다면, 히스토리를 기록하지 않는다.
     * - SearchRequestDto 가 존재하여도, 검색 키워드(keyword) 값이 빈값이라면, 히스토리를 기록하지 않는다.
     * @param joinPoint
     */
    @After("@annotation(com.dkswjdals89.krakensearch.component.SearchHistoryType)")
    public void saveSearchHistory(JoinPoint joinPoint) {
        SearchHistoryType historyType = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(SearchHistoryType.class);

         Arrays.stream(joinPoint.getArgs())
                .filter(SearchRequestDto.class::isInstance)
                .findFirst()
                .map(SearchRequestDto.class::cast)
                 .ifPresent((value) -> {
                    if (!StringUtils.isEmpty(value.getKeyword())) {
                        searchHistoryService.createSearchHistory(CreateSearchHistoryRequestDto.builder()
                                .keyword((value.getKeyword()))
                                .searchType(historyType.value())
                                .build());
                    }
                 });
    }
}

package com.dkswjdals89.krakensearch.service.statistics;

import com.dkswjdals89.krakensearch.domain.statistics.SearchRanking;
import com.dkswjdals89.krakensearch.domain.statistics.SearchRankingRepository;
import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.statistics.SearchRankingDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchRankingService {
    private final SearchRankingRepository searchRankingRepository;

    /**
     * 검색 키워드에 대한 조회 카운트 증가
     * - 오류 발생 시, 예외를 Throw 하지 않고, 로깅 처리
     * @param keyword
     */
    public void incrementSearchKeywordCount(String keyword) {
        try {
            searchRankingRepository.incrementSearchKeywordCount(keyword);
        } catch (Exception e) {
            log.error("Increment Search Keyword Count Fail - " + e.toString());
        }
    }

    /**
     * 검색 키워드 랭킹 조회
     * - 상위 10건에 대해서만 조회하여 반환한다.
     * @return 기본 페이징 리스트 데이터 형식으로 반환
     */
    public BasePagingListResponseDto<SearchRankingDto> searchKeywordRank() {
        List<SearchRanking> rankList = searchRankingRepository.getTopSearchKeywordWithScore(10);

        return BasePagingListResponseDto.<SearchRankingDto>builder()
                .items(rankList.stream()
                        .map(SearchRankingDto::new)
                        .collect(Collectors.toList()))
                .count(rankList.size())
                .build();
    }
}

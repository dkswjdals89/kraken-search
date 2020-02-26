package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.statistics.SearchRankingDto;
import com.dkswjdals89.krakensearch.service.statistics.SearchRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/statistics")
@RestController
public class StatisticsController {
    private final SearchRankingService searchRankingService;

    @GetMapping("/popularKeyword")
    public BasePagingListResponseDto<SearchRankingDto> searchKeywordRanking() {
        return searchRankingService.searchKeywordRank();
    }
}

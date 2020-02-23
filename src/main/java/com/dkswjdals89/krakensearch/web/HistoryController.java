package com.dkswjdals89.krakensearch.web;

import com.dkswjdals89.krakensearch.dto.BasePagingListResponseDto;
import com.dkswjdals89.krakensearch.dto.searchHistory.RecentlySearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.dto.searchHistory.SearchHistoryDetailDto;
import com.dkswjdals89.krakensearch.service.history.SearchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/v1/history")
@RestController
public class HistoryController {
    private final SearchHistoryService searchHistoryService;

    @GetMapping("/search")
    public BasePagingListResponseDto<SearchHistoryDetailDto> searchHistory(@Valid RecentlySearchHistoryRequestDto requestDto) {
        return searchHistoryService.findRecentlyByAccount(requestDto);
    }
}

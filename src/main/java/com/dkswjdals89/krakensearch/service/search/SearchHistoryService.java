package com.dkswjdals89.krakensearch.service.search;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistory;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistoryRepository;
import com.dkswjdals89.krakensearch.dto.searchHistory.CreateSearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.dto.searchHistory.SearchHistoryDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;

    @Transactional()
    public Long createSearchHistory(CreateSearchHistoryRequestDto requestDto) {
        SearchHistory createdHistory = searchHistoryRepository.save(SearchHistory.builder()
                .searchKeyword(requestDto.getKeyword())
                .searchType(requestDto.getSearchType())
                .account(requestDto.getAccount())
                .build());
        return createdHistory.getId();
    }

    @Transactional(readOnly = true)
    public List<SearchHistoryDetailDto> findRecentlyByAccount(Long accountId) {
        List<SearchHistory> searchHistories = searchHistoryRepository.findAllByAccount(Account.builder()
                .id(accountId).build(), Sort.by(Sort.Direction.DESC, "id"));
        return searchHistories.stream().map((SearchHistoryDetailDto::new))
                .collect(Collectors.toList());
    }
}

package com.dkswjdals89.krakensearch.service.history;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistory;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistoryRepository;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.dto.searchHistory.CreateSearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.dto.searchHistory.SearchHistoryDetailDto;
import com.dkswjdals89.krakensearch.utils.ContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
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
        AccountDetailDto account = ContextUtils.getCurrentAccountDetail();
        SearchHistory createdHistory = searchHistoryRepository.save(SearchHistory.builder()
                .searchKeyword(requestDto.getKeyword())
                .searchType(requestDto.getSearchType())
                .account(Account.builder().id(account.getId()).build())
                .build());
        return createdHistory.getId();
    }

    @Transactional(readOnly = true)
    public List<SearchHistoryDetailDto> findRecentlyByAccount() {
        AccountDetailDto account = ContextUtils.getCurrentAccountDetail();
        List<SearchHistory> searchHistories = searchHistoryRepository.findAllByAccount(Account.builder()
                .id(account.getId()).build(), Sort.by(Sort.Direction.DESC, "id"));
        return searchHistories.stream().map((SearchHistoryDetailDto::new))
                .collect(Collectors.toList());
    }
}

package com.dkswjdals89.krakensearch.dto.history;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.history.SearchHistory;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchHistoryDetailDto {
    private Long id;

    private AccountDetailDto account;

    private SearchType searchType;

    private String searchKeyword;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public SearchHistoryDetailDto(SearchHistory searchHistory) {
        this.id = searchHistory.getId();
        this.account = new AccountDetailDto(searchHistory.getAccount());
        this.searchType = searchHistory.getSearchType();
        this.searchKeyword = searchHistory.getSearchKeyword();
        this.createdDate = searchHistory.getCreatedDate();
        this.modifiedDate = searchHistory.getModifiedDate();
    }
}

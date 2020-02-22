package com.dkswjdals89.krakensearch.dto.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistory;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchHistoryDetailDto {
    private Long id;

    private Account account;

    private SearchType searchType;

    private String searchKeyword;

    public SearchHistoryDetailDto(SearchHistory searchHistory) {
        this.id = searchHistory.getId();
        this.account = searchHistory.getAccount();
        this.searchType = searchHistory.getSearchType();
        this.searchKeyword = searchHistory.getSearchKeyword();
    }
}

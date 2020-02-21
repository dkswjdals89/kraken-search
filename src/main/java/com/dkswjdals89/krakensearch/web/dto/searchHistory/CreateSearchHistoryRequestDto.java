package com.dkswjdals89.krakensearch.web.dto.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateSearchHistoryRequestDto {
    private Account account;
    private String keyword;
    private SearchType searchType;

    @Builder
    public CreateSearchHistoryRequestDto(Account account, String keyword, SearchType searchType) {
        this.account = account;
        this.keyword = keyword;
        this.searchType = searchType;
    }
}

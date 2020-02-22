package com.dkswjdals89.krakensearch.dto.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateSearchHistoryRequestDto {
    private Account account;
    private String keyword;
    private SearchType searchType;
}

package com.dkswjdals89.krakensearch.dto.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateSearchHistoryRequestDto {
    private String keyword;
    private SearchType searchType;
}

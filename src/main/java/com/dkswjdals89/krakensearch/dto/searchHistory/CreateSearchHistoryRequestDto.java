package com.dkswjdals89.krakensearch.dto.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSearchHistoryRequestDto {
    private String keyword;
    private SearchType searchType;
}

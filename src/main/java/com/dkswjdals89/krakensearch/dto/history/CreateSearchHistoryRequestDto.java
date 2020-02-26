package com.dkswjdals89.krakensearch.dto.history;

import com.dkswjdals89.krakensearch.constant.SearchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateSearchHistoryRequestDto {
    private String keyword;
    private SearchType searchType;
}

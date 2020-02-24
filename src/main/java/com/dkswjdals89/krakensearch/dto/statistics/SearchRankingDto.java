package com.dkswjdals89.krakensearch.dto.statistics;

import com.dkswjdals89.krakensearch.domain.statistics.SearchRanking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchRankingDto {
    private String keyword;
    private int count;

    public SearchRankingDto(SearchRanking searchRanking) {
        this.keyword = searchRanking.getKeyword();
        this.count = searchRanking.getCount().intValue();
    }
}

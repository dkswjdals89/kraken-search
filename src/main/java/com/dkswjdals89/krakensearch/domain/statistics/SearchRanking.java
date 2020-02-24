package com.dkswjdals89.krakensearch.domain.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.ZSetOperations;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SearchRanking {
    private String keyword;
    private Double count;

    public SearchRanking(ZSetOperations.TypedTuple<String> tuple) {
        this.keyword = tuple.getValue();
        this.count = tuple.getScore();
    }
}

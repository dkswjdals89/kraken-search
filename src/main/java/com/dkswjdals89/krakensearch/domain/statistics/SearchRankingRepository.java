package com.dkswjdals89.krakensearch.domain.statistics;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

import static com.dkswjdals89.krakensearch.constant.RedisKeyConstant.SEARCH_RANK_REDIS_KEY;

@Slf4j
@RequiredArgsConstructor
@Repository
public class SearchRankingRepository {
    private final RedisTemplate<String, String> redisTemplate;
    private ZSetOperations<String, String> zSetOps;

    @PostConstruct
    public void init() {
        zSetOps = redisTemplate.opsForZSet();
    }

    public List<SearchRanking> getTopSearchKeywordWithScore(int maxRank) {
        return zSetOps.reverseRangeWithScores(SEARCH_RANK_REDIS_KEY, 0, maxRank - 1)
                .stream().map(SearchRanking::new).collect(Collectors.toList());
    }

    public void incrementSearchKeywordCount(String searchKeyword) {
        zSetOps.incrementScore(SEARCH_RANK_REDIS_KEY, searchKeyword, 1);
    }
}

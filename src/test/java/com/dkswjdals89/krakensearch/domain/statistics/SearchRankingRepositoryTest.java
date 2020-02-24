package com.dkswjdals89.krakensearch.domain.statistics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.dkswjdals89.krakensearch.constant.RedisKeyConstant.SEARCH_RANK_REDIS_KEY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("검색 랭킹 Repository 테스트")
public class SearchRankingRepositoryTest {
    @Autowired
    private SearchRankingRepository searchRankingRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    public void cleanup() {
        redisTemplate.delete(SEARCH_RANK_REDIS_KEY);
    }

    @Nested
    @DisplayName("검색 키워드 카운트 증가 테스트")
    class InsertSearchCountTest {
        @Test
        @DisplayName("Repository를 이용하여 1씩 증가해야 한다.")
        public void thenIncrementSearchKeywordCountUseRepository() {
            String keyword = "Effective Java";

            searchRankingRepository.incrementSearchKeywordCount(keyword);

            Double savedScore = redisTemplate.opsForZSet()
                    .score(SEARCH_RANK_REDIS_KEY, keyword);
            assertThat(savedScore, equalTo(1D));
        }
    }

    @Nested
    @DisplayName("인기 검색 키워드 리스트 조회 테스트")
    class TopSearchKeywordListTest {
        @Test
        @DisplayName("조회시 키워드 검색 횟수 만큼의 값을 반환 해야 한다.")
        public void getTopSearchKeywordWithScoreTest() {
            List<String> keywordLists = Arrays.asList(
                    "토비의 스프링", "토비의 스프링", "토비의 스프링", "토비의 스프링",
                    "자바8 인 액션", "자바8 인 액션", "자바8 인 액션",
                    "Effective Java", "Effective Java",
                    "스프링 인 액션");
            Map<String, Long> keywordCount = keywordLists.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            keywordLists.forEach(searchRankingRepository::incrementSearchKeywordCount);

            List<SearchRanking> result = searchRankingRepository.getTopSearchKeywordWithScore(10);

            result.forEach((a) -> {
                Long expectedCount = keywordCount.get(a.getKeyword());
                assertThat(a.getCount().intValue(), equalTo(expectedCount.intValue()));
            });
        }
    }
}

package com.dkswjdals89.krakensearch.service.statistics;

import com.dkswjdals89.krakensearch.domain.statistics.SearchRanking;
import com.dkswjdals89.krakensearch.domain.statistics.SearchRankingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SearchRankingServiceTest {
    @Autowired
    private SearchRankingService searchRankingService;

    @MockBean
    private SearchRankingRepository searchRankingRepository;

    @Test
    @DisplayName("검색 키워드에 대해 카운트 증가 처리가 수행되어야 한다.")
    public void incrementSearchKeywordUseRepository() {
        String keyword = "디자인 패턴";

        searchRankingService.incrementSearchKeywordCount(keyword);

        verify(searchRankingRepository).incrementSearchKeywordCount(keyword);
    }

    @Test
    @DisplayName("인기 키워드에 대해 상위 10개 까지만 조회해야 한다.")
    public void getSearchKeywordRankMustOnlyTop10() {
        List<SearchRanking> rankingList = Arrays.asList(
                new SearchRanking("스프링", 3D),
                new SearchRanking("토비", 10D),
                new SearchRanking("백종원", 50D)
        );

        when(searchRankingRepository.getTopSearchKeywordWithScore(anyInt()))
                .thenReturn(rankingList);

        searchRankingService.searchKeywordRank();

        ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
        verify(searchRankingRepository)
                .getTopSearchKeywordWithScore(argument.capture());
        assertThat(argument.getValue(), equalTo(10));
    }
}

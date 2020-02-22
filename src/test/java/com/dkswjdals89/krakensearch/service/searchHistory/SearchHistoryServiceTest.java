package com.dkswjdals89.krakensearch.service.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistory;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistoryRepository;
import com.dkswjdals89.krakensearch.dto.searchHistory.CreateSearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.service.history.SearchHistoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchHistoryServiceTest {
    @Autowired
    SearchHistoryService searchHistoryService;

    @MockBean
    SearchHistoryRepository searchHistoryRepository;

    @Test
    public void createSearchHistoryUseRepository() {
        Account requestAccount = Account.builder().id((long) 1).build();
        String searchKeyword = "어린왕자";
        CreateSearchHistoryRequestDto requestDto = CreateSearchHistoryRequestDto.builder()
                .account(requestAccount)
                .keyword(searchKeyword)
                .searchType(SearchType.BOOK)
                .build();

        when(searchHistoryRepository.save(any()))
                .thenReturn(SearchHistory.builder()
                .id(Long.valueOf(1)).build());

        searchHistoryService.createSearchHistory(requestDto);

        verify(searchHistoryRepository).save(ArgumentMatchers.refEq(SearchHistory.builder()
                .account(requestAccount)
                .searchKeyword(searchKeyword)
                .searchType(SearchType.BOOK)
                .build()));
    }
}

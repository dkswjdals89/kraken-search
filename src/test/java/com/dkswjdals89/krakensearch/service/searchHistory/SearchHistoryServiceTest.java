package com.dkswjdals89.krakensearch.service.searchHistory;

import com.dkswjdals89.krakensearch.component.ContextHolderComponent;
import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistory;
import com.dkswjdals89.krakensearch.domain.searchHistory.SearchHistoryRepository;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.dto.searchHistory.CreateSearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.service.history.SearchHistoryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SearchHistoryServiceTest {
    @Autowired
    SearchHistoryService searchHistoryService;

    @MockBean
    SearchHistoryRepository searchHistoryRepository;

    @MockBean
    ContextHolderComponent contextHolderComponent;

    @Test
    @DisplayName("검색 요청 히스토리를 데이터 베이스에 저장해야 한다.")
    public void createSearchHistoryUseRepository() {
        Account requestAccount = Account.builder().id((long) 1).build();
        String searchKeyword = "어린왕자";
        SearchType searchType = SearchType.BOOK;

        CreateSearchHistoryRequestDto requestDto = CreateSearchHistoryRequestDto.builder()
                .keyword(searchKeyword)
                .searchType(searchType)
                .build();

        when(contextHolderComponent.getCurrentAccountDetail())
                .thenReturn(new AccountDetailDto(requestAccount));
        when(searchHistoryRepository.save(any()))
                .thenReturn(SearchHistory.builder()
                .id(1L).build());

        ArgumentCaptor<SearchHistory> argument = ArgumentCaptor.forClass(SearchHistory.class);
        searchHistoryService.createSearchHistory(requestDto);

        verify(searchHistoryRepository).save(argument.capture());
        SearchHistory callArgument = argument.getValue();
        assertThat(callArgument.getSearchKeyword(), equalTo(searchKeyword));
        assertThat(callArgument.getSearchType(), equalTo(searchType));
        assertThat(callArgument.getAccount().getId(), equalTo(requestAccount.getId()));
    }
}

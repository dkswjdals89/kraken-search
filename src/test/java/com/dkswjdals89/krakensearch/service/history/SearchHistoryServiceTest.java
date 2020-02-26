package com.dkswjdals89.krakensearch.service.history;

import com.dkswjdals89.krakensearch.component.ContextHolderComponent;
import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.history.SearchHistory;
import com.dkswjdals89.krakensearch.domain.history.SearchHistoryRepository;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.dto.history.CreateSearchHistoryRequestDto;
import com.dkswjdals89.krakensearch.dto.history.RecentlySearchHistoryRequestDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("검색 히스토리 서비스 테스트")
public class SearchHistoryServiceTest {
    @Autowired
    SearchHistoryService searchHistoryService;

    @MockBean
    SearchHistoryRepository searchHistoryRepository;

    @MockBean
    ContextHolderComponent contextHolderComponent;

    @AfterEach
    public void afterMockClean() {
        reset(searchHistoryRepository, contextHolderComponent);
    }

    @Nested
    @DisplayName("검색 히스토리 생성")
    class CrateSearchHistoryTest {
        @Test
        @DisplayName("검색 요청 히스토리를 데이터 베이스에 저장해야 한다.")
        public void createSearchHistoryUseRepository() {
            Account requestAccount = Account.builder()
                    .id(1L)
                    .build();
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
                            .id(1L)
                            .account(requestAccount)
                            .build());

            ArgumentCaptor<SearchHistory> argument = ArgumentCaptor.forClass(SearchHistory.class);
            searchHistoryService.createSearchHistory(requestDto);

            verify(searchHistoryRepository).save(argument.capture());
            SearchHistory callArgument = argument.getValue();
            assertThat(callArgument.getSearchKeyword(), equalTo(searchKeyword));
            assertThat(callArgument.getSearchType(), equalTo(searchType));
            assertThat(callArgument.getAccount().getId(), equalTo(requestAccount.getId()));
        }
    }

    @Nested
    @DisplayName("사용자 검색 히스토리 조회")
    class FindSearchHistoryByAccountTest {
        RecentlySearchHistoryRequestDto requestDto;
        AccountDetailDto accountDetail;

        @BeforeEach
        public void initCommonData() {
            requestDto = RecentlySearchHistoryRequestDto
                    .builder()
                    .page(1).size(1).build();
            accountDetail = AccountDetailDto.builder()
                    .id(1L)
                    .userId("dkswjdals89")
                    .password("dkswjdals8(!@")
                    .activated(true)
                    .build();
        }

        @Test
        @DisplayName("현재 유저 계정 정보를 조회해야 한다.")
        public void shouldFindCurrentAccountDetail() {
            when(contextHolderComponent.getCurrentAccountDetail())
                    .thenReturn(accountDetail);
            when(searchHistoryRepository.findAllByAccount(any(), any()))
                    .thenReturn(new PageImpl<>(new ArrayList<>()));

            searchHistoryService.findRecentlyByAccount(requestDto);

            verify(contextHolderComponent, times(1))
                    .getCurrentAccountDetail();
        }

        @Test
        @DisplayName("id 기준 내림차순 정렬이 되어야 한다.")
        public void shouldSortByIdDesc() {
            when(contextHolderComponent.getCurrentAccountDetail())
                    .thenReturn(accountDetail);
            when(searchHistoryRepository.findAllByAccount(any(), any()))
                    .thenReturn(new PageImpl<>(new ArrayList<>()));

            searchHistoryService.findRecentlyByAccount(requestDto);

            ArgumentCaptor<PageRequest> pageRequestArgumentCaptor = ArgumentCaptor.forClass(PageRequest.class);
            verify(searchHistoryRepository).findAllByAccount(any(), pageRequestArgumentCaptor.capture());
            Sort requestSort = pageRequestArgumentCaptor.getValue().getSort();

            assertThat(requestSort.toList(), is(not(empty())));
            assertThat(requestSort.getOrderFor("id"), is(not(nullValue())));
            assertThat(requestSort.getOrderFor("id").getDirection(), is(equalTo(Sort.Direction.DESC)));
        }

        @Test
        @DisplayName("Repository를 이용하여 조회해야 한다.")
        public void shouldFindUseRepository() {
            when(contextHolderComponent.getCurrentAccountDetail())
                    .thenReturn(accountDetail);
            when(searchHistoryRepository.findAllByAccount(any(), any()))
                    .thenReturn(new PageImpl<>(new ArrayList<>()));

            searchHistoryService.findRecentlyByAccount(requestDto);

            ArgumentCaptor<Account> accountArgumentCaptor = ArgumentCaptor.forClass(Account.class);
            ArgumentCaptor<PageRequest> pageRequestArgumentCaptor = ArgumentCaptor.forClass(PageRequest.class);

            verify(searchHistoryRepository).findAllByAccount(accountArgumentCaptor.capture(), pageRequestArgumentCaptor.capture());
            Account accountArgumentCaptorValue = accountArgumentCaptor.getValue();
            PageRequest pageRequestArgumentCaptorValue = pageRequestArgumentCaptor.getValue();

            assertThat(accountArgumentCaptorValue.getId(), is(equalTo(accountDetail.getId())));
            assertThat(pageRequestArgumentCaptorValue.getPageNumber(), is(equalTo(requestDto.getPage() - 1)));
            assertThat(pageRequestArgumentCaptorValue.getPageSize(), is(equalTo(requestDto.getSize())));
        }
    }
}

package com.dkswjdals89.krakensearch.domain.history;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.domain.account.AccountRole;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("검색 히스토리 Repository 테스트")
public class SearchHistoryRepositoryTest {
    @Autowired
    SearchHistoryRepository searchHistoryRepository;

    @Autowired
    AccountRepository accountRepository;

    Account requestAccount;

    @AfterEach
    public void afterCleanup() {
        searchHistoryRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @BeforeEach
    public void initAccount() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        requestAccount = accountRepository.save(Account.builder()
                .userId(userId)
                .password(password)
                .role(AccountRole.USER)
                .build());
    }

    @Nested
    @DisplayName("검색 히스토리 등록 테스트")
    class InsertAccountTest {
        @Test
        @DisplayName("요청 검색 키워드와 검색 타입이 DB에 저장되어야 한다.")
        public void insertTest() {
            String searchKeyword = "봄";
            SearchType searchType = SearchType.BOOK;

            SearchHistory createdEntity = searchHistoryRepository.save(SearchHistory.builder()
                    .account(requestAccount)
                    .searchKeyword(searchKeyword)
                    .searchType(searchType)
                    .build());

            assertThat(createdEntity, notNullValue());
            assertThat(createdEntity.getAccount(), equalTo(requestAccount));
            assertThat(createdEntity.getSearchKeyword(), equalTo(searchKeyword));
            assertThat(createdEntity.getSearchType(), equalTo(searchType));
        }
    }

    @Nested
    @DisplayName("최근 검색 히스토리 테스트")
    class RecentlyHistoryTest {
        @Test
        @DisplayName("등록된 검색 히스토리 데이터를 반환해야 한다.")
        public void findRecentlyHistory() {
            List<String> searchKeywords = new ArrayList<>(Arrays.asList("봄", "봄비", "토비", "어린왕자"));
            SearchType searchType = SearchType.BOOK;
            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));

            Stack<SearchHistory> createdStack = new Stack<>();
            searchKeywords.stream().map(keyword -> searchHistoryRepository.save(SearchHistory.builder()
                    .account(requestAccount)
                    .searchKeyword(keyword)
                    .searchType(searchType)
                    .build())).forEach(createdStack::push);

            Page<SearchHistory> result = searchHistoryRepository.findAllByAccount(requestAccount, pageRequest);

            assertThat(result.getTotalElements(), equalTo((long) searchKeywords.size()));

            result.stream().forEach((history) -> {
                SearchHistory beforeCreated = createdStack.pop();
                assertThat(history.getId(), equalTo(beforeCreated.getId()));
            });
        }
    }
}

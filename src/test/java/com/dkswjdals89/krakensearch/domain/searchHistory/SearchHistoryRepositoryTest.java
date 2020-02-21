package com.dkswjdals89.krakensearch.domain.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchHistoryRepositoryTest {
    @Autowired
    SearchHistoryRepository searchHistoryRepository;

    @Autowired
    AccountRepository accountRepository;

    Account requestAccount;

    @AfterEach
    public void eachHistoryCleanup() {
        searchHistoryRepository.deleteAll();
    }

    @After
    public void accountCleanup() {
        searchHistoryRepository.deleteAll();
        accountRepository.deleteAll();
    }

    @Before
    public void initAccount() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        requestAccount = accountRepository.save(Account.builder()
                .userId(userId)
                .password(password)
                .build());
    }

    @Test
    public void insertTest() {
        String searchKeyword = "봄";
        SearchType searchType = SearchType.BOOK;

        SearchHistory createdEntity = searchHistoryRepository.save(SearchHistory.builder()
                .account(requestAccount)
                .searchKeyword(searchKeyword)
                .searchType(searchType)
                .build());

        assertThat(createdEntity).isNotNull();
        assertThat(createdEntity.getAccount()).isEqualTo(requestAccount);
        assertThat(createdEntity.getSearchKeyword()).isEqualTo(searchKeyword);
        assertThat(createdEntity.getSearchType()).isEqualTo(searchType);
    }

    @Test
    public void findRecentlyHistory() {
        List<String> searchKeywords = new ArrayList<>(Arrays.asList("봄", "봄비", "토비", "어린왕자"));
        SearchType searchType = SearchType.BOOK;

        Stack<SearchHistory> createdStack = new Stack<>();
        searchKeywords.stream().map(keyword -> searchHistoryRepository.save(SearchHistory.builder()
                .account(requestAccount)
                .searchKeyword(keyword)
                .searchType(searchType)
                .build())).forEach(createdStack::push);

        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        List<SearchHistory> result = searchHistoryRepository.findAllByAccount(requestAccount, sort);

        assertThat(result.size()).isEqualTo(searchKeywords.size());

        result.stream().forEach((history) -> {
            SearchHistory beforeCreated = createdStack.pop();
            assertThat(history.getId()).isEqualTo(beforeCreated.getId());
        });
    }
}

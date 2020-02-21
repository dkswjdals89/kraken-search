package com.dkswjdals89.krakensearch.domain.searchHistory;

import com.dkswjdals89.krakensearch.domain.account.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findAllByAccount(Account account, Sort sort);
}

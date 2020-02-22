package com.dkswjdals89.krakensearch.domain.searchHistory;

import com.dkswjdals89.krakensearch.domain.account.Account;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findAllByAccount(Account account, Sort sort);

    @Query(value = "SELECT h.keyword as keyword, count(h.keyword) as count " +
            "FROM SearchHistory h " +
            "GROUP BY h.keyword " +
            "ORDER BY count(h.keyword) DESC", nativeQuery = true)
    List<SearchHistory> getSearchKeywordTop10();
}

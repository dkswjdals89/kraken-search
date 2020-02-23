package com.dkswjdals89.krakensearch.domain.searchHistory;

import com.dkswjdals89.krakensearch.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    Page<SearchHistory> findAllByAccount(Account account, Pageable pageable);

    @Query(value = "SELECT h.keyword as keyword, count(h.keyword) as count " +
            "FROM SearchHistory h " +
            "GROUP BY h.keyword " +
            "ORDER BY count(h.keyword) DESC", nativeQuery = true)
    List<SearchHistory> getSearchKeywordTop10();
}

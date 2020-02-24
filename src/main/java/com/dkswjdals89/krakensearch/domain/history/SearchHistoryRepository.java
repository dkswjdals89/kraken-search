package com.dkswjdals89.krakensearch.domain.history;

import com.dkswjdals89.krakensearch.domain.account.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    Page<SearchHistory> findAllByAccount(Account account, Pageable pageable);
}

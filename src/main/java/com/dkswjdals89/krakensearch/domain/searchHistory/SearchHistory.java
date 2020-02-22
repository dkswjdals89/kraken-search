package com.dkswjdals89.krakensearch.domain.searchHistory;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.BaseTimeEntity;
import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "tlb_search_history")
public class SearchHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    private SearchType searchType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String searchKeyword;
}

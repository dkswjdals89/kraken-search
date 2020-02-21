package com.dkswjdals89.krakensearch.domain.searchHistory;

import com.dkswjdals89.krakensearch.domain.BaseTimeEntity;
import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity(name = "tlb_search_history")
public class SearchHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Account.class, fetch = FetchType.LAZY)
    @JoinColumn()
    private Account account;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String SearchType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String searchKeyword;

    @Column(columnDefinition = "TEXT", nullable = false)
    private LocalDateTime time;
}

package com.dkswjdals89.krakensearch.domain.searchHistory;

import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class SearchHistory {
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

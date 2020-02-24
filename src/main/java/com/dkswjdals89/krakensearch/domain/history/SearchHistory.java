package com.dkswjdals89.krakensearch.domain.history;

import com.dkswjdals89.krakensearch.constant.SearchType;
import com.dkswjdals89.krakensearch.domain.BaseTimeEntity;
import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity(name = "tlb_search_history")
public class SearchHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TEXT", nullable = false)
    private SearchType searchType;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String searchKeyword;
}

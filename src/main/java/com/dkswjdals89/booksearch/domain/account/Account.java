package com.dkswjdals89.booksearch.domain.account;

import com.dkswjdals89.booksearch.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    @Builder
    public Account(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}

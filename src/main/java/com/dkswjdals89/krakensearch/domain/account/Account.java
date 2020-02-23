package com.dkswjdals89.krakensearch.domain.account;

import com.dkswjdals89.krakensearch.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity(name = "tlb_account")
public class Account extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", unique = true)
    private String userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final AccountRole role = AccountRole.USER;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean activated;
}

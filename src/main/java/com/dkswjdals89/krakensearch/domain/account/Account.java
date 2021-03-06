package com.dkswjdals89.krakensearch.domain.account;

import com.dkswjdals89.krakensearch.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity(name = "tlb_account")
public class Account extends BaseTimeEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 24)
    private String userId;

    @Column(nullable = false, length = 150)
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

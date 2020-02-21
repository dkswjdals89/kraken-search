package com.dkswjdals89.krakensearch.domain.account;

import com.dkswjdals89.krakensearch.domain.BaseTimeEntity;
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

    @Column(columnDefinition = "TEXT", unique = true)
    private String userId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String password;

    private String firstName;

    private String lastName;

    private String email;

    private Boolean activated;

    @Builder
    public Account(Long id, String userId, String password, String firstName, String lastName, String email, Boolean activated) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
    }
}

package com.dkswjdals89.krakensearch.dto.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountDetailDto {
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean activated;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public AccountDetailDto(Account account) {
        this.id = account.getId();
        this.userId = account.getUserId();
        this.email = account.getEmail();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.createdDate = account.getCreatedDate();
        this.modifiedDate = account.getModifiedDate();
        this.activated = account.getActivated();
    }
}

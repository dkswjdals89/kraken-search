package com.dkswjdals89.krakensearch.web.dto.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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

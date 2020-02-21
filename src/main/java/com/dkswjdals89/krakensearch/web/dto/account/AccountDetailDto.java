package com.dkswjdals89.krakensearch.web.dto.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import lombok.Getter;

@Getter
public class AccountDetailDto {
    private long id;
    private String userId;

    public AccountDetailDto(Account account) {
        this.id = account.getId();
        this.userId = account.getUserId();
    }
}

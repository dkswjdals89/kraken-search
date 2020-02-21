package com.dkswjdals89.krakensearch.web.dto.account;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class AccountCreateRequestDto {
    @NotEmpty(message = "Required data")
    @Size(min = 3, max = 20, message = "UserId Length must be in range({min} ~ {max})")
    private String userId;

    @NotEmpty(message = "Required data")
    @Size(min = 6, max = 20, message = "Password Length must be in range({min} ~ {max})")
    private String password;

    @Builder
    public AccountCreateRequestDto(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}

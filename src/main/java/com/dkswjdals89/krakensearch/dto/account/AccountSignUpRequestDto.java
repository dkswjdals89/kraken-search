package com.dkswjdals89.krakensearch.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountSignUpRequestDto {
    @NotEmpty(message = "Required data")
    @Size(min = 3, max = 20, message = "UserId Length must be in range({min} ~ {max})")
    private String userId;

    @NotEmpty(message = "Required data")
    @Size(min = 6, max = 20, message = "Password Length must be in range({min} ~ {max})")
    private String password;
}

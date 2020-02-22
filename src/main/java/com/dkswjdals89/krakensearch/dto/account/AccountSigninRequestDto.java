package com.dkswjdals89.krakensearch.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountSigninRequestDto {
    private String userId;
    private String password;
}

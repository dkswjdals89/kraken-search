package com.dkswjdals89.krakensearch.component;

import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.dto.account.AccountPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ContextHolderComponent {
    public AccountDetailDto getCurrentAccountDetail() {
        AccountPrincipal principal = (AccountPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new AccountDetailDto(principal.getAccount());
    }
}

package com.dkswjdals89.krakensearch.component;

import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class ContextHolderComponent {
    public AccountDetailDto getCurrentAccountDetail() {
        return (AccountDetailDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

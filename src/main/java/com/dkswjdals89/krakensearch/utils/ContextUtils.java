package com.dkswjdals89.krakensearch.utils;

import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import org.springframework.security.core.context.SecurityContextHolder;

public class ContextUtils {
    public static AccountDetailDto getCurrentAccountDetail() {
        return (AccountDetailDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

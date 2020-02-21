package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public String joinAccount() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return "test";
    }
}

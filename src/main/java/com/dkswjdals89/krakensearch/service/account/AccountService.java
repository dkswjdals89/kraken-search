package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.web.dto.account.AccountCreateRequestDto;
import com.dkswjdals89.krakensearch.web.dto.account.AccountDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountDetailDto joinAccount(AccountCreateRequestDto requestDto) throws Exception {
        Account beforeAccount = accountRepository.findOneByUserId(requestDto.getUserId());
        if (beforeAccount != null) {
            throw new Exception("이미 존재하는 ID 입니다.");
        }

        Account createdAccount = accountRepository.save(Account.builder()
                .userId(requestDto.getUserId())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build());
        return new AccountDetailDto(createdAccount);
    }
}

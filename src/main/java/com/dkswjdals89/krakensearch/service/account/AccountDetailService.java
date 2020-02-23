package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.dkswjdals89.krakensearch.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * `Spring Security`에서 유저 정보를 조회하기 위한 Service
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AccountDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
        log.debug("Request Load User - " + accountId);
         Optional<Account> account = accountRepository.findById(Long.valueOf(accountId));
         account.orElseThrow(() -> new ServiceException(ServiceError.NOT_FOUND_ACCOUNT, "사용자 계정을 찾을수 없습니다."));
         return new AccountDetailDto(account.get());
    }
}

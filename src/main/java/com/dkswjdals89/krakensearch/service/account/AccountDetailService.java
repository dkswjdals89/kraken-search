package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.domain.account.AccountRole;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.dkswjdals89.krakensearch.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountDetailService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException {
         Optional<Account> account = accountRepository.findById(Long.valueOf(accountId));
         account.orElseThrow(() -> new ServiceException(ServiceError.NOT_FOUND_ACCOUNT, "사용자 계정을 찾을수 없습니다."));
         return new AccountDetailDto(account.get());
//         return convertAccountToUser(account.get());
    }

    private User convertAccountToUser(Account account) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        if (account.getRole() != null) {
            grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole().getKey()));
        }

        return new User(account.getUserId(),
                account.getPassword(),
                grantedAuthorities);
    }
}

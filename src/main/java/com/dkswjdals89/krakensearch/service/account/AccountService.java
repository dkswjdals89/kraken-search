package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.config.security.jwt.JwtTokenProvider;
import com.dkswjdals89.krakensearch.domain.account.AccountRole;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninResponseDto;
import com.dkswjdals89.krakensearch.exception.ServiceException;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.dto.account.AccountSignUpRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final Logger logger = LoggerFactory.getLogger(Account.class);
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public AccountDetailDto signup(AccountSignUpRequestDto requestDto) {
        logger.debug("Request sing up - " + requestDto.toString());
        Optional<Account> beforeAccount = accountRepository.findOneByUserId(requestDto.getUserId());

        beforeAccount.ifPresent((value) -> {
            throw new ServiceException(ServiceError.DUPLICATED_ERROR, "이미 존재하는 ID 입니다.");
        });

        Account createdAccount = accountRepository.save(Account.builder()
                .userId(requestDto.getUserId())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(AccountRole.USER) //  회원 가입시 기본으로 유저 권한을 부여한다.
                .build());
        return new AccountDetailDto(createdAccount);
    }

    public AccountSigninResponseDto signin(AccountSigninRequestDto requestDto) {
        Optional<Account> account = accountRepository.findOneByUserId(requestDto.getUserId());
        account.orElseThrow(() -> new ServiceException(ServiceError.NOT_FOUND_ACCOUNT, "해당 계정이 존재하지 않습니다."));

        Account accountVal = account.get();
        if (!passwordEncoder.matches(requestDto.getPassword(), accountVal.getPassword())) {
            throw new ServiceException(ServiceError.PASSWORD_ERROR, "비밀번호가 올바르지 않습니다.");
        }
        String jwtToken = jwtTokenProvider.createToken(new AccountDetailDto(accountVal));
        return AccountSigninResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}

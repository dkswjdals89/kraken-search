package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.config.security.jwt.JwtTokenProvider;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.domain.account.AccountRole;
import com.dkswjdals89.krakensearch.dto.account.AccountDetailDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSignUpRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninResponseDto;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.dkswjdals89.krakensearch.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 회원 가입
     * - 회원 가입시 권한은 `USER`로 세팅되며, 추후 권한 정책에 따라 변경될수 있음.
     *
     * @param requestDto    계정 생성에 필요한 요청 데이터
     * @return  생성된 계정 상세 정보 반환
     */
    @Transactional
    public AccountDetailDto signup(AccountSignUpRequestDto requestDto) {
        log.debug("Request sing up - " + requestDto.toString());

        Optional<Account> beforeAccount = accountRepository.findOneByUserId(requestDto.getUserId());

        beforeAccount.ifPresent((value) -> {
            throw new ServiceException(ServiceError.DUPLICATED_ERROR, "이미 존재하는 ID 입니다.");
        });

        Account createdAccount = accountRepository.save(Account.builder()
                .userId(requestDto.getUserId())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(AccountRole.USER) //  회원 가입시 기본으로 유저 권한을 부여한다.
                .build());

        log.debug("Create Account Result - " + createdAccount);
        return new AccountDetailDto(createdAccount);
    }

    /**
     * 로그인
     * @param requestDto    로그인에 필요한 userId, Password 데이터
     * @return JWT 토큰 정보가 담긴 데이터를 반환
     */
    @Transactional
    public AccountSigninResponseDto signin(AccountSigninRequestDto requestDto) {
        log.debug("Request sign in" + requestDto.getUserId());

        Optional<Account> account = accountRepository.findOneByUserId(requestDto.getUserId());
        account.orElseThrow(() -> new ServiceException(ServiceError.NOT_FOUND_ACCOUNT, "해당 계정이 존재하지 않습니다."));

        Account accountVal = account.get();
        if (!passwordEncoder.matches(requestDto.getPassword(), accountVal.getPassword())) {
            log.warn("Password Not Match! - userId:" + requestDto.getUserId());
            throw new ServiceException(ServiceError.PASSWORD_ERROR, "비밀번호가 올바르지 않습니다.");
        }
        String jwtToken = jwtTokenProvider.createToken(accountVal);

        log.debug("Created JWT Token" + jwtToken);
        return AccountSigninResponseDto.builder()
                .token(jwtToken)
                .build();
    }
}

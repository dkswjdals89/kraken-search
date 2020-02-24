package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.config.security.jwt.JwtTokenProvider;
import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.dto.account.AccountSignUpRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninRequestDto;
import com.dkswjdals89.krakensearch.dto.account.AccountSigninResponseDto;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.dkswjdals89.krakensearch.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @Test
    @DisplayName("가입시 요청 데이터가 DB에 데이터가 생성되어야 한다.")
    public void signup_AccountSuccessTest() {
        // Given
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";
        String expectedEncPassword = "encodePassword";

        AccountSignUpRequestDto requestDto = AccountSignUpRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(passwordEncoder.encode(password))
                .thenReturn(expectedEncPassword);
        when(accountRepository.save(any()))
                .thenReturn(Account.builder()
                        .id(1L)
                        .userId(userId)
                        .password(password)
                        .build());

        accountService.signup(requestDto);

        // Then
        ArgumentCaptor<Account> argument = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository).save(argument.capture());

        Account callArgument = argument.getValue();
        assertThat(callArgument.getUserId(), equalTo(userId));
        assertThat(callArgument.getPassword(), equalTo(expectedEncPassword));
    }

    @Test
    @DisplayName("가입시 사용자 ID 중복 체크가 되어야 한다.")
    public void signup_AccountUserIdDuplicateCheckTest() {
        // Given
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        AccountSignUpRequestDto requestDto = AccountSignUpRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(Optional.empty());
        when(accountRepository.save(any()))
                .thenReturn(Account.builder()
                        .id(1L)
                        .userId(userId)
                        .password(password)
                        .build());

        accountService.signup(requestDto);

        // Then
        verify(accountRepository).findOneByUserId(userId);
    }

    @Test
    @DisplayName("가입시 이미 동일한 계정 아이디가 존재하는 경우 예외가 발생해야 한다.")
    public void signup_ExistUserIdThrowException() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        String exceptedErrorMeg = "이미 존재하는 ID 입니다.";

        AccountSignUpRequestDto requestDto = AccountSignUpRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(Optional.of(new Account()));

        Exception exception = assertThrows(Exception.class, ()->
                accountService.signup(requestDto));

        assertThat(exception.getMessage(), equalTo(exceptedErrorMeg));
    }

    @Test
    @DisplayName("가입시 비밀번호가 암호화되어야 한다.")
    public void Signup_AccountPasswordEncoder() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        AccountSignUpRequestDto requestDto = AccountSignUpRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(accountRepository.save(any()))
                .thenReturn(Account.builder()
                        .id(1L)
                        .userId(userId)
                        .password(password)
                        .build());

        when(passwordEncoder.encode(password))
                .thenReturn("암호화됬당!");

        accountService.signup(requestDto);

        verify(passwordEncoder).encode(password);
    }

    @Test
    @DisplayName("로그인시 요청 아이디에 해당하는 계정 데이터를 조회해야 한다.")
    public void signin_FindAccountWithRequestUserId() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(Optional.of(Account.builder()
                        .id(Long.valueOf("1"))
                        .userId(userId)
                        .password(password)
                        .build()));
        when(jwtTokenProvider.createToken(any()))
                .thenReturn("JWT Token");
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);

        accountService.signin(AccountSigninRequestDto.builder()
                .userId(userId)
                .password(password)
                .build());

        verify(accountRepository).findOneByUserId(userId);
    }

    @Test
    @DisplayName("로그인시 계정 데이터가 존재하지 않는 경우, 오류를 반환해야 한다.")
    public void signin_NotFoundAccountThrowError() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> accountService.signin(AccountSigninRequestDto.builder()
                .userId(userId)
                .password(password)
                .build()));

        assertThat(exception.getServiceError(), equalTo(ServiceError.NOT_FOUND_ACCOUNT));
        assertThat(exception.getMessage(), equalTo("해당 계정이 존재하지 않습니다."));
    }

    @Test
    @DisplayName("로그인시 요청 비밀번호가 유효한지 검증해야 한다.")
    public void signin_CheckPasswordMatches() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";
        String encodedPassword = "sdfjklsfj12kl321";

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(Optional.of(Account.builder()
                        .id(Long.valueOf("1"))
                        .userId(userId)
                        .password(encodedPassword)
                        .build()));
        when(jwtTokenProvider.createToken(any()))
                .thenReturn("JWT Token");
        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(true);

        accountService.signin(AccountSigninRequestDto.builder()
                .userId(userId)
                .password(password)
                .build());

        verify(passwordEncoder).matches(password, encodedPassword);
    }

    @Test
    @DisplayName("로그인시 요청 비밀번호가 일치하지 않는 경우, 오류를 반환해야 한다.")
    public void signin_CheckPasswordNotMatchThrowError() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";
        String encodedPassword = "sdfjklsfj12kl321";

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(Optional.of(Account.builder()
                        .id(Long.valueOf("1"))
                        .userId(userId)
                        .password(encodedPassword)
                        .build()));
        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(false);

        ServiceException exception = assertThrows(ServiceException.class, () ->
                accountService.signin(AccountSigninRequestDto.builder()
                        .userId(userId)
                        .password(password)
                        .build()));

        assertThat(exception.getServiceError(), equalTo(ServiceError.PASSWORD_ERROR));
        assertThat(exception.getMessage(), equalTo("비밀번호가 올바르지 않습니다."));
    }

    @Test
    @DisplayName("로그인시 계정 정보가 올바른 경우, JWT 토큰을 생성해야 한다.")
    public void signin_CheckAuthSuccessMakeJWTToken() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";
        String encodedPassword = "sdfjklsfj12kl321";

        Optional<Account> account = Optional.of(Account.builder()
                .id(Long.valueOf("1"))
                .userId(userId)
                .password(encodedPassword)
                .build());

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(account);
        when(jwtTokenProvider.createToken(account.get()))
                .thenReturn("JWT Token");
        when(passwordEncoder.matches(password, encodedPassword))
                .thenReturn(true);

        accountService.signin(AccountSigninRequestDto.builder()
                .userId(userId)
                .password(password)
                .build());

        verify(jwtTokenProvider, times(1))
                .createToken(ArgumentMatchers.refEq(account.get()));
    }

    @Test
    @DisplayName("로그인시 발급된 JWT 토큰을 전달해야 한다.")
    public void sigin_ReturnCreateJWTToken() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";
        String encodedPassword = "sdfjklsfj12kl321";

        String expectedJwtToken = "JWT Token";

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(Optional.of(Account.builder()
                        .id(Long.valueOf("1"))
                        .userId(userId)
                        .password(encodedPassword)
                        .build()));
        when(jwtTokenProvider.createToken(any()))
                .thenReturn(expectedJwtToken);
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);

        AccountSigninResponseDto result = accountService.signin(AccountSigninRequestDto.builder()
                .userId(userId)
                .password(password)
                .build());

        assertThat(result, notNull());
        assertThat(result.getToken(), equalTo(expectedJwtToken));
    }
}

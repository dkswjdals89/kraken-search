package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.web.dto.account.AccountCreateRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceTest {
    @Autowired
    AccountService accountService;

    @MockBean
    AccountRepository accountRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @Test
    public void joinAccountSuccessTest() throws Exception {
        // Given
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        AccountCreateRequestDto requestDto = AccountCreateRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(accountRepository.save(any()))
                .thenReturn(Account.builder()
                        .id(Long.valueOf(1))
                        .userId(userId)
                        .password(password)
                        .build());


        accountService.joinAccount(requestDto);

        // Then
        verify(accountRepository).save(any());
    }

    @Test
    public void joinAccountUserIdDuplicateCheckTest() throws Exception {
        // Given
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        AccountCreateRequestDto requestDto = AccountCreateRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(null);
        when(accountRepository.save(any()))
                .thenReturn(Account.builder()
                        .id(Long.valueOf(1))
                        .userId(userId)
                        .password(password)
                        .build());

        accountService.joinAccount(requestDto);

        // Then
        verify(accountRepository).findOneByUserId(userId);
    }

    @Test
    public void existUserIdThrowExceptionTest() {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        String exceptedErrorMeg = "이미 존재하는 ID 입니다.";

        AccountCreateRequestDto requestDto = AccountCreateRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(accountRepository.findOneByUserId(userId))
                .thenReturn(new Account());

        Exception exception = assertThrows(Exception.class, ()->
                accountService.joinAccount(requestDto));

        assertThat(exception.getMessage()).isEqualTo(exceptedErrorMeg);
    }

    @Test
    public void passwordEncoderTest() throws Exception {
        String userId = "dkswjdals89";
        String password = "dkswjdals89!@";

        AccountCreateRequestDto requestDto = AccountCreateRequestDto.builder()
                .userId(userId)
                .password(password)
                .build();

        when(accountRepository.save(any()))
                .thenReturn(Account.builder()
                        .id(Long.valueOf(1))
                        .userId(userId)
                        .password(password)
                        .build());

        when(passwordEncoder.encode(password))
                .thenReturn("암호화됬당!");

        accountService.joinAccount(requestDto);

        verify(passwordEncoder).encode(password);
    }
}

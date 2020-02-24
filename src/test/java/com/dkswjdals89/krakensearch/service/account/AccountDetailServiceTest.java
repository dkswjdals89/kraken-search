package com.dkswjdals89.krakensearch.service.account;

import com.dkswjdals89.krakensearch.domain.account.Account;
import com.dkswjdals89.krakensearch.domain.account.AccountRepository;
import com.dkswjdals89.krakensearch.exception.ServiceError;
import com.dkswjdals89.krakensearch.exception.ServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Spring Security Account Detail Service 테스트")
public class AccountDetailServiceTest {
    @Autowired
    AccountDetailService accountDetailService;

    @MockBean
    AccountRepository accountRepository;

    @Test
    @DisplayName("유저 정보 조회 시 요청 ID에 해당하는 유저 데이터를 조회해야 한다.")
    public void findAccountTest() {
        String accountId = "1";

        when(accountRepository.findById(Long.valueOf(accountId)))
                .thenReturn(Optional.ofNullable(
                        Account.builder()
                                .id(Long.valueOf(accountId))
                                .userId("dkswjdals8989")
                                .password("Password")
                                .build()));

        accountDetailService.loadUserByUsername(accountId);

        verify(accountRepository).findById(Long.valueOf(accountId));
    }

    @Test
    @DisplayName("유저 정보 조회 시 해당 유저가 없는 경우, 오류를 반환해야 한다.")
    public void notFoundUserErrorTest() {
        String accountId = "1";

        when(accountRepository.findById(Long.valueOf(accountId)))
                .thenReturn(Optional.empty());

        ServiceException exception = assertThrows(ServiceException.class, () -> accountDetailService.loadUserByUsername(accountId));

        assertThat(exception.getServiceError(), equalTo(ServiceError.NOT_FOUND_ACCOUNT));
        assertThat(exception.getMessage(), equalTo("사용자 계정을 찾을수 없습니다."));
    }
}
